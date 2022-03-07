package com.org.jp.mapper.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.org.jp.model.sys.SysNews;

/**
 * <p>
 * 新闻 Mapper 接口
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Mapper
public interface NewsMapper extends BaseMapper<SysNews> {
	SysNews selectById(@Param("id") String id);

	void updateByPrimaryKeySelective(@Param("record") SysNews record, @Param("id") String id);
}
