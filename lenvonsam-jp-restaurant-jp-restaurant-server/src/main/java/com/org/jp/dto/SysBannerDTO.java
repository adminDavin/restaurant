package com.org.jp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * bannerDTO
 *
 * @author samy
 */
@Setter
@Getter
public class SysBannerDTO implements Serializable {

    private String id;

    /**
     * 名称
     */
    private String bannerName;

    /**
     * 云图片值
     */
    @NotBlank(message = "云图片值不能为空")
    private String cloudName;

    /**
     * 地址
     */
    @NotBlank(message = "图片地址不能为空")
    private String bannerUrl;

    /**
     * 方位区域
     */
    private String sectionArea;

    /**
     * 外部链接
     */
    private String linkUrl;

    /**
     * 外链标识 1 是 0 否
     */
    private Boolean linkFlag;

    /**
     * 启用状态 1 启用 2 停用
     */
    private Integer status;

}
