package com.quickex.domain.stage2;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoTerrainBid implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "number", type = IdType.ID_WORKER_STR)
    private String number;


}
