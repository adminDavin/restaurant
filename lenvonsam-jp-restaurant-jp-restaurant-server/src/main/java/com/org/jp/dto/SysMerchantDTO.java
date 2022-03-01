package com.org.jp.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.xy.api.annotation.Email;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 商户DTO
 *
 * @author samy
 */
@Setter
@Getter
public class SysMerchantDTO implements Serializable {

    private String id;

    /**
     * 商户编号
     */
    private String merchantCode;

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String merchantName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 商户缩略图
     */
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
     * line网站地址
     */
    private String lineSite;

    /**
     * 地图地址
     */
    private String mapUrl;

    /**
     * 基础设施标识
     */
    private String infrastructureMark;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 商户地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 商户简介
     */
    private String intro;

    /**
     * 商户营业时间说明
     */
    private String openTimeInfo;

    /**
     * 图片集
     */
    private List<SysFileDTO> gallery;
}
