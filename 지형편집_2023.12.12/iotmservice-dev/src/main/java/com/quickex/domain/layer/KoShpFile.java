package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.quickex.core.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KoShpFile
{
    private static final long serialVersionUID = 1L;

    private String fileId;

    @Excel(name = "original file name")
    private String fileDesc;

    @Excel(name = "Local file name")
    private String fileName;

    @Excel(name = "file extension")
    private String fileSuffix;

    @Excel(name = "file size", readConverterExp = "k=b")
    private String fileSize;

    @Excel(name = "File upload path")
    private String fileUploadPath;

    @Excel(name = "File decompression path")
    private String fileUnzipPath;

    @Excel(name = "Parent file ID")
    private String fileParentId;

    @Excel(name = "jsonData")
    private String fileShpJson;

    /** Delete identity (0 normal, 2 deleted) */
    private Long delFlag;

    @TableField(exist=false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist=false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
