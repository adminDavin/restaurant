package com.org.jp.controller.sys;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.SysProductDTO;
import com.org.jp.model.sys.SysProduct;
import com.org.jp.service.sys.ISysProductService;
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
 * 商品表 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-16
 */
@RestController
@RequestMapping("/sys/product")
public class SysProductController extends BaseController {
    @Autowired
    private ISysProductService sysProductService;

    @GetMapping("")
    public ResultDTO productList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysProduct> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysProduct.class);
        IPage<SysProduct> pages = sysProductService.page((Page<SysProduct>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'product_'+ #sysProductDTO.id", value = "product", condition = "#id != null")
    })
    public ResultDTO productSaveOrUpdate(@RequestBody @Valid SysProductDTO sysProductDTO) {
        SysProduct origin = new SysProduct();
        BeanUtils.copyProperties(sysProductDTO, origin);
        if (StringUtil.isEmpty(origin.getProductCode())) {
            String nextId = sysSerialService.getNextId("sysProduct");
            String productCode = DaoUtil.generateBillCode("SP", nextId);
            origin.setProductCode(productCode);
        }
//        if (StringUtil.isEmpty(origin.getBannerName())) {
//            origin.setBannerName(origin.getCloudName());
//        }
        sysProductService.saveOrUpdate(origin);
        return ResultDTO.ok();
    }

    @GetMapping("{id}")
    @Cacheable(key = "'product_' + #id", value = "product", condition = "#id != null")
    public ResultDTO productInfo(@PathVariable("id") String id) {
        SysProduct product = sysProductService.getBaseMapper().selectById(id);
        if (product == null) {
            return ResultDTO.error("product不存在");
        } else {
            return ResultDTO.ok(product);
        }
    }


    @PostMapping("batch/status")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO productBatchUpdateStatus(@RequestBody JSONObject params) {
        if (StringUtil.isEmpty(params.getString("status"))) {
            return ResultDTO.error("状态不能为空");
        } else if (StringUtil.isEmpty(params.getString("ids"))) {
            return ResultDTO.error("ids不能为空");
        } else {
            String[] idsArr = params.getString("ids").split(",");
            for (String id : idsArr) {
                productUpdateStatus(id, Integer.parseInt(params.getString("status")));
            }
            return ResultDTO.ok();
        }
    }

    @CacheEvict(key = "'product_' + #id", value = "user", condition = "#id != null")
    private Boolean productUpdateStatus(String id, Integer status) {
        SysProduct updateProduct = sysProductService.getById(id);
        updateProduct.setStatus(status);
        return sysProductService.updateById(updateProduct);
    }

    @DeleteMapping("batch/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO productBatchDelete(String ids) throws QiniuException {
        String[] idsArr = ids.split(",");
        for (String s : idsArr) {
            productRemoveById(s);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'product_' + #id", value = "product", condition = "#id != null")
    private Boolean productRemoveById(String id) throws QiniuException {
        SysProduct origin = sysProductService.getById(id);
        if (origin == null) {
            return false;
        } else {
            return sysProductService.removeById(id);
        }
    }

}

