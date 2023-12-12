package com.quickex.domain.stage2;

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
public class KoTerrainPolygon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String number;

    private String name;

    private Integer terrainType;

    private Integer terrainSelect;

    private String geom;

    private Date registTime;

    private String registUser;

    private String planeHeight;

    private String tunnelHeight;

    private String textureId;

    private String textureUrl;

    private String landNo;

    private String shpPath;

    private String taskId;

    @TableField(exist = false)
    private List<KoTerrainModel> models;

}
