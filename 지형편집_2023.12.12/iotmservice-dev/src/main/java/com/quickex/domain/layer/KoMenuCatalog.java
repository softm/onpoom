package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KoMenuCatalog
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String parentId;

    private String isLeaf;

    @TableField("\"name\"")
    private String name;

    @TableField("\"comment\"")
    private String comment;

    private Integer sort;

    private String provinceId;

    private String imgPath;

    private String serviceState;

    @TableField(exist = false)
    private List<KoMenuCatalog> children = new ArrayList<KoMenuCatalog>();


    @TableField(exist = false)
    private String createUser;

}
