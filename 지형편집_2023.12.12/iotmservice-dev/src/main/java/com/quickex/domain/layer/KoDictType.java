package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KoDictType {
    private static final long serialVersionUID = 1L;

    /**
     * Dictionary primary key
     */

    @TableId(type = IdType.ID_WORKER_STR)
    private String dictId;

    /**
     * Dictionary name
     */
    private String dictName;

    /**
     * Dictionary type
     */
    private String dictType;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Parent department ID
     */
    private Long parentId;

    /**
     * Ancestor list
     */
    private String ancestors;

    /**
     * subsidiary department
     */
    @TableField(exist=false)
    private List<KoDictType> children = new ArrayList<KoDictType>();






}
