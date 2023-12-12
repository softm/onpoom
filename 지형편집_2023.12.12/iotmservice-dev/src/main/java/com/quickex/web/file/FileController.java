package com.quickex.web.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.domain.doc.UserCulturalHeritageCreateSHPModel;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.FSZipUtils;
import com.quickex.utils.ShapeUtil;
import com.quickex.utils.WKTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.taglibs.standard.tag.common.fmt.RequestEncodingSupport;
import org.kabeja.batik.tools.SAXPDFSerializer;
import org.kabeja.batik.tools.SAXPNGSerializer;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.SAXGenerator;
import org.kabeja.xml.SAXSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private AppConfig appConfig;

    @PostMapping("/upload")
    public R upload(@RequestParam(value = "file",required = false) MultipartFile file,String userAccount){
        try {
            if (file == null || userAccount==null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" +folderName;
            String filePath = folder + "/" + newName  + suffixName;
            String url = "/" + userAccount+"/"+ folderName + "/" + newName + suffixName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            JSONObject data = new JSONObject();
            data.put("fileName", newName);
            data.put("fileSize", file.getSize());
            data.put("fileSuffix", suffixName);
            data.put("fileUrl", url);
            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }

    @PostMapping("/uploadNSA")
    public R uploadNSA(@RequestParam(value = "file",required = false) MultipartFile file){
        try {
            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = "analysis_" + String.valueOf(System.currentTimeMillis());
            String folder = appConfig.getNsaFilePath();
            String filePath = folder + "/" + newName  + suffixName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            JSONObject data = new JSONObject();
            data.put("fileName", newName+suffixName);

            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }

    @PostMapping("/uploadSHARE")
    public R uploadSHARE(@RequestParam(value = "file",required = false) MultipartFile file,String userAccount){
        try {
            if (file == null || userAccount==null) {
                return R.error();
            }

            log.info("getLxFilePath>>>>>>>>>" + appConfig.getLxFilePath() + "<<<<<<<<<<");

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getLxFilePath() + "/files/" + userAccount + "/" +folderName;
            String filePath = folder + "/" + newName  + suffixName;
            String url = "/files/" + userAccount+"/"+ folderName + "/" + newName + suffixName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            JSONObject data = new JSONObject();
            data.put("fileName", newName);
            data.put("fileSize", file.getSize());
            data.put("fileSuffix", suffixName);
            data.put("fileUrl", url);
            return R.success(data);
        } catch (Exception ex) {
            log.error("msg",ex);
            return R.error();
        }
    }


    @PostMapping("/balancedUploadGeojson")
    public R balancedUploadGeojson(@RequestBody String json){

        try {

            //log.info("getLxFilePath>>>>>>>>>" + appConfig.getLxFilePath() + "<<<<<<<<<<");

            JSONObject data = JSON.parseObject(json);
            String userAccount = data.getString("userAccount");

            String unix = String.valueOf(System.currentTimeMillis());

            String folderName = userAccount + "/" + unix;
            String folderPath = appConfig.getLxFilePath() + "/" + folderName;


            //log.info("getLxFilePath11111>>>>>>>>>" + folderPath + "<<<<<<<<<<");

            File fileDir = new File(folderPath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String fileName1 = "analysis_" + unix + "_A.geojson";
            String fileName2 = "analysis_" + unix + "_B.geojson";
            String fileName3 = "analysis_" + unix + "_C.geojson";
            String fileName4 = "analysis_" + unix + "_D.geojson";

            String str1 = data.getString("geoJson_A_Str");
            String str2 = data.getString("geoJson_B_Str");
            String str3 = data.getString("geoJson_C_Str");
            String str4 = data.getString("geoJson_D_Str");

            File file1 = new File(folderPath + "/" +fileName1);
            if (!file1.exists())
            {
                //log.info("createNewFile>>>>>>>>>" + folderPath + "<<<<<<<<<<");
                file1.createNewFile();
            }

            //log.info("createNewFile>>>>>>>>>2222222222<<<<<<<<<<");
            FileOutputStream  fw1 = new FileOutputStream(folderPath + "/" +fileName1);
            fw1.write(str1.getBytes("utf-8"));
            fw1.close();
            //log.info("createNewFile>>>>>>>>>3333333333<<<<<<<<<<");

            File file2 = new File(folderPath + "/" +fileName2);
            if (!file2.exists())
            {
                file2.createNewFile();
            }
            FileOutputStream  fw2 = new FileOutputStream (folderPath + "/" +fileName2);
            fw2.write(str2.getBytes("utf-8"));
            fw2.close();

            File file3 = new File(folderPath + "/" +fileName3);
            if (!file3.exists())
            {
                file3.createNewFile();
            }
            FileOutputStream  fw3 = new FileOutputStream (folderPath + "/" +fileName3);
            fw3.write(str3.getBytes("utf-8"));
            fw3.close();

            File file4 = new File(folderPath + "/" +fileName4);
            if (!file4.exists())
            {
                file4.createNewFile();
            }
            FileOutputStream  fw4 = new FileOutputStream (folderPath + "/" +fileName4);
            fw4.write(str4.getBytes("utf-8"));
            fw4.close();

            JSONObject result = new JSONObject();
//            result.put("url", "/" + userAccount + "/" + unix);
            result.put("filePath", folderPath);
           // result.put("filePath", "/" + userAccount + "/" + unix);
            result.put("fileName", "analysis_" + unix);
            return R.success(result);
        } catch (Exception ex) {
            log.error("balancedUploadGeojson msg",ex);
            ex.printStackTrace();
            return R.error();
        }
    }

    @PostMapping("/balancedUploadWKT")
    public R balancedUploadWKT(@RequestBody String json) {
        try {
            JSONObject data = JSON.parseObject(json);
            List<UserCulturalHeritageCreateSHPModel> geoms = new ArrayList<>();
            List<String> wktList = JSON.parseArray(data.getString("wkts"), String.class);

            for (int i = 0; i < wktList.size(); i++) {
                UserCulturalHeritageCreateSHPModel item = new UserCulturalHeritageCreateSHPModel();
                item.setGeom(WKTUtil.wktToGeom(wktList.get(i)));
                geoms.add(item);
            }

            String userAccount = data.getString("userAccount");

            String newName = CommonUtils.getUUID();
            String folderName = userAccount + "/" + CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
            String folder = appConfig.getUavModelPath() + "/" + folderName;
            String filePath = folder + "/" + newName + ".shp";

            ShapeUtil.write2Shape(filePath, "EUC_KR", "MultiPolygon", geoms);
            ShapeUtil.zipShapeFile(filePath);

            String url = "/" + folderName + "/" + newName + ".zip";
            JSONObject result = new JSONObject();
            result.put("url", url);
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }

    @PostMapping("/balancedUploadSKP")
    public R uploadNSA(MultipartFile file,String userAccount){
        try {
            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = "skp_" + String.valueOf(System.currentTimeMillis());
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getLxFilePath() + "/skp/" + userAccount + "/" +folderName;
            String filePath = folder + "/" + newName  + suffixName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            JSONObject result = new JSONObject();
            result.put("filePath", folder);
            result.put("fileName", newName+suffixName);

            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }

    @PostMapping("/balancedUploadGeojson1")
    public R balancedUploadGeojson1(@RequestBody String json){

        try {
            JSONObject data = JSON.parseObject(json);
            String userAccount = data.getString("userAccount");

            String unix = String.valueOf(System.currentTimeMillis());

            String folderName = userAccount + "/" + unix;
            String folderPath = appConfig.getLxFilePath() + "/" + folderName;

            File fileDir = new File(folderPath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String fileName1 = "analysis_" + unix + "_A.geojson";
            String str1 = data.getString("geoJson_A_Str");

            File file1 = new File(folderPath + "/" +fileName1);
            if (!file1.exists())
            {
                file1.createNewFile();
            }
            FileOutputStream  fw1 = new FileOutputStream(folderPath + "/" +fileName1);
            fw1.write(str1.getBytes("utf-8"));
            fw1.close();

            JSONObject result = new JSONObject();
            result.put("filePath", folderPath);
            result.put("fileName", "analysis_" + unix);
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }


    //pdf
    @PostMapping("/uploadDxf")
    public R uploadDxf(@RequestParam(value = "file", required = false) MultipartFile file, String userAccount) {
        try {
            if (file == null || userAccount == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName;
            String filePath = folder + "/" + newName + suffixName;


            if (!suffixName.toLowerCase().equals(".dxf")) {
                return R.error("the file is not a DXF file!");
            }

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            String pdfName = CommonUtils.getUUID();
            String outPath = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName + "/" + pdfName + ".pdf";
            String url = "/" + userAccount + "/" + folderName + "/" + pdfName + ".pdf";

            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(new FileInputStream(filePath), "EUC_KR");
            DXFDocument doc = dxfParser.getDocument();
            SAXGenerator generator = new SVGGenerator();
            SAXSerializer out = new org.kabeja.batik.tools.SAXPDFSerializer();
            OutputStream fileo = new FileOutputStream(outPath);
            out.setOutput(fileo);
            generator.generate(doc, out, new HashMap());

            JSONObject data = new JSONObject();
            data.put("fileUrl", url);
            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }

    //pdf
    @PostMapping("/uploadDxf1")
    public R uploadDxf1(@RequestParam(value = "file", required = false) MultipartFile file, String userAccount) {
        try {
            if (file == null || userAccount == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName;
            String filePath = folder + "/" + newName + suffixName;


            if (!suffixName.toLowerCase().equals(".dxf")) {
                return R.error("the file is not a DXF file!");
            }

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            String pdfName = CommonUtils.getUUID();
            String outPath = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName + "/" + pdfName + ".pdf";
            String url = "/" + userAccount + "/" + folderName + "/" + pdfName + ".pdf";

            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(new FileInputStream(filePath), "EUC_KR");
            DXFDocument doc = dxfParser.getDocument();
            SAXGenerator generator = new SVGGenerator();
            SAXSerializer out = new org.kabeja.batik.tools.SAXPDFSerializer();
            OutputStream fileo = new FileOutputStream(outPath);
            out.setOutput(fileo);
            generator.generate(doc, out, new HashMap());

            String dxfUrl =  "/" + userAccount + "/" + folderName + "/" + newName + suffixName;

            JSONObject data = new JSONObject();
            data.put("pdfUrl", url);
            data.put("dxfUrl", dxfUrl);
            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }

    //png
    @PostMapping("/uploadDxf2")
    public R uploadDxf2(@RequestParam(value = "file", required = false) MultipartFile file, String userAccount) {
        try {
            if (file == null || userAccount == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName;
            String filePath = folder + "/" + newName + suffixName;


            if (!suffixName.toLowerCase().equals(".dxf")) {
                return R.error("the file is not a DXF file!");
            }

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            String pdfName = CommonUtils.getUUID();
            String outPath = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName + "/" + pdfName + ".png";
            String url = "/" + userAccount + "/" + folderName + "/" + pdfName + ".png";

            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(new FileInputStream(filePath), "EUC_KR");
            DXFDocument doc = dxfParser.getDocument();
            SAXGenerator generator = new SVGGenerator();
            SAXSerializer out = new org.kabeja.batik.tools.SAXPNGSerializer();
            Map m=new HashMap();
            m.put("width", "1024");
            m.put("height", "768");
            out.setProperties(m);
            OutputStream fileo = new FileOutputStream(outPath);
            out.setOutput(fileo);
            generator.generate(doc, out, new HashMap());

            String dxfUrl =  "/" + userAccount + "/" + folderName + "/" + newName + suffixName;

            JSONObject data = new JSONObject();
            data.put("imgUrl", url);
            data.put("dxfUrl", dxfUrl);
            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }

    //jpg
    @PostMapping("/uploadDxf3")
    public R uploadDxf3(@RequestParam(value = "file", required = false) MultipartFile file, String userAccount) {
        try {
            if (file == null || userAccount == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName;
            String filePath = folder + "/" + newName + suffixName;


            if (!suffixName.toLowerCase().equals(".dxf")) {
                return R.error("the file is not a DXF file!");
            }

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            String pdfName = CommonUtils.getUUID();
            String outPath = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName + "/" + pdfName + ".jpg";
            String url = "/" + userAccount + "/" + folderName + "/" + pdfName + ".jpg";

            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(new FileInputStream(filePath), "EUC_KR");
            DXFDocument doc = dxfParser.getDocument();
            SAXGenerator generator = new SVGGenerator();// SAXJEPGSerializer
            SAXSerializer out = new org.kabeja.batik.tools.SAXJPEGSerializer();
            Map m=new HashMap();
            m.put("width", "1024");
            m.put("height", "768");
            out.setProperties(m);
            OutputStream fileo = new FileOutputStream(outPath);
            out.setOutput(fileo);
            generator.generate(doc, out, new HashMap());

            String dxfUrl =  "/" + userAccount + "/" + folderName + "/" + newName + suffixName;

            JSONObject data = new JSONObject();
            data.put("imgUrl", url);
            data.put("dxfUrl", dxfUrl);
            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }

    //svg
    @PostMapping("/uploadDxf4")
    public R uploadDxf4(@RequestParam(value = "file", required = false) MultipartFile file, String userAccount) {
        try {
            if (file == null || userAccount == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName;
            String filePath = folder + "/" + newName + suffixName;


            if (!suffixName.toLowerCase().equals(".dxf")) {
                return R.error("the file is not a DXF file!");
            }

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            String pdfName = CommonUtils.getUUID();
            String outPath = appConfig.getUavModelPath() + "/" + userAccount + "/" + folderName + "/" + pdfName + ".svg";
            String url = "/" + userAccount + "/" + folderName + "/" + pdfName + ".svg";

            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(new FileInputStream(filePath), "EUC_KR");
            DXFDocument doc = dxfParser.getDocument();
            SAXGenerator generator = new SVGGenerator();// SAXJEPGSerializer
            //org.kabeja.batik.tools.
            SAXSerializer out = new org.kabeja.batik.tools.SAXPNGSerializer();
//            Map m=new HashMap();
//            m.put("width", "1024");
//            m.put("height", "768");
//            out.setProperties(m);
            OutputStream fileo = new FileOutputStream(outPath);
            out.setOutput(fileo);
            generator.generate(doc, out, new HashMap());

            String dxfUrl =  "/" + userAccount + "/" + folderName + "/" + newName + suffixName;

            JSONObject data = new JSONObject();
            data.put("imgUrl", url);
            data.put("dxfUrl", dxfUrl);
            return R.success(data);
        } catch (Exception ex) {
            return R.error();
        }
    }



    @PostMapping("/uploadFile2")
    public R uploadFile2(String userAccount, MultipartFile file){

        try {
            if (file == null || userAccount == null) {
                return R.error();
            }

            String unix = String.valueOf(System.currentTimeMillis());

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = userAccount + "/" + unix;
            String folder = appConfig.getNsaFilePath() + "/" +folderName;
            String filePath = folder + "/" + "analysis_" + unix + "_A"  + suffixName;

//            if (!suffixName.toLowerCase().equals(".xlsx")) {
//                return R.error("the file is not a .xlsx file!");
//            }

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            JSONObject result = new JSONObject();
            result.put("filePath", folder);
            result.put("fileName", "analysis_" + unix + "_A"  + suffixName);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }



    @PostMapping("/uploadFileInDir")
    public R uploadFileInDir(String dir, MultipartFile file){

        try {
            if (file == null || dir == null) {
                return R.error();
            }

            String unix = String.valueOf(System.currentTimeMillis());

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String fileName = "file_" + unix  + suffixName;
            String filePath = dir + "/" + fileName;


            File fileDir = new File(dir);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File uploadFile = new File(filePath);
            file.transferTo(uploadFile);

            JSONObject result = new JSONObject();
            result.put("filePath", dir);
            result.put("fileName", fileName);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }




    @PostMapping("/downloadGeojsonFile")
    public R downloadGeojsonFile(@RequestBody String json){

        try {
//            JSONObject data = JSON.parseObject(json);
//            String userAccount = data.getString("userAccount");
//
//            String newName = CommonUtils.getUUID();
//            String folderName = CommonUtils.currentDateToStr(7);
//            String folder = appConfig.getUavModelPath() + "/" + userAccount + "/" +folderName;
//            String filePath = folder + "/" + newName  + ".geojson";
//            String url = "/" + userAccount+"/"+ folderName + "/" + newName +  ".geojson";
//
//            File fileDir = new File(folder);
//            if (!fileDir.exists() && !fileDir.isDirectory()) {
//                fileDir.mkdirs();
//            }
//
//            String str1 = data.getString("geojson");
//
//            File file1 = new File(filePath);
//            file1.createNewFile();
//            if (!file1.exists())
//            {
//                file1.createNewFile();
//            }
//            FileOutputStream  fw1 = new FileOutputStream(filePath);
//            fw1.write(str1.getBytes("utf-8"));
//            fw1.close();
//
//            JSONObject result = new JSONObject();
//            result.put("url", url);
//            return R.success(result);

            String unix = String.valueOf(System.currentTimeMillis());


            JSONObject data = JSON.parseObject(json);
            String userAccount = data.getString("userAccount");

            String newName = unix;//CommonUtils.getUUID();

            String folder = appConfig.getLxFilePath() + "/Rhino/wind/"+unix;
            String filePath = folder + "/" + newName  + ".geojson";
            String url =  "/Rhino/wind/"+unix+"/" + newName +  ".geojson";

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String str1 = data.getString("geojson");

            File file1 = new File(filePath);
            file1.createNewFile();
            if (!file1.exists())
            {
                file1.createNewFile();
            }
            FileOutputStream  fw1 = new FileOutputStream(filePath);
            fw1.write(str1.getBytes("utf-8"));
            fw1.close();

            JSONObject result = new JSONObject();
            result.put("url", url);
            result.put("fileName", newName +  ".geojson");
            result.put("filePath", folder);
            result.put("unix", unix);

            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }



    @PostMapping("/uploadEcw")
    public R uploadEcw(MultipartFile file,String userAccount){
        try {

            if (userAccount == null || userAccount.isEmpty()) {
                return R.error();
            }

            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));

            if (!suffixName.equalsIgnoreCase(".zip")) {
                return R.error("is not zip file!");
            }

            String zipUuid = CommonUtils.getUUID();
            String zipFileNmae = zipUuid+".zip";

            String basePath = appConfig.getLxFilePath() + "/ecw_folde/in/" + userAccount + "/" + CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
            String zipBase = basePath;
            String zipBasePath = zipBase + "/" + zipFileNmae;

            File fileDir = new File(basePath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File zipFile = new File(zipBasePath);

//            zipFile.getParentFile().mkdirs();
//            if (zipFile.exists()) {
//                zipFile.delete();
//            }
            file.transferTo(zipFile);

            FSZipUtils.unzip(zipFile, zipBase, false);

            File tempFile = new File(zipBase);
            File[] fs = tempFile.listFiles();

            boolean ecwCheck = false;
            boolean prjCheck = false;

            String ecwName = "";
            String prjName = "";

            //cheeck .ecw   / .prj
            for (File f : fs) {
                if (!f.isDirectory()) {
                    String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));
                    if (fileSuffix.toLowerCase().equals(".ecw")) {
                        ecwCheck = true;
                        String uuid = CommonUtils.getUUID();
                        f.renameTo(new File(basePath + "/" + uuid + ".ecw"));
                        ecwName = uuid + ".ecw";
                    }
                    if (fileSuffix.toLowerCase().equals(".prj")) {
                        prjCheck = true;
                        String uuid = CommonUtils.getUUID();
                        f.renameTo(new File(basePath + "/" + uuid + ".prj"));
                        prjName = uuid + ".prj";
                    }
                }
            }

            if(ecwCheck==false){
                return R.error("can't find .ecw file");
            }

            if(prjCheck==false){
                return R.error("can't find .prj file");
            }

            JSONObject result = new JSONObject();
            result.put("filePath", basePath);
            result.put("ecwName", ecwName);
            result.put("prjName", prjName);
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    @PostMapping("/downloadGeojsonFile1")
    public R downloadGeojsonFile1(@RequestBody String json){

        try {
            String unix = String.valueOf(System.currentTimeMillis());

            JSONObject data = JSON.parseObject(json);
            String userAccount = data.getString("userAccount");

            String newName = unix;//CommonUtils.getUUID();

            String folder = appConfig.getLxFilePath() + "/Rhino/noise/"+unix;
            String filePath = folder + "/" + newName  + ".geojson";
            String url =  "/Rhino/noise/"+unix+"/" + newName +  ".geojson";

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            String str1 = data.getString("geojson");

            File file1 = new File(filePath);
            file1.createNewFile();
            if (!file1.exists())
            {
                file1.createNewFile();
            }
            FileOutputStream  fw1 = new FileOutputStream(filePath);
            fw1.write(str1.getBytes("utf-8"));
            fw1.close();

            JSONObject result = new JSONObject();
            result.put("url", url);
            result.put("fileName", newName +  ".geojson");
            result.put("filePath", folder);
            result.put("unix", unix);

            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }






}
