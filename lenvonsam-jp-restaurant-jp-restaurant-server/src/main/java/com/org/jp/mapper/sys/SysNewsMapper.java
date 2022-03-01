package com.org.jp.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.org.jp.model.sys.SysNews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.org.jp.vo.NewsListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 新闻 Mapper 接口
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
public interface SysNewsMapper extends BaseMapper<SysNews> {
    @Select("select t.* from v_news_ft_list t  ${ew.customSqlSegment} ")
    IPage<NewsListVo> selectFrontByPage(IPage<NewsListVo> page, @Param(Constants.WRAPPER) QueryWrapper<NewsListVo> qw);

}
