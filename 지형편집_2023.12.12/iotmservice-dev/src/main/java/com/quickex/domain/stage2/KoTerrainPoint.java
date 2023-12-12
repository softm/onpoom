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
public class KoTerrainPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String number;

    private String geom;

    private String terrainHeight;

    private String editHeight;

    private String resultHeight;

    private String normalDistance;

    private String normalAngle;

    private Integer countType;

    private String elevation;

    private String inletWidth;

    private String polygonId;

    private String taskId;


}
