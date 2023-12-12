package com.quickex.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String pid;

    private String itemName;

    private Integer itemType;

    private String createUser;

    private Date createTime;

    private String itemCode;

    private String proviceId;

    private String cityId;

    @TableField(exist = false)
    private String proviceName;

    @TableField(exist = false)
    private String cityName;

    @TableField(exist = false)
    private List<KoOrganization> children;


}
