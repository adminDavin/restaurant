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
 * 新闻
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_news")
public class SysNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 新闻标题
     */
    @TableField("news_title")
    private String newsTitle;

    /**
     * 新闻内容
     */
    @TableField("news_content")
    private String newsContent;

    /**
     * 新闻缩略内容
     */
    @TableField("news_thumb_content")
    private String newsThumbContent;

    /**
     * 新闻性质 1 集团 2 商户
     */
    @TableField("news_property")
    private Integer newsProperty;

    /**
     * 商户编号
     */
    @TableField("merchant_code")
    private String merchantCode;

    /**
     * 发布时间
     */
    @TableField("publish_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishDate;

    /**
     * 作者
     */
    @TableField("news_author")
    private String newsAuthor;

    /**
     * 新闻缩略图
     */
    @TableField("news_thumb_url")
    private String newsThumbUrl;

    /**
     * 业务类型
     */
    @TableField("busi_type")
    private String busiType;

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

    /**
     * 版本号(乐观锁)
     */
    @TableField("version")
    @Version
    private Integer version;

    /**
     * 状态 0 草稿 1 上架 2 下架
     */
    @TableField("status")
    private Integer status;


}
