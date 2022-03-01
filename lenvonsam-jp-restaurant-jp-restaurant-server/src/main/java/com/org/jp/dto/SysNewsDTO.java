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

/**
 * 新闻DTO
 *
 * @author samy
 */
@Setter
@Getter
public class SysNewsDTO implements Serializable {

    private String id;

    /**
     * 新闻标题
     */
    @NotBlank(message = "新闻标题不能为空")
    private String newsTitle;

    /**
     * 新闻内容
     */
    @NotBlank(message = "新闻内容不能为空")
    private String newsContent;

    /**
     * 新闻缩略内容
     */
    private String newsThumbContent;

    /**
     * 新闻性质 1 集团 2 商户
     */
    private Integer newsProperty;

    /**
     * 商户编号
     */
    private String merchantCode;

    /**
     * 发布时间
     */
    @NotNull(message = "发布时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishDate;

    /**
     * 作者
     */
    private String newsAuthor;

    /**
     * 新闻缩略图
     */
    private String newsThumbUrl;
}
