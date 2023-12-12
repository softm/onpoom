package com.quickex.domain.stage2;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.quickex.domain.layer.KoMenuCatalog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoTextureLibraryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String pid;

    private String name;

    private Integer sort;

    private String userAccount;

    private Date createTime;

    private Integer isPublic;

    private Integer typeLevel;


    @TableField(exist = false)
    private List<KoTextureLibraryType> children = new ArrayList<>();



}
