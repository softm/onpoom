package com.quickex.domain.systable.RequestDto;

import lombok.Data;


@Data
public class TableUserAuthorityEntity {

    private String userName;

    private String tableName;

    private String insert;

    private String delete;

    private String update;

    private String select;
    

}
