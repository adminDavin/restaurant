package com.org.jp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * 商户预约设置DTO
 * @author samy
 */
@Setter
@Getter
public class SysMerchantAppointDTO implements Serializable {

    private String id;

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantCode;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 预约时间
     */
    @NotNull(message = "预约时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointTime;

    /**
     * 预约类型 1 禁止 2 有待商榷
     */
    private Integer appointType;
}
