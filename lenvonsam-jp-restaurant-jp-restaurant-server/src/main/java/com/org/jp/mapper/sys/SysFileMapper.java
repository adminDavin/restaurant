package com.org.jp.mapper.sys;

import com.org.jp.model.sys.SysFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 * 文件 Mapper 接口
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
public interface SysFileMapper extends BaseMapper<SysFile> {

    @Delete("delete from t_sys_file where file_busi_code = #{busiCode}")
    boolean deleteByBusiCode(String busiCode);
}
