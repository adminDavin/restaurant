package com.org.jp.controller.sys;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.SysBannerDTO;
import com.org.jp.model.sys.SysBanner;
import com.org.jp.model.user.UserAccount;
import com.org.jp.service.QiniuService;
import com.org.jp.service.sys.ISysBannerService;
import com.qiniu.common.QiniuException;
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
import java.util.Map;

/**
 * <p>
 * banner 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/sys/banner")
public class SysBannerController extends BaseController {
    @Autowired
    private ISysBannerService sysBannerService;
    @Autowired
    private QiniuService qiniuService;

    @GetMapping("")
    public ResultDTO bannerList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysBanner> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysBanner.class);
        IPage<SysBanner> pages = sysBannerService.page((Page<SysBanner>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'banner_'+ #sysBannerDTO.id", value = "banner", condition = "#id != null")
    })
    public ResultDTO bannerSaveOrUpdate(@RequestBody @Valid SysBannerDTO sysBannerDTO) {
        SysBanner origin = new SysBanner();
        BeanUtils.copyProperties(sysBannerDTO, origin);
        if (StringUtil.isEmpty(origin.getBannerName())) {
            origin.setBannerName(origin.getCloudName());
        }
        sysBannerService.saveOrUpdate(origin);
        return ResultDTO.ok();
    }

    @GetMapping("{id}")
    @Cacheable(key = "'banner_' + #id", value = "banner", condition = "#id != null")
    public ResultDTO bannerInfo(@PathVariable("id") String id) {
        SysBanner banner = sysBannerService.getBaseMapper().selectById(id);
        if (banner == null) {
            return ResultDTO.error("banner不存在");
        } else {
            return ResultDTO.ok(banner);
        }
    }


    @PostMapping("batch/status")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO bannerBatchUpdateStatus(@RequestBody JSONObject params) {
        if (StringUtil.isEmpty(params.getString("status"))) {
            return ResultDTO.error("状态不能为空");
        } else if (StringUtil.isEmpty(params.getString("ids"))) {
            return ResultDTO.error("ids不能为空");
        } else {
            String[] idsArr = params.getString("ids").split(",");
            for (String id : idsArr) {
                bannerUpdateStatus(id, Integer.parseInt(params.getString("status")));
            }
            return ResultDTO.ok();
        }
    }

    @CacheEvict(key = "'banner_' + #id", value = "user", condition = "#id != null")
    private Boolean bannerUpdateStatus(String id, Integer status) {
        SysBanner updateBanner = new SysBanner();
        updateBanner.setId(id);
        updateBanner.setStatus(status);
        return sysBannerService.updateById(updateBanner);
    }

    @DeleteMapping("batch/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO bannerBatchDelete(String ids) throws QiniuException {
        String[] idsArr = ids.split(",");
        for (String s : idsArr) {
            bannerRemoveById(s);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'banner_' + #id", value = "banner", condition = "#id != null")
    private Boolean bannerRemoveById(String id) throws QiniuException {
        SysBanner origin = sysBannerService.getById(id);
        if (origin == null) {
            return false;
        } else {
            if (StringUtil.isNoneEmpty(origin.getCloudName())) {
                qiniuService.deleteFile(origin.getCloudName());
            }
            return sysBannerService.removeById(id);
        }
    }
}

