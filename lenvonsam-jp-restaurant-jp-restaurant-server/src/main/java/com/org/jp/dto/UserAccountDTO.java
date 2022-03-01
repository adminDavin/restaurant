package com.org.jp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户账号DTO
 *
 * @author samy
 */
@Setter
@Getter
public class UserAccountDTO implements Serializable {

    private String id;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 登录密码(sha1加密)
     */
    private String userPwd;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 登录名称
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;


    /**
     * 启用状态 1 启用 2 停用 3 冻结
     */
    private Integer status;

    /**
     * 用户类型  1 内部  2 外部
     */
    private Integer userType;
}
