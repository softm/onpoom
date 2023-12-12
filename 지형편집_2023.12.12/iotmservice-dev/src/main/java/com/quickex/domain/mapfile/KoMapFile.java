package com.quickex.domain.mapfile;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoMapFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String fileId;

    private String treeId;

    private String fileSuffix;

    private String fileUrl;

    private Date createTime;

    private String createUser;

    private String fileName;

    private Integer fileSize;

    private Integer fileType;

//    private String fileLabel;

    private String remarks;

    private String isAdmin;

    private String imgPath;

    @TableField(exist = false)
    private Integer sortType;

    @TableField(exist = false)
    private Integer isCollection;  //0not   1use


    private String organizationId;

    @TableField(exist = false)
    private String organizationName;

    @TableField(exist = false)
    private String kssj;

    @TableField(exist = false)
    private String jssj;

    @TableField(exist = false)
    private String createUser1;

    //    @TableField(exist = false)
//    private List<KoMapFileLabelRelation> children = new ArrayList<>();

//    @TableField(exist = false)
//    private List<KoMapFileLabelRelation> children = new ArrayList<>();

//    @TableField(exist = false)
//    private List<KoMapFileLabelRelationDto> children = new ArrayList<>();

}
