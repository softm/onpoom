package com.quickex.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LayerAndTypeSelect implements Serializable {
    private String id;
    private String name;
    private Integer type;          //0:type    1:layer
    private Boolean isChecked;
    private List<LayerAndTypeSelect> children;
}
