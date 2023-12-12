package com.quickex.domain.layer;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class KoMenuCatalogUserWeb {

    private String id;

    private String parentId;

    private String isLeaf;

    private String name;

    private String comment;

    private Integer sort;

    private List<KoLayerService> children = new ArrayList<>();

    private List<KoMenuCatalog> treeChildren = new ArrayList<KoMenuCatalog>();

    private String key;

    private String title;

    private String serviceId;

    private String url;

}
