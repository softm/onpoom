package com.quickex.domain.systable.RequestDto;

import lombok.Data;

@Data
public class TableRowsEntity {

    private String tableName;

    private String name;
    private String comment;
    private String type;
    private Boolean isPK;
    private Boolean isNull;

    private String newName;

}
