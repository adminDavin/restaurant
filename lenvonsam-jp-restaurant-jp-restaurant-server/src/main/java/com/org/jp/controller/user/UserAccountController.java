package com.org.jp.controller.user;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.UserAccountDTO;
import com.org.jp.model.user.UserAccount;
import com.org.jp.service.user.IUserAccountService;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xy.api.dto.ResultDTO;
import org.xy.api.utils.DaoUtil;
import org.xy.api.utils.StringUtil;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

import static com.org.jp.util.CommonUtil.DEFAULT_PLATFORM_CODE;

/**
 * <p>
 * 用户账号 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/user/account")
public class UserAccountController extends BaseController {
    @Autowired
    private IUserAccountService userAccountService;

    @PostMapping("login")
    public ResultDTO userLogin(@RequestBody UserAccountDTO userAccountDTO) {
        if (StringUtil.isEmpty(userAccountDTO.getLoginName())) {
            return ResultDTO.error("登录名不能为空");
        }
        if (StringUtil.isEmpty(userAccountDTO.getUserPwd())) {
            return ResultDTO.error("密码不能为空");
        }
        UserAccount userAccount = userAccountService.lambdaQuery()
                .eq(UserAccount::getLoginName, userAccountDTO.getLoginName())
                .eq(UserAccount::getPlatformCode, DEFAULT_PLATFORM_CODE).one();
        if (userAccount == null) {
            return ResultDTO.error("用户不存在");
        }
        if (userAccount.getStatus() == 2) {
            return ResultDTO.error("该用户被禁用");
        }
        if (!userAccount.getUserPwd().equals(userAccountDTO.getUserPwd())) {
            return ResultDTO.error("用户名或密码不正确");
        }
        UserAccountDTO respUserAccountDTO = new UserAccountDTO();
        BeanUtils.copyProperties(userAccount, respUserAccountDTO);
        JSONObject userObject = (JSONObject) JSONObject.toJSON(respUserAccountDTO);
        userObject.remove("userPwd");
        redisTemplate.opsForValue().set("u_" + userAccount.getUserCode(), userObject);
        return ResultDTO.ok(userObject);
    }

    @GetMapping("")
    public ResultDTO userList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<UserAccount> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, UserAccount.class);
        IPage<UserAccount> pages = userAccountService.page((Page<UserAccount>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'user_' + #userAccountDTO.id", value = "user", condition = "#userAccountDTO.id != null")
    })
    public ResultDTO userSaveOrUpdate(@RequestBody @Valid UserAccountDTO userAccountDTO) {
        UserAccount origin = new UserAccount();
        BeanUtils.copyProperties(userAccountDTO, origin);
        UserAccount existUser = userAccountService.lambdaQuery()
                .eq(UserAccount::getLoginName, origin.getLoginName())
                .eq(UserAccount::getUserType, 1)
                .eq(UserAccount::getPlatformCode, DEFAULT_PLATFORM_CODE).one();
        if (existUser != null && !existUser.getId().equals(origin.getId())) {
            return ResultDTO.error("登录名不能重复");
        }
        if (StringUtil.isEmpty(origin.getUserCode())) {
            String nextId = sysSerialService.getNextId("userAccount");
            String nextUserCode = DaoUtil.generateBillCode("UA", nextId);
            origin.setUserCode(nextUserCode);
            // 创建默认密码
            origin.setUserPwd("895b317c76b8e504c2fb32dbb4420178f60ce321");
        }
        userAccountService.saveOrUpdate(origin);
        return ResultDTO.ok();
    }

    @GetMapping("{id}")
    @Cacheable(key = "'user_' + #id", value = "user")
    public ResultDTO userInfo(@PathVariable("id") String id) {
        UserAccount user = userAccountService.getBaseMapper().selectById(id);
        if (user == null) {
            return ResultDTO.error("用户不存在");
        } else {
            return ResultDTO.ok(user);
        }
    }

    @PostMapping("batch/status")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO userBatchUpdateStatus(@RequestBody JSONObject params) {
        if (StringUtil.isEmpty(params.getString("status"))) {
            return ResultDTO.error("状态不能为空");
        } else if (StringUtil.isEmpty(params.getString("ids"))) {
            return ResultDTO.error("ids不能为空");
        } else {
            String[] idsArr = params.getString("ids").split(",");
            for (String id : idsArr) {
                userUpdateStatus(id, Integer.parseInt(params.getString("status")));
            }
            return ResultDTO.ok();
        }
    }

    @DeleteMapping("batch/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO userBatchDelete(String ids) {
        String[] idsArr = ids.split(",");
        for (String s : idsArr) {
            userRemoveById(s);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'user_' + #id", value = "user", condition = "#id != null")
    private Boolean userRemoveById(String id) {
        return userAccountService.removeById(id);
    }

    @CacheEvict(key = "'user_' + #id", value = "user", condition = "#id != null")
    private Boolean userUpdateStatus(String id, Integer status) {
        UserAccount updateAccount = new UserAccount();
        updateAccount.setId(id);
        updateAccount.setStatus(status);
        return userAccountService.updateById(updateAccount);
    }
}

