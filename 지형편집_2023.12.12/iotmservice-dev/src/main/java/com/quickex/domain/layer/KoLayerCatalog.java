package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
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
public class KoLayerCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String parentId;

    @TableField("\"name\"")
    private String name;

    @TableField("\"comment\"")
    private String comment;

    private Integer sort;

    private String imgPath;

    private String bigId;

    private String smallId;

    private Integer catalogType;

    private String createUser;

    private Date createTime;

    private String organizationId;

    @TableField(exist = false)
    private List<KoMenuCatalog> children = new ArrayList<>();

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private Boolean isChecked;

    @TableField(exist = false)
    private List<String> ids;

//    private String centerX;
//    private String centerY;


    @TableField(exist = false)
    private String userAccount;
}
