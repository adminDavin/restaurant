package com.org.jp.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.model.sys.*;
import com.org.jp.service.sys.*;
import com.org.jp.vo.NewsListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.xy.api.dto.ResultDTO;
import org.xy.api.utils.DaoUtil;
import org.xy.api.utils.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.org.jp.util.CommonUtil.DEFAULT_PLATFORM_CODE;

/**
 * 系统相关接口
 *
 * @author juny
 */
@RestController
@RequestMapping("/api/sys")
public class ApiSysController extends BaseController {
    @Autowired
    private ISysBannerService sysBannerService;
    @Autowired
    private ISysNewsService sysNewsService;
    @Autowired
    private ISysMerchantService sysMerchantService;
    @Autowired
    private ISysMerchantAppointService sysMerchantAppointService;
    @Autowired
    private ISysProductService sysProductService;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysCardService sysCardService;

    @GetMapping("banner")
    public ResultDTO sysBannerListApi() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysBanner> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysBanner.class);
        qw.eq("status", 1);
        qw.eq("platform_code", DEFAULT_PLATFORM_CODE);
        List<SysBanner> pages = sysBannerService.list(qw);
        return ResultDTO.ok(pages, (long) pages.size());
    }

    @GetMapping("news")
    public ResultDTO newsListApi() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<NewsListVo> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, NewsListVo.class);
        qw.eq("news_property", 1);
        qw.eq("platform_code", DEFAULT_PLATFORM_CODE);
        IPage<NewsListVo> pages = sysNewsService.selectFrontByPage((Page<NewsListVo>) DaoUtil.queryPage(params, DaoUtil.userOrderItem("id")), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @GetMapping("news/{id}")
    @Cacheable(key = "'news_' + #id", value = "news", condition = "#id != null")
    public ResultDTO newsDetailApi(@PathVariable("id") String id) {
        SysNews news = sysNewsService.getBaseMapper().selectById(id);
        if (news == null) {
            return ResultDTO.error("News不存在");
        } else {
            return ResultDTO.ok(news);
        }
    }

    @GetMapping("merchants")
    @Cacheable(key = "'merchant_drop_list'", value = "merchant")
    public ResultDTO merchantListApi() {
        QueryWrapper<SysMerchant> qw = new QueryWrapper<>();
        qw.eq("platform_code", DEFAULT_PLATFORM_CODE);
        List<SysMerchant> pages = sysMerchantService.list(qw);
        return ResultDTO.ok(pages, (long) pages.size());
    }

    @GetMapping("merchant/info/{id}")
    @Cacheable(key = "'merchant_'+#id", value = "merchant")
    public ResultDTO merchantInfoApi(@PathVariable("id") String id) {
        SysMerchant merchant = sysMerchantService.getBaseMapper().selectById(id);
        if (merchant == null) {
            return ResultDTO.error("Merchant不存在");
        } else {
            return ResultDTO.ok(merchant);
        }
    }

    @GetMapping("merchant/gallery")
    public ResultDTO merchantGalleryApi(String merchantCode) {
        return ResultDTO.ok(sysFileService.lambdaQuery().eq(SysFile::getFileBusiCode, merchantCode).list());
    }

    @GetMapping("merchant/cards")
    public ResultDTO merchantCardListApi(String merchantCode) {
        return ResultDTO.ok(sysCardService.lambdaQuery().eq(SysCard::getBusiCode, merchantCode).list());
    }

    @GetMapping("merchant/appointList")
    public ResultDTO merchantAppointListApi(String merchantCode, @RequestParam(required = false) String appointTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime appointDate = null;
        if (StringUtil.isEmpty(appointTime)) {
            LocalDateTime now = LocalDateTime.now();
            String nowStr = dtf.format(now);
            appointDate = LocalDate.parse(nowStr.substring(0, 7) + "-01", dtf).atTime(0, 0);
        } else {
            appointDate = LocalDate.parse(appointTime.substring(0, 7) + "-01", dtf).atTime(0, 0);
        }
        QueryWrapper<SysMerchantAppoint> qw = new QueryWrapper<>();
        qw.eq("merchant_code", merchantCode);
        qw.ge("appoint_time", appointDate);
        List<SysMerchantAppoint> appoints = sysMerchantAppointService.list(qw);
        return ResultDTO.ok(appoints, (long) appoints.size());
    }

    @GetMapping("merchant/productList")
    public ResultDTO merchantProductList(String merchantCode) {
        return ResultDTO.ok(sysProductService.lambdaQuery().eq(SysProduct::getMerchantCode, merchantCode).eq(SysProduct::getProductType, 1).eq(SysProduct::getStatus, 1).list());
    }

    @GetMapping("merchant/menuList")
    public ResultDTO merchantMenuList(String merchantCode) {
        return ResultDTO.ok(sysProductService.lambdaQuery().eq(SysProduct::getMerchantCode, merchantCode).eq(SysProduct::getProductType, 2).eq(SysProduct::getStatus, 1).list());
    }
}
