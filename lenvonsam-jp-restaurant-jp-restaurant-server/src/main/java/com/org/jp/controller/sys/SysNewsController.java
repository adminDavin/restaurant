package com.org.jp.controller.sys;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.jp.controller.BaseController;
import com.org.jp.dto.SysNewsDTO;
import com.org.jp.model.sys.SysNews;
import com.org.jp.service.impl.sys.NewsServiceImpl;
import com.org.jp.service.sys.ISysNewsService;
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
 * 新闻 前端控制器
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/sys/news")
public class SysNewsController extends BaseController {
    @Autowired
    private ISysNewsService sysNewsService;

	@Autowired
	private NewsServiceImpl newsServiceImpl;

    @GetMapping("")
    public ResultDTO newsList() {
        Map<String, Object> params = DaoUtil.requestMap2Map(request);
        QueryWrapper<SysNews> qw = new QueryWrapper<>();
        DaoUtil.parseGenericQueryWrapper(qw, params, SysNews.class);
        IPage<SysNews> pages = sysNewsService.page((Page<SysNews>) DaoUtil.queryPage(params, DaoUtil.userOrderItem()), qw);
        return ResultDTO.ok(pages.getRecords(), pages.getTotal());
    }

	@PostMapping("")
    @Transactional(rollbackFor = {Exception.class})
    @Caching(evict = {
            @CacheEvict(key = "'news_'+ #sysNewsDTO.id", value = "news")
    })
	public ResultDTO newsSaveOrUpdate(@RequestBody @Valid SysNewsDTO sysNewsDTO) {
        SysNews origin = new SysNews();
        BeanUtils.copyProperties(sysNewsDTO, origin);
        if (origin.getNewsContent().length() > 20) {
            origin.setNewsThumbContent(origin.getNewsContent().substring(0, 20));
        } else {
            origin.setNewsThumbContent(origin.getNewsContent());
        }
        try {
        	var result = newsServiceImpl.selectById(origin.getId());
        	if (ObjectUtils.isEmpty(result)) {
        		sysNewsService.saveOrUpdate(origin);
        	} else {
        		newsServiceImpl.updateByPrimaryKeySelective(result.getId(), origin);
        	}
        	System.out.println(result);
        } catch(Exception e) {
        	throw new RuntimeException("upload failed for" + e.getMessage());
        }
        
        return ResultDTO.ok();
    }


    @GetMapping("{id}")
    @Cacheable(key = "'news_' + #id", value = "news", condition = "#id != null")
    public ResultDTO newsInfo(@PathVariable("id") String id) {
        SysNews news = sysNewsService.getBaseMapper().selectById(id);
        if (news == null) {
            return ResultDTO.error("News不存在");
        } else {
            return ResultDTO.ok(news);
        }
    }

    @PostMapping("batch/status")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO newsBatchUpdateStatus(@RequestBody JSONObject params) {
        if (StringUtil.isEmpty(params.getString("status"))) {
            return ResultDTO.error("状态不能为空");
        } else if (StringUtil.isEmpty(params.getString("ids"))) {
            return ResultDTO.error("ids不能为空");
        } else {
            String[] idsArr = params.getString("ids").split(",");
            for (String id : idsArr) {
                newsUpdateStatus(id, Integer.parseInt(params.getString("status")));
            }
            return ResultDTO.ok();
        }
    }

    @CacheEvict(key = "'news_' + #id", value = "news", condition = "#id != null")
    private Boolean newsUpdateStatus(String id, Integer status) {
        SysNews updateNews = new SysNews();
        updateNews.setId(id);
        updateNews.setStatus(status);
        return sysNewsService.updateById(updateNews);
    }

    @DeleteMapping("batch/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ResultDTO newsBatchDelete(String ids) {
        String[] idsArr = ids.split(",");
        for (String s : idsArr) {
            newsRemoveById(s);
        }
        return ResultDTO.ok();
    }

    @CacheEvict(key = "'news_' + #id", value = "news", condition = "#id != null")
    private Boolean newsRemoveById(String id) {
        return sysNewsService.removeById(id);
    }
}

