package com.org.jp.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.SysCardDTO;
import com.org.jp.model.sys.SysCard;
import com.org.jp.service.sys.ISysCardService;
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

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 卡片信息 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/sys/card")
public class SysCardController extends BaseController {

    @Autowired
    private ISysCardService sysCardService;

    @GetMapping("")
    public ResultDTO cardList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysCard> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysCard.class);
        IPage<SysCard> pages = sysCardService.page((Page<SysCard>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'card_'+ #sysCardDTO.id", value = "card", condition = "#sysCardDTO.id != null")
    })
    public ResultDTO cardSaveOrUpdate(@RequestBody @Valid SysCardDTO sysCardDTO) {
        SysCard origin = new SysCard();
        BeanUtils.copyProperties(sysCardDTO, origin);
        sysCardService.saveOrUpdate(origin);
        return ResultDTO.ok();
    }

    @GetMapping("{id}")
    @Cacheable(key = "'card_' + #id", value = "card", condition = "#id != null")
    public ResultDTO cardInfo(@PathVariable("id") String id) {
        SysCard card = sysCardService.getBaseMapper().selectById(id);
        if (card == null) {
            return ResultDTO.error("card不存在");
        } else {
            return ResultDTO.ok(card);
        }
    }

    @DeleteMapping("batch/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO cardBatchDelete(String ids) throws QiniuException {
        String[] idsArr = ids.split(",");
        for (String s : idsArr) {
            cardRemoveById(s);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'card_' + #id", value = "card", condition = "#id != null")
    private Boolean cardRemoveById(String id) throws QiniuException {
        SysCard origin = sysCardService.getById(id);
        if (origin == null) {
            return false;
        } else {
            return sysCardService.removeById(id);
        }
    }

}

