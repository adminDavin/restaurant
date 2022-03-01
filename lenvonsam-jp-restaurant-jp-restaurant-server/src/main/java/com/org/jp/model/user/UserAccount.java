package com.org.jp.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户账号
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 用户编号
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 登录密码(sha1加密)
     */
    @TableField("user_pwd")
    @JsonIgnore
    private String userPwd;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 登录名称
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 用户类型  1 内部  2 外部
     */
    private Integer userType;


    /**
     * 启用状态 1 启用 2 停用 3 冻结
     */
    @TableField("status")
    private Integer status;

    /**
     * 平台编号
     */
    @TableField(value = "platform_code", fill = FieldFill.INSERT)
    @JsonIgnore
    private String platformCode;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    /**
     * 创建人编号
     */
    @TableField(value = "create_user_code", fill=FieldFill.INSERT)
    private String createUserCode;

    /**
     * 创建人名称
     */
    @TableField(value="create_user_name", fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

    /**
     * 修改人编号
     */
    @TableField(value = "update_user_code", fill = FieldFill.UPDATE)
    private String updateUserCode;

    /**
     * 修改人名称
     */
    @TableField(value = "update_user_name", fill = FieldFill.UPDATE)
    private String updateUserName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
