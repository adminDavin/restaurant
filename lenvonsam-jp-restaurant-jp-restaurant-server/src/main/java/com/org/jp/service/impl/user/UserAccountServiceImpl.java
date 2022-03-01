package com.org.jp.service.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.org.jp.dto.UserAccountDTO;
import com.org.jp.model.user.UserAccount;
import com.org.jp.mapper.user.UserAccountMapper;
import com.org.jp.service.user.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账号 服务实现类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JSONObject refreshUserObject(String userCode) {
        UserAccount userAccount = this.lambdaQuery().eq(UserAccount::getUserCode, userCode).one();
        if (userAccount == null) {
            return null;
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        BeanUtils.copyProperties(userAccount, userAccountDTO);
        JSONObject userObject = (JSONObject) JSONObject.toJSON(userAccountDTO);
        userObject.remove("userPwd");
        redisTemplate.opsForValue().set("u_" + userAccount.getUserCode(), userObject);
        return userObject;
    }
}
