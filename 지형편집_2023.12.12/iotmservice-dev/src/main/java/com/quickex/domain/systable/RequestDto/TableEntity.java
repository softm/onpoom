package com.quickex.domain.systable.RequestDto;

import lombok.Data;

import java.util.List;

@Data
public class TableEntity {
    String tableName;
    String comment;
    List<TableRowsEntity> rows;
}
