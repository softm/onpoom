package com.quickex.domain.user;

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
public class KoRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String roleType;

    private String roleName;

    private String roleDescribe;

    private String createUser;

    private Date createTime;

    private Date sortUpdateTime;

    @TableField(exist = false)
    private Boolean isChecked;

    @TableField(exist = false)
    private String account;

    @TableField(exist = false)
    private List<String> ids;


    private String proviceId;

    private String cityId;

    @TableField(exist = false)
    private String proviceName;

    @TableField(exist = false)
    private String cityName;

}
