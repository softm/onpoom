package com.quickex.service.doc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.service.doc.ICitySpaceAnalysiDoc;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.DownloadImageUtils;
import com.quickex.utils.WordTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CitySpaceAnalysiDocImpl implements ICitySpaceAnalysiDoc {

    @Autowired
    private AppConfig appConfig;

    public R doc1(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word1.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images = new ArrayList<>();
            List<String> paths = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths) {
                images.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc2(JSONObject data) {
        try {
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word2.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images1").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1", images1, 310, 180);

            List<FileInputStream> images2 = new ArrayList<>();
            List<String> paths2 = data.getJSONArray("images2").toJavaList(String.class);
            for (String item : paths2) {
                images2.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace2", images2, 310, 180);

            //generate file
            String newFileName = CommonUtils.getUUID();
            String newfoldeName = CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url", docSavePath);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc2_1(JSONObject data) {
        try {
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word2.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images1").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(item));
            }
            template.replaceDocumentImage("ImagesReplace1", images1, 310, 180);

            List<FileInputStream> images2 = new ArrayList<>();
            List<String> paths2 = data.getJSONArray("images2").toJavaList(String.class);
            for (String item : paths2) {
                images2.add(new FileInputStream(item));
            }
            template.replaceDocumentImage("ImagesReplace2", images2, 310, 180);

            //generate file
            String newFileName = CommonUtils.getUUID();
            String newfoldeName = CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url", docSavePath);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc3(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            parametersMap.put("totalArea", data.getString("totalArea"));
            parametersMap.put("averageSlope", data.getString("averageSlope"));

            parametersMap.put("area1", data.getString("area1"));
            parametersMap.put("area2", data.getString("area2"));
            parametersMap.put("area3", data.getString("area3"));
            parametersMap.put("area4", data.getString("area4"));
            parametersMap.put("area5", data.getString("area5"));
            parametersMap.put("area6", data.getString("area6"));
            parametersMap.put("area7", data.getString("area7"));
            parametersMap.put("area8", data.getString("area8"));
            parametersMap.put("area9", data.getString("area9"));

            parametersMap.put("scale1", data.getString("scale1"));
            parametersMap.put("scale2", data.getString("scale2"));
            parametersMap.put("scale3", data.getString("scale3"));
            parametersMap.put("scale4", data.getString("scale4"));
            parametersMap.put("scale5", data.getString("scale5"));
            parametersMap.put("scale6", data.getString("scale6"));
            parametersMap.put("scale7", data.getString("scale7"));
            parametersMap.put("scale8", data.getString("scale8"));
            parametersMap.put("scale9", data.getString("scale9"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word3.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);


            //replace legend img

            //1
            List<FileInputStream> legend1 = new ArrayList<>();
            legend1.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_1.png"));
            template.replaceDocumentImage("legend1",legend1,91,14);

            //2
            List<FileInputStream> legend2 = new ArrayList<>();
            legend2.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_2.png"));
            template.replaceDocumentImage("legend2",legend2,91,14);

            //3
            List<FileInputStream> legend3 = new ArrayList<>();
            legend3.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_3.png"));
            template.replaceDocumentImage("legend3",legend3,91,14);

            //4
            List<FileInputStream> legend4 = new ArrayList<>();
            legend4.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_4.png"));
            template.replaceDocumentImage("legend4",legend4,91,14);

            //5
            List<FileInputStream> legend5 = new ArrayList<>();
            legend5.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_5.png"));
            template.replaceDocumentImage("legend5",legend5,91,14);

            //6
            List<FileInputStream> legend6 = new ArrayList<>();
            legend6.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_6.png"));
            template.replaceDocumentImage("legend6",legend6,91,14);

            //7
            List<FileInputStream> legend7 = new ArrayList<>();
            legend7.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_7.png"));
            template.replaceDocumentImage("legend7",legend7,91,14);

            //8
            List<FileInputStream> legend8 = new ArrayList<>();
            legend8.add(new FileInputStream(appConfig.getWordTemplate() + "/img/doc3_8.png"));
            template.replaceDocumentImage("legend8",legend8,91,14);


            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc4(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            parametersMap.put("buildingCount", data.getString("buildingCount"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");
            for (int i = 0; i < dataLList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("xh", dataLList.getJSONObject(i).getString("xh"));
                map.put("buildingName", dataLList.getJSONObject(i).getString("buildingName"));
                map.put("buildingAddress", dataLList.getJSONObject(i).getString("buildingAddress"));
                map.put("buildingHeight", dataLList.getJSONObject(i).getString("buildingHeight"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word4.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc5(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("buildingCount", data.getString("buildingCount"));
            parametersMap.put("floorAreaSum", data.getString("floorAreaSum"));
            parametersMap.put("allAreaSum", data.getString("allAreaSum"));
            parametersMap.put("layerAvg", data.getString("layerAvg"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word5.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc6(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("totalLength", data.getString("totalLength"));
            parametersMap.put("sumLength", data.getString("sumLength"));
            parametersMap.put("coverage", data.getString("coverage"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word6.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc7(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("totalLength", data.getString("totalLength"));
            parametersMap.put("sumLength", data.getString("sumLength"));
            parametersMap.put("coverage", data.getString("coverage"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word7.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc8(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));


            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word8.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc9(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("date", data.getString("date"));
            parametersMap.put("time", data.getString("time"));
            parametersMap.put("sunshineTime", data.getString("sunshineTime"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word9.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images1").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            List<FileInputStream> images2 = new ArrayList<>();
            List<String> paths2 = data.getJSONArray("images2").toJavaList(String.class);
            for (String item : paths2) {
                images2.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace2",images2,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc10(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("date", data.getString("date"));
            parametersMap.put("time", data.getString("time"));
            parametersMap.put("sunshineTime", data.getString("sunshineTime"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word10.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc11(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");
            for (int i = 0; i < dataLList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("xh", i+1);
                map.put("address", dataLList.getJSONObject(i).getString("address"));
                map.put("images", "images" + (i+1));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word11.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);


            //replace tabl1 images + xh
            for (int i = 0; i < dataLList.size(); i++) {
                List<FileInputStream> images = new ArrayList<>();
                List<String> paths = dataLList.getJSONObject(i).getJSONArray("images").toJavaList(String.class);
                for (String item : paths) {
                    images.add(new FileInputStream(appConfig.getUavModelPath() + item));
                }
                template.replaceDocumentImage("images" + (i + 1), images, 310, 180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc12(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));
            parametersMap.put("address", data.getString("address"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");
            for (int i = 0; i < dataLList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("xh", i+1);
                map.put("startAddress", dataLList.getJSONObject(i).getString("startAddress"));
                map.put("endAddress", dataLList.getJSONObject(i).getString("endAddress"));
                map.put("images", "images" + (i+1));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/city-space-word12.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);


            //replace tabl1 images + xh
            for (int i = 0; i < dataLList.size(); i++) {
                List<FileInputStream> images = new ArrayList<>();
                List<String> paths = dataLList.getJSONObject(i).getJSONArray("images").toJavaList(String.class);
                for (String item : paths) {
                    images.add(new FileInputStream(appConfig.getUavModelPath() + item));
                }
                template.replaceDocumentImage("images" + (i + 1), images, 310, 180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc13(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("cctvName", data.getString("cctvName"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("value1", data.getString("value1"));
            parametersMap.put("value2", data.getString("value2"));
            parametersMap.put("value3", data.getString("value3"));
            parametersMap.put("value4", data.getString("value4"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/cctv-word1.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            //1
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images1").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,160,120);

            //2
            List<FileInputStream> images2 = new ArrayList<>();
            List<String> paths2 = data.getJSONArray("images2").toJavaList(String.class);
            for (String item : paths2) {
                images2.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace2",images2,160,120);

            //3
            List<FileInputStream> images3 = new ArrayList<>();
            List<String> paths3 = data.getJSONArray("images3").toJavaList(String.class);
            for (String item : paths3) {
                images3.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace3",images3,160,120);

            //4
            List<FileInputStream> images4 = new ArrayList<>();
            List<String> paths4 = data.getJSONArray("images4").toJavaList(String.class);
            for (String item : paths4) {
                images4.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace4",images4,160,120);

            //5
            List<FileInputStream> images5 = new ArrayList<>();
            List<String> paths5 = data.getJSONArray("images5").toJavaList(String.class);
            for (String item : paths5) {
                images5.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace5",images5,160,120);


            //6
            List<FileInputStream> images6 = new ArrayList<>();
            List<String> paths6 = data.getJSONArray("images6").toJavaList(String.class);
            for (String item : paths6) {
                images6.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace6",images6,160,120);


            //7
            List<FileInputStream> images7 = new ArrayList<>();
            List<String> paths7 = data.getJSONArray("images7").toJavaList(String.class);
            for (String item : paths7) {
                images7.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace7",images7,160,120);


            //8
            List<FileInputStream> images8 = new ArrayList<>();
            List<String> paths8 = data.getJSONArray("images8").toJavaList(String.class);
            for (String item : paths8) {
                images8.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace8",images8,160,120);



            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }


    public R doc14(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("position", data.getString("position"));
            parametersMap.put("height", data.getString("height"));
            parametersMap.put("distance", data.getString("distance"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template1.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }


    public R doc15(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("amount1", data.getString("amount1"));
            parametersMap.put("amount2", data.getString("amount2"));
            parametersMap.put("region1", data.getString("region1"));
            parametersMap.put("region2", data.getString("region2"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");
            for (int i = 0; i < dataLList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList.getJSONObject(i).getString("c4"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template2.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc16(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template3.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }



    public R doc17(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template4.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc18(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template5.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc19(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template6.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc20(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template7.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc21(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("time", data.getString("time"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template8.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images  --Analysis results
            List<FileInputStream> images1 = new ArrayList<>();
            images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image1")));
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //replace images --Screenshot
            List<FileInputStream> images2 = new ArrayList<>();
            images2.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image2")));
            template.replaceDocumentImage("ImagesReplace2",images2,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc22(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("name", data.getString("name"));
            parametersMap.put("address", data.getString("address"));
            parametersMap.put("time", data.getString("time"));
            parametersMap.put("speed", data.getString("speed"));
            parametersMap.put("direction", data.getString("direction"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template9.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images 1
            if(data.getString("image1")!=null && !data.getString("image1").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image1")));
                template.replaceDocumentImage("ImagesReplace1",images1,360,180);
            }

            //replace images 2
            if (data.getString("image2") != null && !data.getString("image2").equals("")) {
                List<FileInputStream> images2 = new ArrayList<>();
                images2.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image2")));
                template.replaceDocumentImage("ImagesReplace2", images2, 200, 180);
            }

            //replace images 3
            if (data.getString("image3") != null && !data.getString("image3").equals("")) {
                List<FileInputStream> images3 = new ArrayList<>();
                images3.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image3")));
                template.replaceDocumentImage("ImagesReplace3", images3, 200, 180);
            }

            //replace images 4
            if (data.getString("image4") != null && !data.getString("image4").equals("")) {
                List<FileInputStream> images4 = new ArrayList<>();
                images4.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image4")));
                template.replaceDocumentImage("ImagesReplace4", images4, 200, 180);
            }

            //replace images 5
            if (data.getString("image5") != null && !data.getString("image5").equals("")) {
                List<FileInputStream> images5 = new ArrayList<>();
                images5.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image5")));
                template.replaceDocumentImage("ImagesReplace5", images5, 200, 180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc23(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("creatDe", data.getString("creatDe"));
            parametersMap.put("creatTime", data.getString("creatTime"));
            parametersMap.put("bsisMxmmParkngCnt", data.getString("bsisMxmmParkngCnt"));
            parametersMap.put("bsisSiar", data.getString("bsisSiar"));
            parametersMap.put("bsisEntrcCnt", data.getString("bsisEntrcCnt"));
            parametersMap.put("bsisEntrcBt", data.getString("bsisEntrcBt"));
            parametersMap.put("bsisEntrcLt", data.getString("bsisEntrcLt"));
            parametersMap.put("bsisParkngLnBt", data.getString("bsisParkngLnBt"));
            parametersMap.put("bsisParkngLnLt", data.getString("bsisParkngLnLt"));
            parametersMap.put("bsisCartrk_bt", data.getString("bsisCartrk_bt"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template10.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images 1
            List<FileInputStream> images1 = new ArrayList<>();
            images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
            template.replaceDocumentImage("ImagesReplace",images1,310,180);


            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc24(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("time", data.getString("time"));
            parametersMap.put("height", data.getString("height"));
            parametersMap.put("diagonal", data.getString("diagonal"));
            parametersMap.put("area", data.getString("area"));


            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template11.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images 1
            List<FileInputStream> images1 = new ArrayList<>();
            images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
            template.replaceDocumentImage("ImagesReplace",images1,310,180);


            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc25(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("area", data.getString("area"));
            parametersMap.put("time", data.getString("time"));


            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");
            for (int i = 0; i < dataLList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList.getJSONObject(i).getString("c4"));
                map.put("c5", dataLList.getJSONObject(i).getString("c5"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template12.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> image = new ArrayList<>();
            image.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));


            List<FileInputStream> images1 = new ArrayList<>();
            images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc26(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("time", data.getString("time"));
            parametersMap.put("ratio", data.getString("ratio"));

            parametersMap.put("ratio1", data.getString("ratio1"));
            parametersMap.put("ratio2", data.getString("ratio2"));
            parametersMap.put("ratio3", data.getString("ratio3"));
            parametersMap.put("ratio4", data.getString("ratio4"));
            parametersMap.put("ratio5", data.getString("ratio5"));
            parametersMap.put("ratio6", data.getString("ratio6"));
            parametersMap.put("ratio7", data.getString("ratio7"));
            parametersMap.put("ratio8", data.getString("ratio8"));
            parametersMap.put("ratio9", data.getString("ratio9"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template13.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images

//            String downloadPath = appConfig.getWordTemplate() +"/downloadTemp/" + System.currentTimeMillis()+".png";
//            DownloadImageUtils.writeImageToDisk(data.getString("image"),downloadPath);
//
//            List<FileInputStream> images1 = new ArrayList<>();
//            images1.add(new FileInputStream(downloadPath));
//            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //replace legend img

            //1
            List<FileInputStream> legend1 = new ArrayList<>();
            legend1.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/1.png"));
            template.replaceDocumentImage("legend1",legend1,91,14);

            //2
            List<FileInputStream> legend2 = new ArrayList<>();
            legend2.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/2.png"));
            template.replaceDocumentImage("legend2",legend2,91,14);

            //3
            List<FileInputStream> legend3 = new ArrayList<>();
            legend3.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/3.png"));
            template.replaceDocumentImage("legend3",legend3,91,14);

            //4
            List<FileInputStream> legend4 = new ArrayList<>();
            legend4.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/4.png"));
            template.replaceDocumentImage("legend4",legend4,91,14);

            //5
            List<FileInputStream> legend5 = new ArrayList<>();
            legend5.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/5.png"));
            template.replaceDocumentImage("legend5",legend5,91,14);

            //6
            List<FileInputStream> legend6 = new ArrayList<>();
            legend6.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/6.png"));
            template.replaceDocumentImage("legend6",legend6,91,14);

            //7
            List<FileInputStream> legend7 = new ArrayList<>();
            legend7.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/7.png"));
            template.replaceDocumentImage("legend7",legend7,91,14);

            //8
            List<FileInputStream> legend8 = new ArrayList<>();
            legend8.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/8.png"));
            template.replaceDocumentImage("legend8",legend8,91,14);

            //9
            List<FileInputStream> legend9 = new ArrayList<>();
            legend9.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/9.png"));
            template.replaceDocumentImage("legend9",legend9,91,14);


            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }
    public R doc27(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("time", data.getString("time"));
            parametersMap.put("ratio", data.getString("ratio"));

            parametersMap.put("ratio1", data.getString("ratio1"));
            parametersMap.put("ratio2", data.getString("ratio2"));
            parametersMap.put("ratio3", data.getString("ratio3"));
            parametersMap.put("ratio4", data.getString("ratio4"));
            parametersMap.put("ratio5", data.getString("ratio5"));
            parametersMap.put("ratio6", data.getString("ratio6"));
            parametersMap.put("ratio7", data.getString("ratio7"));
            parametersMap.put("ratio8", data.getString("ratio8"));
            parametersMap.put("ratio9", data.getString("ratio9"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template14.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images

            String downloadPath = appConfig.getWordTemplate() +"/downloadTemp/" + System.currentTimeMillis()+".png";
            DownloadImageUtils.writeImageToDisk(data.getString("image"),downloadPath);

            List<FileInputStream> images1 = new ArrayList<>();
            images1.add(new FileInputStream(downloadPath));
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            //replace legend img

            //1
            List<FileInputStream> legend1 = new ArrayList<>();
            legend1.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/1.png"));
            template.replaceDocumentImage("legend1",legend1,91,14);

            //2
            List<FileInputStream> legend2 = new ArrayList<>();
            legend2.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/2.png"));
            template.replaceDocumentImage("legend2",legend2,91,14);

            //3
            List<FileInputStream> legend3 = new ArrayList<>();
            legend3.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/3.png"));
            template.replaceDocumentImage("legend3",legend3,91,14);

            //4
            List<FileInputStream> legend4 = new ArrayList<>();
            legend4.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/4.png"));
            template.replaceDocumentImage("legend4",legend4,91,14);

            //5
            List<FileInputStream> legend5 = new ArrayList<>();
            legend5.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/5.png"));
            template.replaceDocumentImage("legend5",legend5,91,14);

            //6
            List<FileInputStream> legend6 = new ArrayList<>();
            legend6.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/6.png"));
            template.replaceDocumentImage("legend6",legend6,91,14);

            //7
            List<FileInputStream> legend7 = new ArrayList<>();
            legend7.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/7.png"));
            template.replaceDocumentImage("legend7",legend7,91,14);

            //8
            List<FileInputStream> legend8 = new ArrayList<>();
            legend8.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/8.png"));
            template.replaceDocumentImage("legend8",legend8,91,14);

            //9
            List<FileInputStream> legend9 = new ArrayList<>();
            legend9.add(new FileInputStream(appConfig.getWordTemplate() + "/sunshine-analysis-20221025-img/9.png"));
            template.replaceDocumentImage("legend9",legend9,91,14);


            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }


    public R doc28(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));
            parametersMap.put("data8", data.getString("data8"));
            parametersMap.put("data9", data.getString("data9"));
            parametersMap.put("data10", data.getString("data10"));
            parametersMap.put("data11", data.getString("data11"));
            parametersMap.put("data12", data.getString("data12"));
            parametersMap.put("data13", data.getString("data13"));
            parametersMap.put("data14", data.getString("data14"));
            parametersMap.put("data15", data.getString("data15"));
            parametersMap.put("data16", data.getString("data16"));
            parametersMap.put("data17", data.getString("data17"));
            parametersMap.put("data18", data.getString("data18"));
            parametersMap.put("data19", data.getString("data19"));
            parametersMap.put("data20", data.getString("data20"));
            parametersMap.put("data21", data.getString("data21"));
            parametersMap.put("data22", data.getString("data22"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template15.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc29(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template16.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }
    public R doc30(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));
            parametersMap.put("data8", data.getString("data8"));
            parametersMap.put("data9", data.getString("data9"));
            parametersMap.put("data10", data.getString("data10"));
            parametersMap.put("data11", data.getString("data11"));
            parametersMap.put("data12", data.getString("data12"));
            parametersMap.put("data13", data.getString("data13"));
            parametersMap.put("data14", data.getString("data14"));
            parametersMap.put("data15", data.getString("data15"));
            parametersMap.put("data16", data.getString("data16"));
            parametersMap.put("data17", data.getString("data17"));
            parametersMap.put("data18", data.getString("data18"));
            parametersMap.put("data19", data.getString("data19"));
            parametersMap.put("data20", data.getString("data20"));
            parametersMap.put("data21", data.getString("data21"));
            parametersMap.put("data22", data.getString("data22"));
            parametersMap.put("data23", data.getString("data23"));
            parametersMap.put("title", data.getString("title"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");
            for (int i = 0; i < dataLList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("xh", i+1);
                map.put("data1", dataLList.getJSONObject(i).getString("data1"));
                map.put("data2", dataLList.getJSONObject(i).getString("data2"));
                map.put("data3", dataLList.getJSONObject(i).getString("data3"));
                map.put("data4", dataLList.getJSONObject(i).getString("data4"));
                map.put("data5", dataLList.getJSONObject(i).getString("data5"));
                map.put("data6", dataLList.getJSONObject(i).getString("data6"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template17.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }


    public R doc31(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));
            parametersMap.put("data8", data.getString("data8"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template18.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc32(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));
            parametersMap.put("data8", data.getString("data8"));
            parametersMap.put("data9", data.getString("data9"));
            parametersMap.put("data10", data.getString("data10"));
            parametersMap.put("data11", data.getString("data11"));
            parametersMap.put("data12", data.getString("data12"));
            parametersMap.put("data13", data.getString("data13"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template19.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
//            if(data.getString("image")!=null && !data.getString("image").equals("")){
//                List<FileInputStream> images1 = new ArrayList<>();
//                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
//                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
//            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc33(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList1 = data.getJSONArray("list1");
            for (int i = 0; i < dataLList1.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList1.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList1.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList1.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList1.getJSONObject(i).getString("c4"));
                map.put("c5", dataLList1.getJSONObject(i).getString("c5"));
                table1.add(map);
            }

            List<Map<String, Object>> table2 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList2 = data.getJSONArray("list2");
            for (int i = 0; i < dataLList2.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList2.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList2.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList2.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList2.getJSONObject(i).getString("c4"));
                map.put("c5", dataLList2.getJSONObject(i).getString("c5"));
                map.put("c6", dataLList2.getJSONObject(i).getString("c6"));
                map.put("c7", dataLList2.getJSONObject(i).getString("c7"));
                map.put("c8", dataLList2.getJSONObject(i).getString("c8"));
                map.put("c9", dataLList2.getJSONObject(i).getString("c9"));
                table2.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("table2", table2);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template20.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc34(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template21.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc35(JSONObject data){
        try {
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("docName", data.getString("docName"));
            parametersMap.put("analysisDate", data.getString("analysisDate"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template22.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            String downloadPath = appConfig.getWordTemplate() +"/downloadTemp/" + System.currentTimeMillis()+".png";
            DownloadImageUtils.writeImageToDisk(data.getString("image1"),downloadPath);

            List<FileInputStream> images1 = new ArrayList<>();
            images1.add(new FileInputStream(downloadPath));
            template.replaceDocumentImage("ImagesReplace1",images1,310,180);

            List<FileInputStream> images2 = new ArrayList<>();
            images2.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image2")));
            template.replaceDocumentImage("ImagesReplace2",images2,310,180);

            //generate file
            String newFileName = CommonUtils.getUUID();
            String newfoldeName = CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url", docSavePath);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }
    public R doc36(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template23.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc37(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("data7", data.getString("data7"));
            parametersMap.put("data8", data.getString("data8"));
            parametersMap.put("data9", data.getString("data9"));
            parametersMap.put("data10", data.getString("data10"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template24.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc38(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));

            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template25.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

    public R doc39(JSONObject data) {
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("area", data.getString("area"));
            parametersMap.put("waterLevel", data.getString("waterLevel"));
            parametersMap.put("time", data.getString("time"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList1 = data.getJSONArray("list1");
            for (int i = 0; i < dataLList1.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList1.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList1.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList1.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList1.getJSONObject(i).getString("c4"));
                table1.add(map);
            }


            List<Map<String, Object>> table2 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList2 = data.getJSONArray("list2");
            for (int i = 0; i < dataLList2.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("addr", dataLList2.getJSONObject(i).getString("addr"));
                map.put("bldNm", dataLList2.getJSONObject(i).getString("bldNm"));
                map.put("spanuatYycnt", dataLList2.getJSONObject(i).getString("spanuatYycnt"));
                map.put("grndFlr", dataLList2.getJSONObject(i).getString("grndFlr"));
                map.put("ugrndFlr", dataLList2.getJSONObject(i).getString("ugrndFlr"));
                table2.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("table2", table2);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template26.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
//            if(data.getString("image")!=null && !data.getString("image").equals("")){
//                List<FileInputStream> images1 = new ArrayList<>();
//                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
//                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
//            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
            return R.error();
        }
    }




    public R doc40(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("title1", data.getString("title1"));
            parametersMap.put("title2", data.getString("title2"));
            parametersMap.put("title3", data.getString("title3"));
            parametersMap.put("title4", data.getString("title4"));
            parametersMap.put("title5", data.getString("title5"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList1 = data.getJSONArray("tableData1");
            for (int i = 0; i < dataLList1.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name1", dataLList1.getJSONObject(i).getString("name1"));
                map.put("name2", dataLList1.getJSONObject(i).getString("name2"));
                map.put("name3", dataLList1.getJSONObject(i).getString("name3"));
                table1.add(map);
            }

            List<Map<String, Object>> table2 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList2 = data.getJSONArray("tableData2");
            for (int i = 0; i < dataLList2.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name1", dataLList2.getJSONObject(i).getString("name1"));
                map.put("name2", dataLList2.getJSONObject(i).getString("name2"));
                map.put("name3", dataLList2.getJSONObject(i).getString("name3"));
                map.put("name4", dataLList2.getJSONObject(i).getString("name4"));
                map.put("name5", dataLList2.getJSONObject(i).getString("name5"));
                map.put("name6", dataLList2.getJSONObject(i).getString("name6"));
                map.put("name7", dataLList2.getJSONObject(i).getString("name7"));
                map.put("name8", dataLList2.getJSONObject(i).getString("name8"));
                map.put("name9", dataLList2.getJSONObject(i).getString("name9"));
                table2.add(map);
            }

            List<Map<String, Object>> table3 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList3 = data.getJSONArray("tableData3");
            for (int i = 0; i < dataLList3.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name1", dataLList3.getJSONObject(i).getString("name1"));
                map.put("name2", dataLList3.getJSONObject(i).getString("name2"));
                map.put("name3", dataLList3.getJSONObject(i).getString("name3"));
                map.put("name4", dataLList3.getJSONObject(i).getString("name4"));
                map.put("name5", dataLList3.getJSONObject(i).getString("name5"));
                table3.add(map);
            }

            List<Map<String, Object>> table4 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList4 = data.getJSONArray("tableData4");
            for (int i = 0; i < dataLList4.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name1", dataLList4.getJSONObject(i).getString("name1"));
                map.put("name2", dataLList4.getJSONObject(i).getString("name2"));
                map.put("name3", dataLList4.getJSONObject(i).getString("name3"));
                map.put("name4", dataLList4.getJSONObject(i).getString("name4"));
                map.put("name5", dataLList4.getJSONObject(i).getString("name5"));
                table4.add(map);
            }

            List<Map<String, Object>> table5 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList5 = data.getJSONArray("tableData5");
            for (int i = 0; i < dataLList5.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name1", dataLList5.getJSONObject(i).getString("name1"));
                map.put("name2", dataLList5.getJSONObject(i).getString("name2"));
                map.put("name3", dataLList5.getJSONObject(i).getString("name3"));
                map.put("name4", dataLList5.getJSONObject(i).getString("name4"));
                map.put("name5", dataLList5.getJSONObject(i).getString("name5"));
                map.put("name6", dataLList5.getJSONObject(i).getString("name6"));
                map.put("name7", dataLList5.getJSONObject(i).getString("name7"));
                map.put("name8", dataLList5.getJSONObject(i).getString("name8"));
                map.put("name9", dataLList5.getJSONObject(i).getString("name9"));
                table5.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("table2", table2);
            wordDataMap.put("table3", table3);
            wordDataMap.put("table4", table4);
            wordDataMap.put("table5", table5);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template27.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }



    public R doc41(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));
            parametersMap.put("sum1", data.getString("sum1"));
            parametersMap.put("sum2", data.getString("sum2"));
            parametersMap.put("sum3", data.getString("sum3"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList1 = data.getJSONArray("table1");
            for (int i = 0; i < dataLList1.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList1.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList1.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList1.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList1.getJSONObject(i).getString("c4"));
                map.put("c5", dataLList1.getJSONObject(i).getString("c5"));
                map.put("c6", dataLList1.getJSONObject(i).getString("c6"));
                map.put("c7", dataLList1.getJSONObject(i).getString("c7"));
                map.put("c8", dataLList1.getJSONObject(i).getString("c8"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template28.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }
    public R doc42(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList1 = data.getJSONArray("table1");
            for (int i = 0; i < dataLList1.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList1.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList1.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList1.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList1.getJSONObject(i).getString("c4"));
                map.put("c5", dataLList1.getJSONObject(i).getString("c5"));
                map.put("c6", dataLList1.getJSONObject(i).getString("c6"));
                map.put("c7", dataLList1.getJSONObject(i).getString("c7"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template29.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }
    public R doc43(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("data1", data.getString("data1"));
            parametersMap.put("data2", data.getString("data2"));
            parametersMap.put("data3", data.getString("data3"));
            parametersMap.put("data4", data.getString("data4"));
            parametersMap.put("data5", data.getString("data5"));
            parametersMap.put("data6", data.getString("data6"));

            parametersMap.put("sum1", data.getString("sum1"));
            parametersMap.put("sum2", data.getString("sum2"));
            parametersMap.put("sum3", data.getString("sum3"));
            parametersMap.put("sum4", data.getString("sum4"));
            parametersMap.put("sum5", data.getString("sum5"));
            parametersMap.put("sum6", data.getString("sum6"));


            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
            JSONArray dataLList1 = data.getJSONArray("table1");
            for (int i = 0; i < dataLList1.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", dataLList1.getJSONObject(i).getString("c1"));
                map.put("c2", dataLList1.getJSONObject(i).getString("c2"));
                map.put("c3", dataLList1.getJSONObject(i).getString("c3"));
                map.put("c4", dataLList1.getJSONObject(i).getString("c4"));
                map.put("c5", dataLList1.getJSONObject(i).getString("c5"));
                map.put("c6", dataLList1.getJSONObject(i).getString("c6"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template30.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace1",images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }


    public R doc44(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("amount1", data.getString("amount1"));
            parametersMap.put("amount2", data.getString("amount2"));
            parametersMap.put("region1", data.getString("region1"));
            parametersMap.put("region2", data.getString("region2"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            JSONArray dataLList = data.getJSONArray("list");

            List<JSONObject> list = new ArrayList<>();

            for (int i = 0; i < dataLList.size(); i++) {
                list.add(dataLList.getJSONObject(i));
                JSONArray cList = dataLList.getJSONObject(i).getJSONArray("list");
                for (int j = 0; j < cList.size(); j++) {
                    list.add(cList.getJSONObject(j));
                }
            }

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("c1", list.get(i).getString("c1"));
                map.put("c2", list.get(i).getString("c2"));
                map.put("c3", list.get(i).getString("c3"));
                map.put("c4", list.get(i).getString("c4"));
                map.put("c5", list.get(i).getString("c5"));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template31.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace images
            List<FileInputStream> images1 = new ArrayList<>();
            List<String> paths1 = data.getJSONArray("images").toJavaList(String.class);
            for (String item : paths1) {
                images1.add(new FileInputStream(appConfig.getUavModelPath() + item));
            }
            template.replaceDocumentImage("ImagesReplace",images1,310,180);

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }


    public R doc45(JSONObject data){
        try{
            Map<String, Object> wordDataMap = new HashMap<String, Object>();
            Map<String, Object> parametersMap = new HashMap<String, Object>();

            parametersMap.put("address", data.getString("address"));
            parametersMap.put("docName", data.getString("docName"));

            List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();

            List<String> images = data.getJSONArray("images").toJavaList(String.class);

            for (int i = 0; i < images.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("lineName", "line"+(i+1));
                map.put("lineImage", "lineImage"+(i+1));
                table1.add(map);
            }

            wordDataMap.put("table1", table1);
            wordDataMap.put("parametersMap", parametersMap);

            //WordTemplate file
            File templateFile = new File(appConfig.getWordTemplate() + "/word-template32.docx");

            //read WordTemplate
            FileInputStream fileInputStream = new FileInputStream(templateFile);
            WordTemplate template = new WordTemplate(fileInputStream);

            //replace text
            template.replaceDocument(wordDataMap);

            //replace image
            if(data.getString("image")!=null && !data.getString("image").equals("")){
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + data.getString("image")));
                template.replaceDocumentImage("ImagesReplace",images1,310,180);
            }

            //replace images
            for (int i = 0; i < images.size(); i++) {
                String placeholder = "lineImage" + (i + 1);
                List<FileInputStream> images1 = new ArrayList<>();
                images1.add(new FileInputStream(appConfig.getUavModelPath() + images.get(i)));
                template.replaceDocumentImage(placeholder,images1,310,180);
            }

            //generate file
            String newFileName =CommonUtils.getUUID();
            String newfoldeName =CommonUtils.currentDateToStr(7);
            String docSavePath = "/" + data.getString("userAccount") + "/" + newfoldeName + "/" + newFileName + ".docx";
            File folderDir = new File(appConfig.getUavModelPath() + "/" + data.getString("userAccount") + "/" + newfoldeName);
            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outputFile = new File(appConfig.getUavModelPath() + docSavePath);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);

            //result
            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);

        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }
    }

}
