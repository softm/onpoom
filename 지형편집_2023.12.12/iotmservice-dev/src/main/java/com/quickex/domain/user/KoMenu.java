package com.quickex.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class KoMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String icon;

    private String menuName;

    private String menuPath;

    private Integer menuState;

    private String menuDescribe;

    private String pid;

    private Integer orderNum;

    private Integer menuType;

    private Integer sysType;

    private String menuStyle;

    @TableField(exist = false)
    private List<KoMenu> children;

    @TableField(exist = false)
    private Boolean isChecked;

    @TableField(exist = false)
    private List<String> ids;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String account;

    @TableField(exist = false)
    private Integer sort;

    private String menuData1;

    private String menuData2;

    private String menuData3;

    private Integer menuData4;

}
