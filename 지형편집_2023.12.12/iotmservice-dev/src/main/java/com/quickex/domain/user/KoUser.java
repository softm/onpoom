package com.quickex.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "account", type = IdType.INPUT)
    private String account;

    private String password;

    private String cityId;

    private String areaId;

    private String organizationId;

    private String departmentId;

    private String roleId;

    private Integer userState;

    private String createUser;

    private Date createTime;


    @TableField(exist = false)
    private String cityName;

    @TableField(exist = false)
    private String areaName;

    @TableField(exist = false)
    private String organizationName;

    @TableField(exist = false)
    private String departmentName;



    @TableField(exist = false)
    private Integer sysType;

}
