package com.quickex.service.layer.impl;

import com.quickex.service.layer.IUpLoadService;
import org.springframework.stereotype.Service;

//package com.quickex.service.layer.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.quickex.core.config.KoConfig;
//import com.quickex.core.config.ServerConfig;
//import com.quickex.core.constant.Constants;
//import com.quickex.core.constant.FileTypeEnum;
//import com.quickex.core.result.R;
//import com.quickex.core.util.DateUtils;
//import com.quickex.core.util.UnPackeUtil;
//import com.quickex.core.util.file.FileUploadUtils;
//import com.quickex.core.util.uuid.IdUtils;
//import com.quickex.domain.layer.KoShpFile;
//import com.quickex.mapper.layer.KoShpFileMapper;
//import com.quickex.service.layer.IUpLoadService;
//import com.vividsolutions.jts.geom.Geometry;
//import lombok.extern.slf4j.Slf4j;
//import org.geotools.data.shapefile.ShapefileDataStore;
//import org.geotools.data.simple.SimpleFeatureIterator;
//import org.geotools.data.simple.SimpleFeatureSource;
//import org.geotools.geojson.feature.FeatureJSON;
//import org.geotools.geometry.jts.JTS;
//import org.geotools.referencing.CRS;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.referencing.FactoryException;
//import org.opengis.referencing.crs.CoordinateReferenceSystem;
//import org.opengis.referencing.operation.MathTransform;
//import org.opengis.referencing.operation.TransformException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
@Service
public class UpLoadImpl implements IUpLoadService {

//    @Autowired
//    private ServerConfig serverConfig;
//    @Resource
//    private KoShpFileMapper egovFileMapper;
//
//
//    public R upLoadZip(MultipartFile zipFile) throws IOException, FactoryException, TransformException {
//        if (null == zipFile) {
//            return R.error("Please upload the compressed file");
//        }
//        // File name before upload
//        String fileDesc = zipFile.getOriginalFilename();
//        // Get suffix
//        String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));
//        //Judge whether it is zip
//        if (!suffixName.equalsIgnoreCase(FileTypeEnum.FILE_TYPE_ZIP.fileStufix)) {
//            return R.error("Please upload the compressed file");
//        }
//        // Upload and return new file name
//        HashMap result = FileUploadUtils.uploadZip(KoConfig.getUploadPath(), zipFile);
//        File file = (File)result.get("file");
//        String webFileName = (String)result.get("webFileName");
//        // Uploaded file name
//        String fileName = file.getName();
//
//        String webUrl = serverConfig.getUrl() + webFileName;
//
//        //Unzip the zip file
//        //Decompression path (decompression address + uploaded new file name)
//        String unFileNme = fileName.substring(0, fileName.lastIndexOf("."));
//        String unZipPath = KoConfig.getUnZipPath()+File.separator+unFileNme;
//        long start = System.currentTimeMillis();
//        UnPackeUtil.unPackZip(file, unZipPath);
//        log.info("Decompression is completed, taking a total of time"+(System.currentTimeMillis() - start)+"ms");
//
//        buildZipFileData(webFileName,unFileNme,fileDesc,fileName);
//        //Get the extracted file
//        List<String> zipChildrenFiles = getZipChildrenFile(unZipPath);
//        if (zipChildrenFiles.size() < 0) {
//            return R.error("There are no files in the package!");
//        }
//        //Get the 3DS model file（.gltf/.glb）
//        List<String> fileBy3d = get3dFile(zipChildrenFiles);
//        if (fileBy3d.size() < 0) {
//            return R.error("There is no 3D model file in the compressed package, please upload Gltf or Model file in GLB format!");
//        }
//        //Get SHP file
//        List<String> shpFile = shpFile = getShpFile(zipChildrenFiles);
//        if (shpFile.size() < 0) {
//            return R.error("There is no SHP file in the compressed package!");
//        }
//        String shpFilePath = shpFile.get(0);
////        File shapeFile  = new File(shpFilePath);
//
//        //Read SHP file
//        R ajaxResult = readShp(shpFilePath, fileBy3d, unFileNme);
//
//        ajaxResult.put("zipName", fileName);
//        ajaxResult.put("zipPath", webUrl);
//        return R.success();
//    }
//    private KoShpFile buildZipFileData(String webFileName, String unFileNme, String fileDesc, String fileName) {
//        KoShpFile egovFile = new KoShpFile();
//        String fileId = fileName.substring(0, fileName.lastIndexOf("."));
//        String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));
//        String unZipPath = Constants.RESOURCE_PREFIX + "/" + "unZip" + "/" + unFileNme;
//        egovFile.setFileId(fileId);
//        egovFile.setFileName(fileName);
//        egovFile.setFileDesc(fileDesc);
//        egovFile.setFileUnzipPath(unZipPath);
//        egovFile.setFileUploadPath(webFileName);
//        egovFile.setFileSuffix(suffixName);
//        egovFile.setCreateTime(DateUtils.getNowDate());
//        egovFile.setUpdateTime(DateUtils.getNowDate());
//        egovFile.setDelFlag(0L);
//        int i = egovFileMapper.insertEgovFile(egovFile);
//        return egovFile;
//    }
//
//
//    /**
//     * Get all subordinate files under the unzipped directory, excluding subdirectories under the directory
//     * @param unZipPath
//     * @return
//     */
//    private List<String> getZipChildrenFile(String unZipPath) {
//        List<String> files = new ArrayList<String>();
//        File file = new File(unZipPath);
//        File[] tempList = file.listFiles();
//
//        for (int i = 0; i < tempList.length; i++) {
//            if (tempList[i].isFile()) {
//                //File path
//                files.add(tempList[i].toString());
//                //File name, excluding path
//                //String fileName = tempList[i].getName();
//                log.info("Unzip file----"+tempList[i].toString());
//            }
//            if (tempList[i].isDirectory()) {
//                //There is no recursion here，
//            }
//        }
//        return files;
//    }
//
//    private List<String> get3dFile(List<String> zipChildrenFiles) {
//        List<String> fileBy3ds = new ArrayList<String>();
//        List<String> fileByGltf = new ArrayList<String>();
//        List<String> fileByGlb = new ArrayList<String>();
//        for (String file: zipChildrenFiles) {//fIle is the full file path
//            // Get suffix
//            String suffixName = file.substring(file.lastIndexOf("."));
//            if (suffixName.equalsIgnoreCase(FileTypeEnum.FILE_TYPE_3DS.fileStufix)) {
//                fileBy3ds.add(file);
//            }
//            if (suffixName.equalsIgnoreCase(FileTypeEnum.FILE_TYPE_GLTF.fileStufix)) {
//                fileByGltf.add(file);
//            }
//            if (suffixName.equalsIgnoreCase(FileTypeEnum.FILE_TYPE_GLB.fileStufix)) {
//                fileByGlb.add(file);
//            }
//        }
////        if (fileByGltf.size() > 0 && fileByGlb.size() > 0) {
////            //Both file formats exist, merge and de duplicate
////            List<String> collect = Stream.of(fileByGltf, fileByGlb)
////                    .flatMap(Collection::stream)
////                    .distinct()
////                    .collect(Collectors.toList());
////            return collect;
////        }
////        if (fileBy3ds.size() > 0) {
////            return fileBy3ds;
////        }
//        if (fileByGltf.size() > 0) {
//            return fileByGltf;
//        }
//        if (fileByGlb.size() > 0) {
//            return fileByGlb;
//        }
//        return fileByGltf;
//    }
//
//    private List<String> getShpFile(List<String> zipChildrenFiles) {
//        List<String> files = new ArrayList<String>();
//        for (String file: zipChildrenFiles) {
//            // Get suffix
//            String suffixName = file.substring(file.lastIndexOf("."));
//            if (suffixName.equalsIgnoreCase(FileTypeEnum.FILE_TYPE_SHP.fileStufix)) {
//                files.add(file);
//            }
//        }
//        return files;
//    }
//
//
//
//    /**
//     * Get the feature from SHP according to the model name
//     * @param names
//     * @param sfSource feature Iterator source data
//     * @return
//     */
//    public List<SimpleFeature> getFeaturesByNames(List<String> names, SimpleFeatureSource sfSource) throws IOException {
//        SimpleFeatureIterator sfIter = null;
//        List<SimpleFeature> features = new ArrayList<>();
//
//        for (String name : names) {
//            sfIter = sfSource.getFeatures().features();
//            while (sfIter.hasNext()) {
//                SimpleFeature feature = (SimpleFeature) sfIter.next();
//                if (is3dsData(name,feature)) {
//                    features.add(feature);
//                    break;
//                }
//            }
//        }
//        return features;
//    }
//
//    private KoShpFile build3DFileData(String id,String unFilePath,String fileName,JSONObject jsonObject,String unFileNme) {
//        KoShpFile egovFile = new KoShpFile();
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        egovFile.setFileId(id);
//        egovFile.setFileName(fileName);
//        egovFile.setFileDesc(fileName);
//        egovFile.setFileUploadPath(unFilePath);
//        egovFile.setFileSuffix(suffixName);
//        egovFile.setFileShpJson(jsonObject.toJSONString());
//        egovFile.setCreateTime(DateUtils.getNowDate());
//        egovFile.setUpdateTime(DateUtils.getNowDate());
//        egovFile.setFileParentId(unFileNme);
//        egovFile.setDelFlag(0L);
//        int i = egovFileMapper.insertEgovFile(egovFile);
//        return egovFile;
//    }
//
//
//    public R readShp(String shpFilePath,List<String> names,String unFileNme) throws IOException, TransformException, FactoryException {
//        long start = System.currentTimeMillis();
//        log.info("Start parsing SHP file----"+shpFilePath);
//
//        // Reading ShapeFile files using geotools
//        File shapeFile = new File(shpFilePath);
//        ShapefileDataStore store = new ShapefileDataStore(shapeFile.toURI().toURL());
//        //Set encoding
//        Charset charset = Charset.forName("EUC_KR");
//        store.setCharset(charset);
//        SimpleFeatureSource sfSource = store.getFeatureSource();
//        // The assembly model file address (relative path) traverses each feature from the ShapeFile file, and then converts the feature to a geojson string
//        List<SimpleFeature> featuresByNames = getFeaturesByNames(names, sfSource);
//        List<Map<String, Object>> rows = new ArrayList<>();
//        for (SimpleFeature feature: featuresByNames) {
//            Map<String, Object> obj = new HashMap<>();
//            String id = IdUtils.fastSimpleUUID();
//            obj.put("id", id);
//
//            obj.put("time", DateUtils.dateSimple());
//            //Assembly model file address (relative path)
//            String unFilePath = getRelative3dFilePath(names, feature,unFileNme);
//            String fileName = getFileNameByFileWebPath(unFilePath);
//            String webFilePath = serverConfig.getUrl() + unFilePath;
//            obj.put("name", fileName);
//            obj.put("filePath", webFilePath);
//            //Coordinate system conversion
//            Geometry geometry = (Geometry)feature.getDefaultGeometry();
//            CoordinateReferenceSystem crsresource = CRS.decode("EPSG:5186");
//            CoordinateReferenceSystem crsTarget = CRS.decode("EPSG:4326");
//            MathTransform transform = CRS.findMathTransform(crsresource, crsTarget,true);
//            Geometry transform1 = JTS.transform(geometry, transform);
//            feature.setAttribute("the_geom",transform1);
//            // Feature convert GeoJSON
//            FeatureJSON fjson = new FeatureJSON();
//            StringWriter writer = new StringWriter();
//            fjson.writeFeature(feature, writer);
//            String sjson = writer.toString();
////            log.info(feature.getAttribute("UTL3D_FTE").toString());
////            log.info("sjson=====  >>>>  "  + sjson);
//            //Resolve feature properties
//            JSONObject jsonObject = JSON.parseObject(sjson);
//            obj.put("attribute", jsonObject.get("properties"));
//            obj.put("geometry", jsonObject.get("geometry"));
//            build3DFileData(id,unFilePath,fileName,jsonObject,unFileNme);
//            rows.add(obj);
//        }
//        R result = R.success();
//        result.put("total", rows.size());
//        result.put("rows", rows);
//        System.out.println("The data analysis is completed, which takes a total of time"+(System.currentTimeMillis() - start)+"ms");
//        return result;
//    }
//
//
//    public boolean is3dsData(String fileBy3d, SimpleFeature feature) {
//        File file = new File(fileBy3d);
//        String name = file.getName();
//        String fileName = name.substring(0,name.lastIndexOf("."))+".3ds";
//        if (feature.getAttribute("LODlink").toString().equalsIgnoreCase(fileName)) {
//            return true;
//        }
//        return false;
//    }
//
//    public String getRelative3dFilePath(List<String> files, SimpleFeature feature,String unFileNme) {
//        String filePath = "";
//        for (String file: files) {
//            if (is3dsData(file, feature)) {
//                File tmpfile = new File(file);
//                String fileName = tmpfile.getName();
//                filePath = Constants.RESOURCE_PREFIX + "/" + "unZip" + "/" + unFileNme +"/"+ fileName;
//            }
//        }
//        return filePath;
//    }
//
//    private String getAbsolute3dFilePath(String relative3dFilePath) {
//        String absolute3dFilePath = relative3dFilePath.replace(Constants.RESOURCE_PREFIX, KoConfig.getUploadPath()+"/");
//        return absolute3dFilePath;
//    }
//
//    public String getFileNameByFileWebPath(String file) {
//        String suffixName = file.substring(file.lastIndexOf("/")+1);
//        return suffixName;
//    }
}
