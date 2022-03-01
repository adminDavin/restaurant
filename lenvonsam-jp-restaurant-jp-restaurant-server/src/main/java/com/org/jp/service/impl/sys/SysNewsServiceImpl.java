package com.org.jp.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.org.jp.model.sys.SysNews;
import com.org.jp.mapper.sys.SysNewsMapper;
import com.org.jp.service.sys.ISysNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.jp.vo.NewsListVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Service
public class SysNewsServiceImpl extends ServiceImpl<SysNewsMapper, SysNews> implements ISysNewsService {

    @Override
    public IPage<NewsListVo> selectFrontByPage(IPage<NewsListVo> page, QueryWrapper<NewsListVo> qw) {
        return this.baseMapper.selectFrontByPage(page, qw);
    }
}
