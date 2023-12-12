package com.quickex.service.other.impl;



import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.operation.union.CascadedPolygonUnion;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.WKTWriter2;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.FeatureType;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Test111 {

    public static void main(String[] args) {

        try{

            // Load GeoJSON file
            File file = new File("D:/A_TEST/output1.geojson");

            // Reading GeoJSON files using FeatureJSON
            FeatureJSON featureJSON = new FeatureJSON();
            FeatureCollection featureCollection = featureJSON.readFeatureCollection(file);

            // Get all geometric objects
            List<Geometry> geometryList = new ArrayList<>();
            FeatureIterator<SimpleFeature> featureIterator = featureCollection.features();
            while(featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                geometryList.add(geometry);
            }
            featureIterator.close();

            // Print all geometric objects
//            for(Geometry geometry : geometryList) {
//                System.out.println(geometry);
//            }

            // Merge Polygons with intersecting relationships into one MultiPolygon
            CascadedPolygonUnion polygonUnion = new CascadedPolygonUnion(geometryList);
            Geometry unionGeometry = polygonUnion.union();
            MultiPolygon multiPolygon = (MultiPolygon) unionGeometry;

            // Detach and add all Polygons in MultiPolygon to the List
            List<Geometry> mergedGeometryList = new ArrayList<>();
            for(int i = 0; i < multiPolygon.getNumGeometries(); i++) {
                Geometry geometry = multiPolygon.getGeometryN(i);
                if(geometry instanceof Polygon) {
                    mergedGeometryList.add(geometry);
                }
            }

            // Print a list of merged Geometry objects
            for(Geometry geometry : mergedGeometryList) {
                System.out.println(new WKTWriter2().write(geometry));
            }

        }catch (Exception ex){
            log.error("error",ex);
        }

    }


}




