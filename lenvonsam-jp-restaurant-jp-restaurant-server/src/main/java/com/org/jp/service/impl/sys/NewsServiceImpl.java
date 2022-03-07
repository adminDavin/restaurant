package com.org.jp.service.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.jp.mapper.sys.NewsMapper;
import com.org.jp.model.sys.SysNews;

@Service
public class NewsServiceImpl {

	@Autowired
	private NewsMapper newsMapper;

	public SysNews selectById(String id) {
		return newsMapper.selectById(id);
	}
	
	public void updateByPrimaryKeySelective(String id, SysNews record) {
		newsMapper.updateByPrimaryKeySelective(record, id);
	}

}
