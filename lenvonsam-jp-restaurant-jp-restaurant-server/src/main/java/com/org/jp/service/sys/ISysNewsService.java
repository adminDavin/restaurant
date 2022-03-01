package com.org.jp.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.org.jp.model.sys.SysNews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.org.jp.vo.NewsListVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 新闻 服务类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
public interface ISysNewsService extends IService<SysNews> {
    /**
     * 前台新闻列表
     *
     * @param page
     * @param qw
     * @return
     */
    IPage<NewsListVo> selectFrontByPage(IPage<NewsListVo> page, QueryWrapper<NewsListVo> qw);
}
