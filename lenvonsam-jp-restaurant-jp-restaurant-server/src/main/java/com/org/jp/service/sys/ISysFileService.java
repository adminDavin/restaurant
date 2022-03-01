package com.org.jp.service.sys;

import com.org.jp.model.sys.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiniu.common.QiniuException;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 * 文件 服务类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
public interface ISysFileService extends IService<SysFile> {

    /**
     * 根据业务编号批量删除文件
     *
     * @param busiCode
     * @return
     */
    boolean deleteBusiCodeFiles(String busiCode);

    /**
     * 删除业务文件以及云端文件
     *
     * @param busiCode
     */
    void deleteBusiCodeFilesAndCloudFiles(String busiCode);
}
