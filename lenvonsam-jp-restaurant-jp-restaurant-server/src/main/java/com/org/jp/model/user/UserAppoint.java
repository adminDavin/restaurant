package com.org.jp.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户预约信息
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_appoint")
public class UserAppoint implements Serializable {

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
     * 商户编号
     */
    @TableField("merchant_code")
    private String merchantCode;

    /**
     * 商户名称
     */
    @TableField("merchant_name")
    private String merchantName;

    /**
     * 预约时间
     */
    @TableField("appoint_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointTime;

    /**
     * 预约时段
     */
    @TableField("appoint_range")
    private String appointRange;

    /**
     * 收件邮箱
     */
    @TableField("receive_email")
    private String receiveEmail;

    /**
     * 预约联系电话
     */
    @TableField("appoint_phone")
    private String appointPhone;

    /**
     * 成人数量
     */
    private int adultCount;

    /**
     * 小孩数量
     */
    private int childCount;

    /**
     * 套餐编号
     */
    private String productCode;

    /**
     * 套餐名称
     */
    private String productTitle;

    /**
     * 预约状态 1 待处理 2 已发送 3待确认
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    /**
     * 创建人编号
     */
    @TableField(value = "create_user_code", fill = FieldFill.INSERT)
    private String createUserCode;

    /**
     * 创建人名称
     */
    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
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
