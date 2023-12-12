package com.quickex.domain.systable.RequestDto;

import lombok.Data;

@Data
public class TableDataDetailEntity {

    /**
     *  1:string 2:number
     */
    private int type;

    private String key;
    private String value;
}
