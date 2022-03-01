package com.org.jp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class UserAppointDTO implements Serializable {

    private String id;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户名称
     */
    @NotBlank(message = "名称不能为空")
    private String userName;

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantCode;

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String merchantName;

    /**
     * 预约时间
     */
    @NotNull(message = "预约时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointTime;

    /**
     * 预约时段
     */
    @NotBlank(message = "预约时段不能为空")
    private String appointRange;

    /**
     * 收件邮箱
     */
    @NotBlank(message = "预约时段不能为空")
    private String receiveEmail;

    /**
     * 预约联系电话
     */
    @NotBlank(message = "预约联系方式不能为空")
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
     * 备注
     */
    private String remark;
}
