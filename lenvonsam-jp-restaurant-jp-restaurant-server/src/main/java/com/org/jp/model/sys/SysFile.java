package com.org.jp.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author samy
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_file")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件链接
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件业务类型
     */
    @TableField("file_busi_type")
    private String fileBusiType;

    /**
     * 文件业务编号
     */
    @TableField("file_busi_code")
    private String fileBusiCode;

    /**
     * 文件类型 1 图片 2 视频
     */
    @TableField("file_type")
    private Integer fileType;

    /**
     * 文件云端名称
     */
    @TableField("file_cloud_name")
    private String fileCloudName;

    /**
     * 文件来源 1 七牛 2 阿里云 3 本地
     */
    @TableField("file_source")
    private Integer fileSource;

    /**
     * 平台编号
     */
    @TableField(value = "platform_code", fill = FieldFill.INSERT)
    @JsonIgnore
    private String platformCode;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

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


}
