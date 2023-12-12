package com.quickex.domain.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoUserCulturalHeritageShp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String pid;

    private String areaName;

    /**
     * {"point":"","linestring":"","polygon":""}
     */
    private String filePath;

    private String pointFile;

    private String linestringFile;

    private String polygonFile;

    private String wkt;

    private String createUser;

    private Date createTime;

    private String planeValue;

    private String tiltValue;

    /**
     * 1:point  2:linestring  3:polygon
     */
    private Integer fileType;

    private String epsg;

    @TableField(exist = false)
    private List<KoUserCulturalHeritageShp> children;


}
