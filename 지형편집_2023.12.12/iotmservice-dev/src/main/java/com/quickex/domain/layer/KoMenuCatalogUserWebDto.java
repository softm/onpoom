package com.quickex.domain.layer;

import lombok.Data;

import java.util.List;

@Data
public class KoMenuCatalogUserWebDto {

    private String treeId;

    private String key;

    private String title;

    private String url;

    private String isDefault;

    private String serviceId;

    private Integer dataType;

    private String layerType;

    private Integer sort;

    //The classification is true and the file is false
    private boolean disableCheckbox;

    private List<KoMenuCatalogUserWebDto> children;

    private String minX;
    private String minY;
    private String maxX;
    private String maxY;
    private Integer minLevel;
    private Integer maxLevel;

    private Integer impactComparison;

    private Integer monomerSelection;

    private String templateUrl;

    private Integer colorMatchingSwitch;

    private Integer positioningFunctionSwitch;

    private String alias;

    private Integer editShpHeightSwitch;

    private String shpHeight;

    private String imgPath;

    private Boolean isCollection;

    private Integer catalogType;



    //cs used [input]
    private String centerX;
    private String centerY;

    private Integer tilesVagueSwitch;

}
