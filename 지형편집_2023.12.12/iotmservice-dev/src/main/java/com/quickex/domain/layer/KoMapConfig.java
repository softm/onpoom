package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoMapConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal lat;

    private BigDecimal lng;

    private BigDecimal heading;

    private BigDecimal pitch;

    private BigDecimal roll;

    @TableId(value = "province", type = IdType.INPUT)
    private String province;


}
