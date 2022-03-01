package com.org.jp.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.org.jp.model.sys.SysSerial;
import com.org.jp.mapper.sys.SysSerialMapper;
import com.org.jp.service.sys.ISysSerialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import static com.org.jp.util.CommonUtil.DEFAULT_PLATFORM_CODE;

/**
 * <p>
 * 序列 服务实现类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Service
public class SysSerialServiceImpl extends ServiceImpl<SysSerialMapper, SysSerial> implements ISysSerialService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public String getNextId(String serialKey) {
        String dateStr = sdf.format(System.currentTimeMillis());
        QueryWrapper<SysSerial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("serial_key", serialKey);
        queryWrapper.eq("serial_date", dateStr);
        queryWrapper.eq("platform_code", DEFAULT_PLATFORM_CODE);
        SysSerial serial = this.baseMapper.selectOne(queryWrapper);
        if (serial == null) {
            serial = new SysSerial();
            serial.setSerialKey(serialKey);
            serial.setSerialDate(dateStr);
            serial.setSerialValue("1");
            this.baseMapper.insert(serial);
        } else {
            int preId = Integer.parseInt(serial.getSerialValue());
            int nextId = preId + 1;
            serial.setSerialValue(String.valueOf(nextId));
            this.baseMapper.updateById(serial);
        }
        return serial.getSerialValue();
    }
}
