package com.org.jp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * SysCardDTO
 *
 * @author juny
 */
@Setter
@Getter
public class SysCardDTO implements Serializable {

    private String id;

    /**
     * 业务单据编号
     */
    @NotBlank(message = "业务单据编号不能为空")
    private String busiCode;

    /**
     * 业务单据名称
     */
    @NotBlank(message = "业务单据名称不能为空")
    private String busiName;

    /**
     * 业务单据类型
     */
    @NotBlank(message = "业务单据类型不能为空")
    private String busiType;

    /**
     * 详情标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 详情内容
     */
    @NotBlank(message = "详情内容不能为空")
    private String content;
}
