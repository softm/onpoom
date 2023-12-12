package com.quickex.domain.mapfile;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoMapFileType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String parentId;

    private String name;

    private Integer sort;

    private String createUser;

    private Date createTime;

    private Integer isAdmin;

    @TableField(exist = false)
    private List<KoMapFileType> children = new ArrayList<KoMapFileType>();

}
