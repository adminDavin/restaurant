package com.org.jp.service.impl.user;

import com.org.jp.model.user.UserAppoint;
import com.org.jp.mapper.user.UserAppointMapper;
import com.org.jp.service.user.IUserAppointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户预约信息 服务实现类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Service
public class UserAppointServiceImpl extends ServiceImpl<UserAppointMapper, UserAppoint> implements IUserAppointService {

}
