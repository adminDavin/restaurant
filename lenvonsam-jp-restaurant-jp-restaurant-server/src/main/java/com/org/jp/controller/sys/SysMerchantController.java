package com.org.jp.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.SysFileDTO;
import com.org.jp.dto.SysMerchantDTO;
import com.org.jp.model.sys.SysFile;
import com.org.jp.model.sys.SysMerchant;
import com.org.jp.service.sys.ISysFileService;
import com.org.jp.service.sys.ISysMerchantService;
import com.org.jp.service.sys.ISysMerchantService;
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
import java.util.Arrays;
import java.util.Map;

import static com.org.jp.util.CommonUtil.DEFAULT_PLATFORM_CODE;

/**
 * <p>
 * 商户 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/sys/merchant")
public class SysMerchantController extends BaseController {
    @Autowired
    private ISysMerchantService sysMerchantService;
    @Autowired
    private ISysFileService sysFileService;

    @GetMapping("")
    public ResultDTO merchantList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysMerchant> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysMerchant.class);
        IPage<SysMerchant> pages = sysMerchantService.page((Page<SysMerchant>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'merchant_'+ #sysMerchantDTO.id", value = "merchant"),
            @CacheEvict(key = "'merchant_drop_list'", value = "merchant")
    })
    public ResultDTO merchantSaveOrUpdate(@RequestBody @Valid SysMerchantDTO sysMerchantDTO) {
        SysMerchant origin = new SysMerchant();
        BeanUtils.copyProperties(sysMerchantDTO, origin);
        SysMerchant sysMerchant = sysMerchantService.lambdaQuery()
                .eq(SysMerchant::getMerchantName, origin.getMerchantName())
                .eq(SysMerchant::getPlatformCode, DEFAULT_PLATFORM_CODE).one();
        if (sysMerchant != null && !sysMerchant.getMerchantCode().equals(origin.getMerchantCode())) {
            return ResultDTO.error("名称不能重复");
        } else {
            if (StringUtil.isEmpty(origin.getMerchantCode())) {
                String nextId = sysSerialService.getNextId("sysMerchant");
                String merchantCode = DaoUtil.generateBillCode("SMT", nextId);
                origin.setMerchantCode(merchantCode);
            }
            sysMerchantService.saveOrUpdate(origin);
            // 删除图片
            sysFileService.deleteBusiCodeFilesAndCloudFiles(origin.getMerchantCode());
            // 保存图片
            if (sysMerchantDTO.getGallery() != null) {
                for (SysFileDTO sysFileDTO : sysMerchantDTO.getGallery()) {
                    SysFile sysFile = new SysFile();
                    BeanUtils.copyProperties(sysFileDTO, sysFile);
                    sysFile.setFileBusiCode(origin.getMerchantCode());
                    sysFile.setFileBusiType(origin.getBusiType());
                    sysFile.setFileSource(1);
                    sysFile.setFileType(1);
                    if (StringUtil.isEmpty(sysFile.getFileName())) {
                        sysFile.setFileName(sysFile.getFileCloudName());
                    }
                    sysFileService.save(sysFile);
                }
            }
            return ResultDTO.ok();
        }
    }

    @GetMapping("{id}")
    @Cacheable(key = "'merchant_'+#id", value = "merchant")
    public ResultDTO merchantInfo(@PathVariable("id") String id) {
        SysMerchant merchant = sysMerchantService.getBaseMapper().selectById(id);
        if (merchant == null) {
            return ResultDTO.error("Merchant不存在");
        } else {
            return ResultDTO.ok(merchant);
        }
    }

    @DeleteMapping("batch/remove")
    @CacheEvict(key = "'merchant_drop_list'", value = "merchant")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO merchantBatchDelete(String ids) {
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            merchantRemoveById(id);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'merchant_'+ #id", value = "merchant")
    private void merchantRemoveById(String id) {
        sysMerchantService.removeById(id);
    }

    @GetMapping("dropList")
    @Cacheable(key = "'merchant_drop_list'", value = "merchant")
    public ResultDTO merchantDropList() {
        return ResultDTO.ok(sysMerchantService.lambdaQuery().eq(SysMerchant::getPlatformCode, DEFAULT_PLATFORM_CODE).list());
    }
}

