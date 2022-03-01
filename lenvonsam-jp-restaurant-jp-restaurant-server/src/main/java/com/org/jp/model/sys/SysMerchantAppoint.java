package com.org.jp.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户预约设置
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_merchant_appoint")
public class SysMerchantAppoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

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
     * 预约类型 1 禁止 2 有待商榷
     */
    @TableField("appoint_type")
    private Integer appointType;

    /**
     * 删除标识 0 否 1 是
     */
    @TableField("del_flag")
    @TableLogic
    @JsonIgnore
    private Boolean delFlag;

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
