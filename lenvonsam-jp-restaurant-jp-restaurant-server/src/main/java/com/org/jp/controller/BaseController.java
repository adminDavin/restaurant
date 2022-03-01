package com.org.jp.controller;

import com.org.jp.service.sys.ISysSerialService;
import com.org.jp.service.user.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author samy
 */
@Controller
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected ISysSerialService sysSerialService;
    @Autowired
    protected IUserAccountService userAccountService;
    @Autowired
    protected RedisTemplate redisTemplate;

    /**
     * @apiDefine ok
     * @apiSuccessExample {json} 出参示例
     * {
     *     "success": true,
     *     "message": "success"
     * }
     */
}
