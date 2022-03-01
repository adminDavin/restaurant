package com.org.jp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * NewsListVo
 *
 * @author juny
 */
@Setter
@Getter
public class NewsListVo implements Serializable {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishDate;
    private String newsTitle;
    private Integer newsProperty;
    private String merchantCode;
    @JsonIgnore
    private String platformCode;
}
