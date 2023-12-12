package com.quickex.domain.doc;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCulturalHeritageCreateSHPModel {

    private Geometry geom;
    private String areaName;
    private String planeValue;
    private String tiltValue;
    private String epsg;

    public String getAreaName(){
        if(areaName==null){ return ""; }
        return this.areaName;
    }

    public String getPlaneValue(){
        if(planeValue==null){ return ""; }
        return this.planeValue;
    }

    public String getTiltValue(){
        if(tiltValue==null){ return ""; }
        return this.tiltValue;
    }

    public String getEpsg(){
        if(epsg==null){ return ""; }
        return this.epsg;
    }
}
