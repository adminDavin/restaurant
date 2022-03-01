package com.org.jp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Setter
@Getter
public class SysFileDTO implements Serializable {

    private String id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件链接
     */
    @NotBlank(message = "链接地址不能为空")
    private String fileUrl;

    /**
     * 文件业务类型
     */
    private String fileBusiType;

    /**
     * 文件业务编号
     */
    private String fileBusiCode;

    /**
     * 文件类型 1 图片 2 视频
     */
    private Integer fileType;

    /**
     * 文件云端名称
     */
    @NotBlank(message = "云端文件名称不能为空")
    private String fileCloudName;
}
