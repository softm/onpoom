package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KoLayerService
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String serviceName;

    private String createUser;

    private String layerType;

    private String serviceUri;

    private String isDefault;

    private String serviceState;

    private String menuCatalogId;

    private Date createTime;

    private String treeId;

    private Integer sort;

    private String minX;
    private String minY;
    private String maxX;
    private String maxY;

    private Integer minLevel;
    private Integer maxLevel;

    private String tilesPath;

    private String uploadUser;

    private Date uploadTime;

    private Integer impactComparison;

    private Integer monomerSelection;

    private String templateUrl;

    private Integer colorMatchingSwitch;

    private Integer positioningFunctionSwitch;

    private String alias;

    private Integer editShpHeightSwitch;

    private String shpHeight;

    private String resourceType;
    private String organizationId;
    private String describe;
    private String catalogBigId;
    private String catalogSmallId;
    private String catalogLayerId;

    @TableField(exist = false)
    private String kssj;
    @TableField(exist = false)
    private String jssj;

    @TableField(exist = false)
    private String layerTypeName;
    @TableField(exist = false)
    private String organizationName;

    private String centerX;
    private String centerY;


    private Integer tilesVagueSwitch;
}
