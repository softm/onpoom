package com.quickex.service.other.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.mapper.layer.KoMenuCatalogMapper;
import com.quickex.mapper.systable.KoTableMapper;
import com.quickex.service.other.IKoReadGeoJson;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.FSZipUtils;
import com.quickex.utils.WKTUtil;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Slf4j
@Service
public class KoReadGeoJsonImpl implements IKoReadGeoJson {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private KoTableMapper koTableMapper;

    public R readTxt(MultipartFile file, String userAccount) {

        try {
            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/"+ folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            if (!path.isEmpty()) {
                File saveFile11 = new File(path);
                file.transferTo(saveFile11);
            }

            List<String> strs  = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
            String data = "";
            for (String item:strs) {
                data = data + item;
            }
            JSONObject result = JSON.parseObject(data);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R readGeoJson(MultipartFile file, String userAccount) {

        try {
            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/"+ folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            if (!path.isEmpty()) {
                File saveFile11 = new File(path);
                file.transferTo(saveFile11);
            }

            List<String> strs  = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
            String data = "";
            for (String item:strs) {
                data = data + item;
            }
            JSONObject result = JSON.parseObject(data);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R readSHP(MultipartFile file, String userAccount) {

        try {
            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }

            File saveFile11 = new File(path);
            file.transferTo(saveFile11);

            String unPath = path.substring(0, path.length() - 3);
            FSZipUtils.unzip(saveFile11, unPath, false);  //unzip

            String shpName = this.findShpName(unPath);

            if (shpName.isEmpty()) {
                return R.error("SHP file not found");
            }

            String shpPath = unPath + "/" + shpName;

            JSONObject data = JSON.parseObject(shapeToGeojson(shpPath));
            return R.success(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    private String findShpName(String folderPath) {
        File folder = new File(folderPath);
        File[] fs = folder.listFiles();
        for (File f : fs) {
            if (!f.isDirectory()) {
                String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));
                if (fileSuffix.toLowerCase().equals(".shp")) {
                    return f.getName();
                    //return f.getName().substring(0, f.getName().lastIndexOf("."));
                }
            }
        }
        return "";
    }

    private String shapeToGeojson(String shpPath) {
        String res = "";
        FeatureJSON fjson = new FeatureJSON(new GeometryJSON(14));
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("{\"type\": \"FeatureCollection\",\"features\": ");
            File file = new File(shpPath);
            ShapefileDataStore shpDataStore = null;
            shpDataStore = new ShapefileDataStore(file.toURL());
            Charset charset = Charset.forName("EUC_KR");
            shpDataStore.setCharset(charset);
            String typeName = shpDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = null;
            featureSource = shpDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection result = featureSource.getFeatures();
            SimpleFeatureIterator itertor = result.features();
            JSONArray array = new JSONArray();
            while (itertor.hasNext()) {
                SimpleFeature feature = itertor.next();
                StringWriter writer = new StringWriter();
                fjson.writeFeature(feature, writer);
                JSONObject json = JSON.parseObject(writer.toString());
                array.add(json);
            }
            itertor.close();
            sb.append(array.toString());
            sb.append("}");
            res = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public R objConvertToGltf(JSONObject par) {
        try {

            String inPath = appConfig.getUavModelPath() + par.getString("inPath");

            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + par.getString("userAccount") + "/" +folderName;
            String url = "/" + par.getString("userAccount")+"/"+ folderName + "/" + newName + ".gltf";

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String outPath = appConfig.getUavModelPath() + url;

            boolean runTime = System.getProperty("os.name").toLowerCase().contains("win");
            String npm = runTime ? "cmd /c " : "";

            String command = npm + "obj2gltf -i " + inPath + " -o " + outPath;
            ByteArrayOutputStream susStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            CommandLine commandLine = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
            exec.setStreamHandler(streamHandler);
            int code = exec.execute(commandLine);
            System.out.println("result code: " + code);
            String suc = susStream.toString("UTF-8");
            String err = errStream.toString("UTF-8");

            JSONObject result = new JSONObject();
            result.put("url", url);
            result.put("msg", suc);
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return R.error();
        }
    }

    public static void main(String[] args) {
        try{
            //String command="cmd /c npm -v";
            String command = "cmd /c obj2gltf -i D:/Cesium-ion-3D-Tiling-Pipeline-Windows-4.5.5/SampleData/AGI_HQ/AGI_HQ.obj -o D:/Cesium-ion-3D-Tiling-Pipeline-Windows-4.5.5/out/out1/model14.gltf";

            ByteArrayOutputStream susStream = new ByteArrayOutputStream();

            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            CommandLine commandLine = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
            exec.setStreamHandler(streamHandler);
            int code = exec.execute(commandLine);
            System.out.println("result code: " + code);

            String suc = susStream.toString("GBK");
            String err = errStream.toString("GBK");
            System.out.println(suc);
            System.out.println(err);
        }catch (Exception EX){
            EX.printStackTrace();
        }

    }


    //region  shp - convert epsg - geojson
    public R readSHPandConvertEPSG(MultipartFile file, String userAccount,String sourceEpsg,String targetEpsg) {
        try {

            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }

            File saveFile11 = new File(path);
            file.transferTo(saveFile11);

            String unPath = path.substring(0, path.length() - 3);
            FSZipUtils.unzip(saveFile11, unPath, false);  //unzip

            String shpName = this.findShpName(unPath);

            if (shpName.isEmpty()) {
                return R.error("SHP file not found");
            }

            JSONArray list = readShapeFile(unPath);

            for (int i = 0; i < list.size(); i++) {
                JSONObject item = list.getJSONObject(i);
                String newWkt = epsgConvert(sourceEpsg, targetEpsg, item.getString("the_geom"));
                item.put("the_geom", newWkt);
            }

            JSONObject geojson = JSON.parseObject("{\"type\":\"FeatureCollection\",\"features\":[]}");

            for (int i = 0; i < list.size(); i++) {
                JSONObject item = list.getJSONObject(i);
                JSONObject geojsonItem = new JSONObject();
                geojsonItem.put("type","Feature");
                geojsonItem.put("geometry", JSON.parseObject(WKTUtil.wktToJson(item.getString("the_geom"))));
                item.remove("the_geom");
                geojsonItem.put("properties",item);
                geojson.getJSONArray("features").add(geojsonItem);
            }
            return R.success(geojson);
        } catch (Exception ex) {
            log.error("error", ex);
            return R.error();
        }
    }

    private String epsgConvert(String sourceEpsg, String targetEpsg, String data) {
        String result = "";
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(data);
            CoordinateReferenceSystem crsresource = CRS.decode("EPSG:" + sourceEpsg,true);
            CoordinateReferenceSystem crsTarget = CRS.decode("EPSG:" + targetEpsg,true);
            MathTransform transform = CRS.findMathTransform(crsresource, crsTarget, true);
            Geometry transform1 = JTS.transform(geometry, transform);
            result = transform1.toString();
            return result;
        } catch (Exception ex) {
            return result;
        }
    }

    private JSONArray readShapeFile(String filePath) {
        JSONArray modelList =new JSONArray();
        File folder = new File(filePath);
        if (!folder.isDirectory()) {
            if (folder.toString().endsWith(".shp")) {
                try {
                    modelList = getShapeFile(folder);
                    return modelList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("The file suffix selected is not.shp");
            }
        }else{
            File[] files = folder.listFiles();
            if (!(files.length > 0)) {
                System.out.println("Directory file is empty");
                return modelList;
            }

            for (File file : files) {
                if (!file.toString().endsWith(".shp")) {
                    continue;
                }
                try {
                    JSONArray models = getShapeFile(file);
                    modelList.addAll(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelList;
    }

    private JSONArray getShapeFile(File file) throws Exception {
        JSONArray list = new JSONArray();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", file.toURI().toURL());
        DataStore dataStore = DataStoreFinder.getDataStore(map);
        ((ShapefileDataStore) dataStore).setCharset(Charset.forName("EUC_KR"));
        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            Iterator<? extends Property> iterator = feature.getValue().iterator();
            JSONObject object = new JSONObject();
            while (iterator.hasNext()) {
                Property property = iterator.next();
                Object key = property.getName();
                if (key != null) {
                    if(property.getName().toString().equals("the_geom")){
                        String temp = property.getValue().toString();
                        object.put("the_geom", temp);
                    }
                }
                if (key != null && !property.getName().toString().equals("the_geom")) {
                    object.put(key.toString(), property.getValue());
                }
            }
            list.add(object);
        }
        return list;
    }
    //endregion



    public R gridTool(JSONObject par){
        try{

            String administrative = "jeollabuk-do";//par.getString("administrative");
            String city = "jeonju";//par.getString("city");
            String cesiumPipelineBinPath = "D:/Cesium/3DTilingPipeline/bin";//par.getString("cesiumPipelineBinPath");
            String cityPath = "E:/data/grid_data/jeonju/jeollabuk-do";//par.getString("cityPath");

            String filePath1 = "E:/data/01.3ds/03.LOD2.5/dejing";//par.getString("filePath1");
            String filePath2 = "E:/data/01.3ds/03.LOD2.5/wansan";//par.getString("filePath2");

            List<LinkedHashMap<String,Object>> lod25GridList = koTableMapper.lod25GridList(administrative,city);

            for (int i = 0; i < lod25GridList.size(); i++) {
                LinkedHashMap<String,Object> item = lod25GridList.get(i);

                String f3dsPath = cityPath + "/" + item.get("gridid").toString() +"/3ds";
                File folder = new File(f3dsPath);
                if (!folder.exists() && !folder.isDirectory()) {
                    folder.mkdirs();
                }

                String f3dtilesPath = cityPath + "/" + item.get("gridid").toString() +"/3dtiles";
                File folder1 = new File(f3dtilesPath);
                if (!folder1.exists() && !folder1.isDirectory()) {
                    folder1.mkdirs();
                }


                String fcityGmlPath = cityPath + "/" + item.get("gridid").toString() +"/cityGml";
                File folder2 = new File(fcityGmlPath);
                if (!folder2.exists() && !folder2.isDirectory()) {
                    folder2.mkdirs();
                }

                List<LinkedHashMap<String,Object>> fileList = koTableMapper.lod25GridFileNameList(Integer.valueOf(item.get("gridid").toString()));

                File dir1 = new File(filePath1);
                File[] children1 = dir1.listFiles();
                for (File f : children1) {
                    boolean exists = fileList.stream()
                            .anyMatch(map -> map.containsKey("file_name") && map.get("file_name").toString().equals(f.getName().substring(0, f.getName().indexOf("."))));
                    if (exists == true) {
                        String oldPath = filePath1 + "/" +f.getName().substring(0, f.getName().indexOf(".")) +f.getName().substring(f.getName().lastIndexOf("."));
                        String newPath =  cityPath + "/" + item.get("gridid").toString() +"/3ds/" + f.getName().substring(0, f.getName().indexOf(".")) +f.getName().substring(f.getName().lastIndexOf("."));
                        copyFile(oldPath,newPath);
                    }
                }

                File dir2 = new File(filePath2);
                File[] children2 = dir2.listFiles();
                for (File f : children2) {
                    boolean exists = fileList.stream()
                            .anyMatch(map -> map.containsKey("file_name") && map.get("file_name").toString().equals(f.getName().substring(0, f.getName().indexOf("."))));
                    if(exists==true){
                        String oldPath = filePath2 + "/" +f.getName().substring(0, f.getName().indexOf(".")) +f.getName().substring(f.getName().lastIndexOf("."));
                        String newPath =  cityPath + "/" + item.get("gridid").toString() +"/3ds/" + f.getName().substring(0, f.getName().indexOf(".")) +f.getName().substring(f.getName().lastIndexOf("."));
                        copyFile(oldPath,newPath);
                    }
                }

            }

            return R.success();
        }catch (Exception ex){
            log.error("er:",ex);
            return R.error();
        }
    }

    private void copyFile(String oldPath, String newPath) throws Exception {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) {
            InputStream inStream = new FileInputStream(oldPath);
            File file = new File(newPath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
        }
    }


    public R getShpEPSG(MultipartFile file, String userAccount) {
        try {

            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }

            File saveFile11 = new File(path);
            file.transferTo(saveFile11);

            String unPath = path.substring(0, path.length() - 3);
            FSZipUtils.unzip(saveFile11, unPath, false);  //unzip

            String shpName = this.findShpName(unPath);

            if (shpName.isEmpty()) {
                return R.error("SHP file not found");
            }

            String shpPath = unPath + "/" + shpName;

            File shpFile = new File(shpPath);
            // Get data storage
            Map<String, Object> map = new HashMap<>();
            map.put("url", shpFile.toURI().toURL());
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            // Obtain feature sources
            String typeName = dataStore.getTypeNames()[0];
            FeatureSource<SimpleFeatureType, ?> featureSource = dataStore.getFeatureSource(typeName);
            // Obtain projection information
            CoordinateReferenceSystem crs = featureSource.getInfo().getCRS();
            String epsg = "";
            if (crs != null) {
                try {
                    epsg = CRS.lookupEpsgCode(crs, true).toString();
                } catch (Exception ex) {
                    epsg = "";
                }
            }

            JSONObject result = new JSONObject();
            result.put("epsg", epsg);
            return R.success(result);
        } catch (Exception ex) {
            log.error("error", ex);
            return R.error();
        }
    }


    public R hcmnReadCsv(MultipartFile file, String userAccount) {

        try {
            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }

            File saveFile11 = new File(path);
            file.transferTo(saveFile11);

            JSONArray result = new JSONArray();

            Reader reader = new FileReader(path);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord record : csvParser) {
                String column1 = record.get(0);
                String column2 = record.get(1);

                JSONObject item =new JSONObject();
                item.put("key",column1);
                item.put("value",column2);
                result.add(item);
            }

            csvParser.close();

            return R.success(result);
        } catch (Exception ex) {
            log.error("error:", ex);
            return R.error();
        }

    }



    public R readSHP1(MultipartFile file, String userAccount) {

        try {
            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }

            File saveFile11 = new File(path);
            file.transferTo(saveFile11);

            String unPath = path.substring(0, path.length() - 3);
            FSZipUtils.unzip(saveFile11, unPath, false);  //unzip

            String shpName = this.findShpName(unPath);

            if (shpName.isEmpty()) {
                return R.error("SHP file not found");
            }

            String shpPath = unPath + "/" + shpName;

            JSONObject data = JSON.parseObject(shapeToGeojson(shpPath));

            JSONObject result = new JSONObject();
            result.put("geojson",data);
            result.put("shpPath",path);

            return R.success(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }



}
