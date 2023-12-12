package com.quickex.domain.user;

import lombok.Data;

import java.util.List;

@Data
public class MapMenuResult {

    private String id;
    private String title;
    private String icon;
    private String menuStyle;
    private String menuId;
    private String menuPid;
    private Integer menuType;
    private String menuName;
    private List<MapMenuResult> children;


    private Integer sort;

    private String devRoute;
    private String proRoute;
    private String routeName;
    private Integer routeType;


}
