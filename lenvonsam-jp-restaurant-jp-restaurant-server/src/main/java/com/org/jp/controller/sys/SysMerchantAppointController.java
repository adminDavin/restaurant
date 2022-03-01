package com.org.jp.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.SysMerchantAppointDTO;
import com.org.jp.dto.SysMerchantDTO;
import com.org.jp.model.sys.SysMerchant;
import com.org.jp.model.sys.SysMerchantAppoint;
import com.org.jp.service.sys.ISysMerchantAppointService;
import com.org.jp.service.sys.ISysMerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.xy.api.dto.ResultDTO;
import org.xy.api.utils.DaoUtil;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 商户预约设置 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/sys/merchantAppoint")
public class SysMerchantAppointController extends BaseController {
    @Autowired
    private ISysMerchantAppointService sysMerchantAppointService;

    @GetMapping("")
    public ResultDTO merchantAppointList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysMerchantAppoint> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysMerchantAppoint.class);
        IPage<SysMerchantAppoint> pages = sysMerchantAppointService.page((Page<SysMerchantAppoint>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'merchantAppoint_' + #sysMerchantAppointDTO.id", value = "merchantAppoint")
    })
    public ResultDTO merchantAppointSaveOrUpdate(@RequestBody @Valid SysMerchantAppointDTO sysMerchantAppointDTO) {
        SysMerchantAppoint origin = new SysMerchantAppoint();
        BeanUtils.copyProperties(sysMerchantAppointDTO, origin);
        SysMerchantAppoint exist = sysMerchantAppointService.lambdaQuery()
                .eq(SysMerchantAppoint::getMerchantCode, origin.getMerchantCode())
                .eq(SysMerchantAppoint::getAppointTime, origin.getAppointTime())
                .one();
        if (exist != null && !exist.getId().equals(origin.getId())) {
            return ResultDTO.error("此记录已存在，无法新增");
        }
        sysMerchantAppointService.saveOrUpdate(origin);
        return ResultDTO.ok();
    }

    @GetMapping("{id}")
    @Cacheable(key = "'merchantAppoint_' + #id", value = "merchantAppoint", condition = "#id != null")
    public ResultDTO merchantAppointAppointInfo(@PathVariable("id") String id) {
        SysMerchantAppoint merchantAppoint = sysMerchantAppointService.getBaseMapper().selectById(id);
        if (merchantAppoint == null) {
            return ResultDTO.error("Merchant不存在");
        } else {
            return ResultDTO.ok(merchantAppoint);
        }
    }

    @DeleteMapping("batch/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO merchantAppointBatchDelete(String ids) {
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            merchantRemoveById(id);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'merchantAppoint_' + #id", value = "merchantAppoint", condition = "#id != null")
    private boolean merchantRemoveById(String id) {
        return sysMerchantAppointService.removeById(id);
    }
}

