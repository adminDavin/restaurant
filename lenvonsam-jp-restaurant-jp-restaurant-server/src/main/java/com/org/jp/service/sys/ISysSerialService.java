package com.org.jp.service.sys;

import com.org.jp.model.sys.SysSerial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 序列 服务类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
public interface ISysSerialService extends IService<SysSerial> {
    /**
     * 获取下个流水ID
     *
     * @param serialKey
     * @return
     */
    String getNextId(String serialKey);

}
