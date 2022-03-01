package com.org.jp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 商品DTO
 *
 * @author samy
 */
@Setter
@Getter
public class SysProductDTO implements Serializable {

    private String id;

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
     * 商品编号
     */
    private String productCode;

    /**
     * 商品标题
     */
    @NotBlank(message = "商品标题不能为空")
    private String productTitle;

    /**
     * 商品副标题
     */
    @NotBlank(message = "商品副标题不能为空")
    private String productSubTitle;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品封面/背景
     */
    @NotBlank(message = "商品封面不能为空")
    private String productCoverUrl;

    /**
     * 商品类型  1 套餐 2 菜单
     */
    private Integer productType;

    /**
     * 商品详情地址
     */
    private String productDescUrl;

    /**
     * 商品状态
     */
    private Integer status;
}
