package com.org.jp.controller.sys;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xy.api.dto.ResultDTO;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.org.jp.dto.SysNewsDTO;
import com.org.jp.model.sys.SysNews;
import com.org.jp.service.impl.sys.NewsServiceImpl;
import com.org.jp.service.sys.ISysNewsService;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	private NewsServiceImpl newsServiceImpl;
	
	@Autowired
    private ISysNewsService sysNewsService;
	
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

}
