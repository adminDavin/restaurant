package com.org.jp.service.user;

import com.alibaba.fastjson.JSONObject;
import com.org.jp.model.user.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户账号 服务类
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
public interface IUserAccountService extends IService<UserAccount> {
    /**
     * 根据用户编号刷新登录信息
     *
     * @param userCode
     * @return
     */
    JSONObject refreshUserObject(String userCode);
}
