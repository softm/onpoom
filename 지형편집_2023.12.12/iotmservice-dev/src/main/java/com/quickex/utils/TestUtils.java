package com.quickex.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ddf.NullEscherSerializationListener;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.GeoJSON;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.kabeja.batik.tools.SAXPDFSerializer;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.SAXGenerator;
import org.kabeja.xml.SAXSerializer;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class TestUtils {

    public static Date getLastDate(Date date,int h,int m,int s) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, s);
        return calendar.getTime();
    }

    @Data
    public static class MyObject{

        public MyObject(String id,String name){
            this.id = id;
            this.name = name;
        }

        private String id;
        private String name;
    }

    @Data
    public static class MyObjectA{

        public MyObjectA(String id,String name){
            this.idd = id;
            this.namee = name;
        }

        private String idd;
        private String namee;
    }


    public static List<String> getDatesInMonth(String targetMonth) {
        List<String> dateList = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(targetMonth + "-01");
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!startDate.isAfter(endDate)) {
            dateList.add(startDate.format(formatter));
            startDate = startDate.plusDays(1);
        }

        return dateList;
    }


    public static void main(String[] paramert) {

        try{

            JSONObject userData = new JSONObject();
            userData.put("t1","11");
            userData.put("t2","22");

            Geometry geometry = WKTUtil.wktToGeom("POLYGON((127.34770231083046 36.00225704205016,127.35219388725106 36.00225704205016,127.35219388725106 35.998623300677984,127.34770231083046 35.998623300677984,127.34770231083046 36.00225704205016))");
            //geometry.setUserData(userData);


            //Geometry geometry = WKTUtil.wktToGeom("POLYGON((127.34770231083046 36.00225704205016,127.35219388725106 36.00225704205016,127.35219388725106 35.998623300677984,127.34770231083046 35.998623300677984,127.34770231083046 36.00225704205016))");
            //geometry.setUserData(userData);

            SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
            typeBuilder.setName("MyFeatureType");
            typeBuilder.add("geometry", Geometry.class);
//            typeBuilder.add("data", JSONObject.class);
            typeBuilder.add("t1", String.class);
            typeBuilder.add("t2", Object.class);
            typeBuilder.add("t3", String.class);
            typeBuilder.add("t4", Object.class);
            typeBuilder.add("properties", Object.class);
            SimpleFeatureType featureType = typeBuilder.buildFeatureType();
            List<SimpleFeature> features = new ArrayList<>();
            JSONObject  ccc = new JSONObject();
            ccc.put("aa","aa");
            ccc.put("bb",11);
            //JSONArray ta = new JSONArray();
            List<String> ta = new ArrayList<>();

            for (int i = 0; i < 2; i++) {
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
                featureBuilder.set("geometry",geometry);
                featureBuilder.set("t2","qweqweqweqwe");
                featureBuilder.set("t1","aa");
                featureBuilder.set("t3","");
                featureBuilder.set("t4",ccc.toJSONString());
                featureBuilder.set("t2",ta); //---userData
                //featureBuilder.set("properties",userData); //---userData
                //SimpleFeature feature = featureBuilder.buildFeature(String.valueOf(i));
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }

            SimpleFeatureCollection featureCollection = new ListFeatureCollection(featureType, features);

            FeatureJSON featureJSON = new FeatureJSON(new GeometryJSON(14));

            //featureJSON.

            String geoJSONString = featureJSON.toString(featureCollection);
            System.out.println(geoJSONString);
           // System.out.println(geoJSONString.replace("\\",""));

        }catch (Exception ex){
            ex.printStackTrace();
        }

//        String targetMonth = "2023-08";
//        List<String> dateList = getDatesInMonth(targetMonth);
//
//        for (String date : dateList) {
//            System.out.println(date);
//        }


//        List<MyObject> list1 = new ArrayList<>();
//        List<MyObjectA> list2 = new ArrayList<>();
//
//        list1.add( new MyObject("111","111"));
//        list1.add( new MyObject("222","222"));
//        list1.add( new MyObject("333","333"));
//
//        list2.add( new MyObjectA("222","222"));
//        list2.add( new MyObjectA("333","333"));
//
//
//        List<String> commonIds = new ArrayList<>();
//
//        list1.stream().forEach(obj -> {
//            if (list2.stream().anyMatch(obj2 -> obj2.getIdd() == obj.getId())) {
//                commonIds.add(obj.getId());
//            }
//        });
//
//        for (String item : commonIds) {
//            System.out.println("out------------:" + item);
//        }


//        System.out.println("aabbcc");
//
//        Date date = new Date();
//        getLastDate(date,22,22,11);
//        System.out.println(date);
//        try{
//
////            InputStream in = new FileInputStream("D:/A_TEST/cad/draft1.dxf");
////             Parser dxfParser = DXFParserBuilder.createDefaultParser();
//
//
//            Parser dxfParser = ParserBuilder.createDefaultParser();
//            dxfParser.parse(new FileInputStream("D:/A_TEST/cad/draft1.dxf"), "EUC_KR");  //EUC_KR
//            DXFDocument doc = dxfParser.getDocument();
//
//            SAXGenerator generator = new SVGGenerator();
//            // generate into outputstream
//            // SVG
//             // SAXSerializer out = new SAXPDFSerializer();
//            // pdf
//               SAXSerializer out = new org.kabeja.batik.tools.SAXPDFSerializer();
//            // tiff
//            // org.kabeja.xml.SAXSerialzer out =
//            // org.kabeja.batik.tools.SAXTIFFSerializer();
//            // png
//            //SAXSerializer out =new SAXPNGSerializer();
//            // jpg
//            // org.kabeja.xml.SAXSerialzer out =
//            // org.kabeja.batik.tools.SAXJEPGSerializer();
//            OutputStream fileo = new FileOutputStream("D:/A_TEST/cad/LX111112.pdf");
//            // out.setOutputStream(response.getOutputStream()) //write direct to
//            // ServletResponse
//            out.setOutput(fileo);
//            // generate
//            generator.generate(doc, out, new HashMap());
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
    }
}
