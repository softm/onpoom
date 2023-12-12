package com.quickex.service.mapfile.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.geo.Geometry;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.service.mapfile.IShpFileService;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.FSZipUtils;
import com.vividsolutions.jts.io.WKTReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.geotools.geojson.geom.GeometryJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class IShpFileServiceImpl implements IShpFileService {

    @Autowired
    private AppConfig appConfig;

    private String resultStr ="{\"type\":\"FeatureCollection\",\"features\":[]}";

    public R geojsonToShp(String body) {

        String getUrl = JSON.parseObject(body).getString("url");

        String xxxxx = getPost(getUrl);

        if (xxxxx.equals("")) {
            return R.error("data request error!");
        }

        List<String> geojsonFileList = new ArrayList<>();

        List<String> result = new ArrayList<>();
        JSONArray list = JSON.parseObject(xxxxx).getJSONArray("results");

        //
        String timeStamp = CommonUtils.currentDateToStr(6);

        List<String> tempPath = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

//            String fileName = "";   //"-1,20"

            for (Map.Entry<String, Object> entry : list.getJSONObject(i).entrySet()) {
                String fileName = entry.getKey();

                JSONObject tempJson = JSON.parseObject(resultStr);
                tempJson.put("features", getFeatures(list.getJSONObject(i).getJSONArray(fileName)));

                fileName = fileName.replace(",", "");


                // geojsonFileList.add(appConfig.getShpTempPath() + File.separator + timeStamp + File.separator +fileName);  //
                createFile(tempJson.toJSONString(), appConfig.getShpTempPath() + File.separator + timeStamp, fileName); //


                String inPath = appConfig.getShpTempPath() + File.separator + timeStamp + File.separator + fileName + ".geojson";
                String outPath = appConfig.getShpTempPath() + File.separator + timeStamp + File.separator + fileName;
                createSHP(inPath, outPath);  //

                tempPath.add(fileName);

//                try {
//
//                    //Thread.sleep(500);
//
//                    String zipPath =appConfig.getShpTempPath() + "/" + timeStamp + "/" + fileName;
//                    File zipFile = new File(zipPath + ".zip");
//                    FileOutputStream fos = new FileOutputStream(zipFile);
//                    //D:/navicat/tomcat/webapps/shpTemp/20220219171555406/2030
//                    //FSZipUtils.toZip("D:/navicat/tomcat/webapps/shpTemp/20220219171555406/2030" , fos, false);
//                    log.info("------>>");
//                    log.info("------>>"+zipPath);
//                    log.info("------>>");
//                    FSZipUtils.toZip(zipPath, fos, false);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//                result.add(timeStamp + File.separator + fileName + ".zip");
            }


        }

        try {

            Thread.sleep(5000);   //

            for (int i = 0; i < tempPath.size(); i++) {

                String zipPath =appConfig.getShpTempPath() + "/" + timeStamp + "/" + tempPath.get(i);
                File zipFile = new File(zipPath + ".zip");
                FileOutputStream fos = new FileOutputStream(zipFile);
                //D:/navicat/tomcat/webapps/shpTemp/20220219171555406/2030
                //FSZipUtils.toZip("D:/navicat/tomcat/webapps/shpTemp/20220219171555406/2030" , fos, false);
                log.info("------>>");
                log.info("------>>"+zipPath);
                log.info("------>>");
                FSZipUtils.toZip(zipPath, fos, false);
                result.add(timeStamp + "/" + tempPath.get(i) + ".zip");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return R.success(result);
    }



    private  JSONObject wktToJson(String wkt) {
        String json = null;
        try {
            WKTReader reader = new WKTReader();
            com.vividsolutions.jts.geom.Geometry geometry = reader.read(wkt);
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON(20);
            g.write(geometry, writer);
            json = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.parseObject(json);
    }

    private JSONArray getFeatures(JSONArray dataList){
        JSONArray list =new JSONArray();

        for (int i = 0; i < dataList.size(); i++) {
            JSONObject item = new JSONObject();
            item.put("type", "Feature");
            String wktStr = dataList.getJSONObject(i).getString("geom");
            item.put("geometry", wktToJson(wktStr));
            dataList.getJSONObject(i).remove("geom");
            item.put("properties", dataList.getJSONObject(i));  //
            list.add(item);
        }


//        for (int i = 0; i < dataList.size(); i++) {
//
//            String zbStr = dataList.getJSONObject(i).getString("geom");
//            zbStr = zbStr.replace("MULTIPOLYGON(((", "");
//            zbStr = zbStr.replace(")))", "");
//
//            JSONObject item = new JSONObject();
//            item.put("type", "Feature");
//            dataList.getJSONObject(i).remove("geom");
//            item.put("properties", dataList.getJSONObject(i));  //
//            item.put("geometry", new JSONObject());
//
//            item.getJSONObject("geometry").put("type","Polygon");
//
//            String [] arry = zbStr.split(",");
//
//            String splicing ="[[";
//
//            for (int j = 0; j < arry.length; j++) {
//                splicing+="[";
//                splicing+=arry[j].replace(" ",",");
//                splicing+="],";
//            }
//            splicing = splicing.substring(0,splicing.length()-1);
//            splicing+="]]";
//            item.getJSONObject("geometry").put("coordinates",JSON.parseArray(splicing));
//            list.add(item);
//
//
//        }
        return list;
    }

    public void createFile(String data, String basedir, String name) {
        try {
            File file = new File(basedir + File.separator + name+ ".geojson");
            /*  */
            File fileDir =new File(basedir);
            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                fileDir .mkdir();
            }

            //
//            File fileTest =new File(basedir + File.separator + name);
//            fileTest.mkdirs();

            boolean b = file.createNewFile();
            if(b) {
                Writer out = new FileWriter(file);
                out.write(data);
                out.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSHP(String geojsonDir,String outpuDir){

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://220.120.28.20:8050/fmerest/v3/automations/workflows/9cf66233-79a0-4a4a-abb7-eb63a005d77a/f5106c12-e194-fc4b-a755-70baec14208d/message");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        JSONObject object =new JSONObject();
        object.put("geojsonDir",geojsonDir);
        object.put("outpuDir",outpuDir);
        //body
        String jsonString =object.toJSONString();
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

//            if (responseEntity != null) {
//                return R.success(JSON.parse(EntityUtils.toString(responseEntity)));
//            }else{
//                return R.error();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String getPost(String url){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //HttpPost httpPost = new HttpPost("http://220.120.28.20:8686/korea-admin-1.0.0/api/httpForward/lx_uav_api");  //
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8686/korea-admin-1.0.0/api/httpForward/lx_uav_api");  //
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
        JSONObject object =new JSONObject();
        object.put("url",url);

        String jsonString =object.toJSONString();
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {

                JSONObject result =JSON.parseObject(EntityUtils.toString(responseEntity));
                if (result.getInteger("code") != 200) {
                    return "";
                }

                return result.getJSONObject("data").toJSONString();


              //  return EntityUtils.toString(responseEntity);
            }else{
                return "";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
