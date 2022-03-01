package com.org.jp.service.impl.sys;

import com.org.jp.model.sys.SysFile;
import com.org.jp.mapper.sys.SysFileMapper;
import com.org.jp.service.QiniuService;
import com.org.jp.service.sys.ISysFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiniu.common.QiniuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xy.api.utils.StringUtil;

import java.util.List;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Autowired
    private QiniuService qiniuService;

    @Override
    public boolean deleteBusiCodeFiles(String busiCode) {
        return this.baseMapper.deleteByBusiCode(busiCode);
    }

    @Override
    public void deleteBusiCodeFilesAndCloudFiles(String busiCode) {
        List<SysFile> sysFileList = this.lambdaQuery().eq(SysFile::getFileBusiCode, busiCode).list();
        if (sysFileList.size() > 0) {
            for (SysFile sysFile : sysFileList) {
                try {
                    if (sysFile.getFileSource() == 1 && StringUtil.isNotEmpty(sysFile.getFileCloudName())) {
                        qiniuService.deleteFile(sysFile.getFileCloudName());
                    }
                } catch (Exception e) {
                }
                this.baseMapper.deleteById(sysFile.getId());
            }
        }
    }
}
