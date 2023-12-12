package com.quickex.domain.systable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoTableForeignkey implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String tableId;

    private String name;

    private String field;

    private String quotedPattern;

    private String quotedTable;

    private String quotedField;

    private String deleteHappen;

    private String updateHappen;

    private String notes;


}
