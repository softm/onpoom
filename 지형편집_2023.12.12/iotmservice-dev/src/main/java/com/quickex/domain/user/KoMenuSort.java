package com.quickex.domain.user;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoMenuSort implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderNum;

    private String menuId;

    private Integer sortType;

    private String userId;

    private String roleId;

    @TableField(exist = false)
    private List<KoMenuSort> list;

    @TableField(exist = false)
    private String account;

    private Integer sysType;
}
