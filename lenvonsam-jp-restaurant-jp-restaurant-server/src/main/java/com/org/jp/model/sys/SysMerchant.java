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
 * 商户
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_merchant")
public class SysMerchant implements Serializable {

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
     * 邮箱
     */
    private String email;

    /**
     * 商户缩略图
     */
    @TableField("thumb_url")
    private String thumbUrl;

    /**
     * uber网站地址
     */
    private String uberSite;

    /**
     * facebook网站地址
     */
    private String facebookSite;

    /**
     * tw网站地址
     */
    private String twSite;

    /**
     * ins网站地址
     */
    private String insSite;

    /**
     * 地图截图地址
     */
    private String mapUrl;

    /**
     * line网站地址
     */
    private String lineSite;

    /**
     * 基础设施标识
     */
    private String infrastructureMark;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 商户地址
     */
    @TableField("address")
    private String address;

    /**
     * 经度
     */
    @TableField("longitude")
    private String longitude;

    /**
     * 纬度
     */
    @TableField("latitude")
    private String latitude;

    /**
     * 商户简介
     */
    @TableField("intro")
    private String intro;

    /**
     * 平台编号
     */
    @TableField(value = "platform_code", fill = FieldFill.INSERT)
    @JsonIgnore
    private String platformCode;

    /**
     * 删除标识 0 否 1 是
     */
    @TableField("del_flag")
    @TableLogic
    @JsonIgnore
    private Boolean delFlag;

    /**
     * 商户营业时间说明
     */
    @TableField("open_time_info")
    private String openTimeInfo;

    /**
     * 业务类型
     */
    @TableField("busi_type")
    private String busiType;

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
