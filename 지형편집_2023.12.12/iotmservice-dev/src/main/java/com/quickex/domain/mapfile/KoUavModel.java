package com.quickex.domain.mapfile;

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
public class KoUavModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String name;

    private String style;

    private String size;

    private String batteryCapacity;

    private String flightTime;

    private String flightDistance;

    private String pixel;

    private String weight;

    private String createUser;

    private Date createTime;

    private String fileName;

    private Long fileSize;

    private String fileSuffix;

    private String fileUrl;

    private String imgUrl;


    private String organizationId;

    @TableField(exist = false)
    private String organizationName;

    @TableField(exist = false)
    private String kssj;

    @TableField(exist = false)
    private String jssj;

}
