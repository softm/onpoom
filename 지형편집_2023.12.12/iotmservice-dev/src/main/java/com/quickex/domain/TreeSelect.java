package com.quickex.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quickex.domain.layer.KoDictType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String label;

    private String level;

    private String type;

    private String masterId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    private List<String> names;

    private String typeCode;

    private String typeName;


    public TreeSelect() {

    }
    public TreeSelect(KoDictType dictType) {
        this.id = dictType.getDictId();
        this.label = dictType.getDictName();
        this.children = dictType.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
