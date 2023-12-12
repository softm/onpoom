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
public class KoLayerAccessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String layerProvinceCode;

    private String userProvinceCode;

    private String origin;

    private String userid;

    private String layerId;

    private Date time;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

}
