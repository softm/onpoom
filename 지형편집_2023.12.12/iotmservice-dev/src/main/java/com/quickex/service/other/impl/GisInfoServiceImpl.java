package com.quickex.service.other.impl;

import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.service.other.GisInfoService;
import com.quickex.utils.WKTUtil;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.union.CascadedPolygonUnion;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.WKTWriter2;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.operation.buffer.BufferOp;
import com.vividsolutions.jts.algorithm.PointLocator;

import javax.xml.crypto.dsig.TransformException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GisInfoServiceImpl implements GisInfoService {

    @Autowired
    private AppConfig appConfig;

    public R pointBuffer(JSONObject par){
        try {
            String wktString = par.getString("wkt");
            Integer distance = par.getInteger("distance");
            Geometry geometry = WKTUtil.wktToGeom(wktString);
            double degree = distance / (2 * Math.PI * 6371004) * 360;
            BufferOp bufOp = new BufferOp(geometry);
            bufOp.setEndCapStyle(BufferOp.CAP_ROUND);
            //bufOp.setQuadrantSegments(5);
            Geometry bg = bufOp.getResultGeometry(degree);
            JSONObject result = new JSONObject();
            result.put("wkt", bg.toString());
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R lineBuffer(JSONObject par){
        try {
            String wktString = par.getString("wkt");
            Integer distance = par.getInteger("distance");
            Geometry geometry = WKTUtil.wktToGeom(wktString);
            double degree = distance / (2 * Math.PI * 6371004) * 360;
            BufferOp bufOp = new BufferOp(geometry);
            bufOp.setEndCapStyle(BufferOp.CAP_ROUND);
            //bufOp.setQuadrantSegments(5);
            Geometry bg = bufOp.getResultGeometry(degree);
            JSONObject result = new JSONObject();
            result.put("wkt", bg.toString());
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R polygonBuffer(JSONObject par){
        try {
            String wktString = par.getString("wkt");
            Integer distance = par.getInteger("distance");
            Geometry geometry = WKTUtil.wktToGeom(wktString);
            double degree = distance / (2 * Math.PI * 6371004) * 360;
            BufferOp bufOp = new BufferOp(geometry);
            bufOp.setEndCapStyle(BufferOp.CAP_ROUND);
            //bufOp.setQuadrantSegments(5);
            Geometry bg = bufOp.getResultGeometry(degree);
            JSONObject result = new JSONObject();
            result.put("wkt", bg.toString());
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R contain1(JSONObject par){
        try {
            Double x = par.getDouble("x");
            Double y = par.getDouble("y");
            String wktString = par.getString("wkt");
            Geometry geometry = WKTUtil.wktToGeom(wktString);
            Coordinate point = new Coordinate(x,y);
            PointLocator a=new PointLocator();
            boolean res = a.intersects(point, geometry);
            JSONObject result = new JSONObject();
            result.put("result", res);
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R contain2(JSONObject par){
        try {
            Double x = par.getDouble("x");
            Double y = par.getDouble("y");
            String wktString = par.getString("wkt");
            Geometry geometry = WKTUtil.wktToGeom(wktString);
            Coordinate point = new Coordinate(x,y);
            PointLocator a=new PointLocator();
            boolean res = a.intersects(point, geometry);
            JSONObject result = new JSONObject();
            result.put("result", res);
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R getArea(JSONObject par) {
        try {
            String wktString = par.getString("wkt");
            Geometry geometry = WKTUtil.wktToGeom(wktString);
            CoordinateReferenceSystem source = CRS.decode("CRS:84");
            CoordinateReferenceSystem target = CRS.decode("EPSG:3857");
            MathTransform transform = CRS.findMathTransform(source, target, true);
            Geometry transform1 = JTS.transform(geometry, transform);
            double area = transform1.getArea();
            double area1 = (double) Math.round(area * 100) / 100;
            JSONObject result = new JSONObject();
            result.put("area", area1);
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }


    public R dissolver(JSONObject par){

        try{

            String filePath = par.getString("filePath");

            // Load GeoJSON file
            File file = new File(filePath);

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
//            for(Geometry geometry : mergedGeometryList) {
//                System.out.println(new WKTWriter2().write(geometry));
//            }

            List<String> result = new ArrayList<>();
            for(Geometry geometry : mergedGeometryList) {
                result.add(new WKTWriter2().write(geometry));
            }

            return R.success(result);
        }catch (Exception ex){
            log.error("ex:",ex);
            return R.error();
        }

    }





    public static void main(String[] args) {
        try{
            //Create a line
//        Coordinate[] coordinates4 = new Coordinate[] {
//                new Coordinate(116.664496,40.387171),
//                new Coordinate(116.663463,40.387158),
//        };
//
//        GeometryFactory gf=new GeometryFactory();
//        Geometry gfLineString = gf.createLineString(coordinates4);

            //***
//            String dian ="POINT (395.94918206334114 35.388817636074265)";
//            String xian="LINESTRING (395.9489929676056 35.38877007650268, 395.94940334558487 35.388785383034524)";
//            String mian="MULTIPOLYGON (((127.2888615373538 35.28128083527717, 127.28962499837259 35.28125608704276, 127.28956856323195 35.2808983237999, 127.28885000663698 35.280962977360595, 127.28878921249735 35.28120421661427, 127.2888615373538 35.28128083527717)))";
////            String mian1="POLYGON ((395.9490479528904 35.388811622796645, 395.94903856515884 35.38866949064968, 395.9493564069271 35.38867933057564, 395.9493510425091 35.38882583599757, 395.9490479528904 35.388811622796645))";
////
//            Geometry gfLineString = WKTUtil.wktToGeom(dian);
//            //gfLineString.getCoordinates()
//            double degree = 8 / (2 * Math.PI * 6371004) * 360;
//            //Buffer establishment
//            BufferOp bufOp = new BufferOp(gfLineString);
//            bufOp.setEndCapStyle(BufferOp.CAP_ROUND);
//            //bufOp.setQuadrantSegments(5);
//
//
//            Geometry bg = bufOp.getResultGeometry(degree);
//
//            System.out.println("getArea---"+ getArea(bg));
//
//            System.out.println("buf---"+bg.toString());
//            //Judge whether the point is within the polygon
//            //Coordinate point = new Coordinate(116.663609,40.387187);
//
//            Coordinate point = new Coordinate(395.94933092594147,35.389006234099284);
//
//            PointLocator a=new PointLocator();
//
//            boolean p1 = a.intersects(point, bg);
//            if (p1) {
//                System.out.println("point1 in:" + p1);
//            } else {
//                System.out.println("point1 not in:" + p1);
//            }
            //***
//
//            Geometry geometry = WKTUtil.wktToGeom("POLYGON ((477476.3910752534 39.90144850634788, 477476.3910752534 39.900753015837594, 477476.3920032978 39.90078182327698, 477476.3920032978 39.90146908288268, 477476.3910752534 39.90144850634788))");
//            String str = WKTUtil.geomToWkbHex(geometry);
//            System.out.println("wkb str:" + str);


            //test111
//            GeometryFactory gf=new GeometryFactory();
//            Geometry gfLineString = gf.createLineString(coordinates4);
//            double degree = 10 / (2*Math.PI*6371004)*360;
//
//            BufferOp bufOp = new BufferOp(gfLineString);
//            bufOp.setEndCapStyle(BufferOp.CAP_BUTT);
//            Geometry bg = bufOp.getResultGeometry(degree);
//            System.out.println("bg---"+bg);
//
//            Coordinate point = new Coordinate(116.663609,40.387187);
//            PointLocator a=new PointLocator();
//            boolean p1=a.intersects(point, bg);
            //end test111

//            String geo111="{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[471736.318359375,-27.269278554175017],[471736.23046875006,-28.09136628140693],[471738.27941894525,-28.115593833316762],[471738.5485839844,-27.23997867180821],[471736.318359375,-27.269278554175017]]]}}]}";
//
//            String gjson1="{\"type\":\"FeatureCollection\",\"name\":\"test\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":4,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"大禹渡枢纽沉沙池\",\"ANGLE\":0.0,\"REMARK\":null,\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000004\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[477645.154099999926984,3836587.447000000625849]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":6,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"西干一级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000006\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[462958.316899999976158,3839850.515699999406934]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":7,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"西干二级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000007\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[462243.98369999974966,3840151.3158]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":8,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北一干一级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000008\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[480559.43819999974221,3843021.237700000405312]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":9,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北一干二级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000009\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[480627.382100000046194,3844228.900000000372529]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":10,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北一干三级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000010\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[480753.410199999809265,3845326.18019999936223]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":11,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北一干四级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000011\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[480794.111600000411272,3846419.536800000816584]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":12,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北二干一级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000012\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[469730.441399999894202,3841083.36739999987185]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":13,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北二干二级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000013\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[469085.610399999655783,3842560.002000000327826]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":14,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"北二干三级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000014\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[469002.786000000312924,3844283.669299999251962]}},{\"type\":\"Feature\",\"properties\":{\"OBJECTID\":15,\"PROVINCIAL\":\"山西省\",\"CITY\":\"运城市\",\"COUNTY\":\"芮城县\",\"IRRNAME\":\"大禹渡灌区\",\"IRRTYPE\":\"大型灌区\",\"NAME\":\"礼教二级泵站沉砂池\",\"ANGLE\":0.0,\"REMARK\":\"前池\",\"TYPE\":\"E_BASIN\",\"ID\":\"E_BASIN_00000015\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[464592.689000000245869,3832185.229900000616908]}}]}";
//            Geometry geometry = WKTUtil.geoJsonToGeometry(geo111);
//
//            log.info("TEST>>>:" + geometry.toString());


           //String test="MULTIPOLYGON (((127.2888615373538 35.28128083527717, 127.28962499837259 35.28125608704276, 127.28956856323195 35.2808983237999, 127.28885000663698 35.280962977360595, 127.28878921249735 35.28120421661427, 127.2888615373538 35.28128083527717)))";
//           Geometry geom = WKTUtil.wktToGeom(test);
//           log.info("result:" + geom.getGeometryType());
//            log.info("result:" + geom.getCoordinates());

         //   log.info("result:" + WKTUtil.wktToJson(test));




//            String wkt ="POINT (110.68652579950563 34.700392656835014)";
//            Geometry geom = WKTUtil.wktToGeom(wkt);

//            File file = new File("D:/XX/A/XX.shp");
//            Map<String, Object> map = new HashMap<>();
//            map.put("url", file.toURI().toURL());
//            DataStore dataStore = DataStoreFinder.getDataStore(map);
//            String typeName = dataStore.getTypeNames()[0];
//            FeatureSource<SimpleFeatureType, SimpleFeature> featureSource =
//                    dataStore.getFeatureSource(typeName);
//            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = featureSource.getFeatures();
//            String epsgCode = featureSource.getInfo().getCRS().toWKT();
//            System.out.println("EPSG Code: " + epsgCode);



        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    public static double getArea(Geometry geometry){
        CoordinateReferenceSystem source = null;
        try {
            source = CRS.decode("CRS:84");
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        CoordinateReferenceSystem target = null;
        try {
            target = CRS.decode("EPSG:3857");
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        MathTransform transform = null;
        try {
            transform = CRS.findMathTransform(source, target, true);
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        Geometry transform1 = null;
        try {
            transform1 = JTS.transform(geometry, transform);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double area = transform1.getArea();
        return area;
    }



}
