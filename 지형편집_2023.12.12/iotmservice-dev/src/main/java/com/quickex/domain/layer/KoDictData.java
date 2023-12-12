package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KoDictData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Dictionary coding
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String dictCode;

    /**
     * Dictionary sort
     */
    private Integer dictSort;

    /**
     * Dictionary label
     */
    private String dictLabel;

    /**
     * Dictionary key value
     */
    private String dictValue;

    /**
     * Dictionary type
     */
    private String dictType;

    /**
     * Style properties (other style extensions)
     */
    private String cssClass;

    /**
     * Table dictionary style
     */
    private String listClass;

    /**
     * Default (y yes n no)
     */
    private String isDefault;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KoDictData)) return false;
        KoDictData that = (KoDictData) o;
        return Objects.equals(getDictLabel(), that.getDictLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDictLabel());
    }
}