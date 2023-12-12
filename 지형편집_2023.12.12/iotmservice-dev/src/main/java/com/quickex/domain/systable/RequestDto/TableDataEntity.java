package com.quickex.domain.systable.RequestDto;

import lombok.Data;

import java.util.List;

@Data
public class TableDataEntity {

    private String tableName;

    /**
     *  1:string 2:number
     */
    private int type;
    private String key;
    private String value;

    List<TableDataDetailEntity> data;

}
