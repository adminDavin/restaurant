package com.org.jp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.org.jp.service.user.IUserAccountService;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.xy.api.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static com.org.jp.util.CommonUtil.DEFAULT_PLATFORM_CODE;

/**
 * mp config
 *
 * @author juny
 */
@Configuration
@MapperScan({"com.org.jp.mapper"})
@EnableTransactionManagement
public class MybatisPlusConfig {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 分页
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor paginationInterceptor = new MybatisPlusInterceptor();
        /**
         * 分页插件
         */
        paginationInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        /**
         * 乐观锁
         */
        paginationInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        /**
         * SQL规范性，暂时不成熟
         */
//        IllegalSQLInnerInterceptor illegalSQLInnerInterceptor = new IllegalSQLInnerInterceptor();
//        illegalSQLInnerInterceptor.setProperties();
//        paginationInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return paginationInterceptor;
    }

    /**
     * 自动值插入
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            private String columnUpdateKey = "updateTime";


            @Override
            public void insertFill(MetaObject metaObject) {
                String columnPlatformCodeKey = "platformCode";
                String columnCreateKey = "createTime";
                String columnDelFlag = "delFlag";
                String columnVersion = "version";
                String columnCreateUserCodeKey = "createUserCode";
                String columnCreateUserNameKey = "createUserName";
                String userName = "";
                String userCode = "";
                String authorization = request.getHeader("authorization");
                if (StringUtil.isNotEmpty(authorization)) {
                    JSONObject loginObject = (JSONObject) redisTemplate.opsForValue().get(authorization);
                    // 尝试刷新用户对象缓存操作
                    if (loginObject == null && StringUtil.isNotEmpty(authorization)) {
                        loginObject = userAccountService.refreshUserObject(authorization.split("_")[1]);
                    }
                    if (loginObject != null) {
                        userName = loginObject.getString("userName");
                        userCode = loginObject.getString("userCode");
                    }
                }
                LocalDateTime now = LocalDateTime.now();
                if (metaObject.hasSetter(columnCreateKey)) {
                    this.setFieldValByName(columnCreateKey, now, metaObject);
                }
                if (metaObject.hasSetter(columnUpdateKey)) {
                    this.setFieldValByName(columnUpdateKey, now, metaObject);
                }
                if (metaObject.hasSetter(columnDelFlag)) {
                    this.setFieldValByName(columnDelFlag, false, metaObject);
                }
                if (metaObject.hasSetter(columnVersion)) {
                    this.setFieldValByName(columnVersion, 0, metaObject);
                }
                if (metaObject.hasSetter(columnPlatformCodeKey)) {
                    this.setFieldValByName(columnPlatformCodeKey, DEFAULT_PLATFORM_CODE, metaObject);
                }
                if (metaObject.hasSetter(columnCreateUserNameKey) && StringUtil.isNotEmpty(userName)) {
                    this.setFieldValByName(columnCreateUserNameKey, userName, metaObject);
                }
                if (metaObject.hasSetter(columnCreateUserCodeKey) && StringUtil.isNotEmpty(userCode)) {
                    this.setFieldValByName(columnCreateUserCodeKey, userCode, metaObject);
                }

            }

            @Override
            public void updateFill(MetaObject metaObject) {
                String columnUpdateUserCodeKey = "updateUserCode";
                String columnUpdateUserNameKey = "updateUserName";
                String userName = "";
                String userCode = "";
                String authorization = request.getHeader("authorization");
                if (StringUtil.isNotEmpty(authorization)) {
                    JSONObject loginObject = (JSONObject) redisTemplate.opsForValue().get(authorization);
                    if (loginObject != null) {
                        userName = loginObject.getString("userName");
                        userCode = loginObject.getString("userCode");
                    }
                }
                if (metaObject.hasSetter(columnUpdateKey)) {
                    this.setFieldValByName(columnUpdateKey, LocalDateTime.now(), metaObject);
                }
                if (metaObject.hasSetter(columnUpdateUserNameKey) && StringUtil.isNotEmpty(userName)) {
                    this.setFieldValByName(columnUpdateUserNameKey, userName, metaObject);
                }
                if (metaObject.hasSetter(columnUpdateUserCodeKey) && StringUtil.isNotEmpty(userCode)) {
                    this.setFieldValByName(columnUpdateUserCodeKey, userCode, metaObject);
                }
            }
        };
    }
}
