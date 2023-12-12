package com.quickex.service.mapfile.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.AppConfig;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.mapfile.Dto.KoMapFileDto;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.mapfile.KoMapFileCollection;
import com.quickex.domain.mapfile.KoMapFileLabel;
import com.quickex.domain.mapfile.KoMapFileType;
import com.quickex.domain.user.KoUser;
import com.quickex.mapper.layer.KoLayerServiceMapper;
import com.quickex.mapper.mapfile.KoMapFileMapper;
import com.quickex.mapper.mapfile.KoMapFileTypeMapper;
import com.quickex.service.mapfile.IKoMapFileCollectionService;
import com.quickex.service.mapfile.IKoMapFileService;
import com.quickex.service.mapfile.IKoMapFileTypeService;
import com.quickex.service.user.IKoUserService;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.FSZipUtils;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


@Log4j
@Service
public class KoMapFileServiceImpl extends BaseServiceImpl<KoMapFileMapper, KoMapFile> implements IKoMapFileService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private KoLayerServiceMapper koLayerServiceMapper;

    @Autowired
    private IKoMapFileCollectionService collectionService;

    @Autowired
    private IKoMapFileTypeService typeService;

    @Autowired
    private IKoUserService userService;


    public R getAdminRootTypeFiles(KoMapFile entity){
        List<KoMapFile> list =this.baseMapper.getAdminRootTypeFiles(entity);
        return R.success(list);
    }







    @Transactional
    public R upload(MultipartFile image, MultipartFile model, String treeId, String createUser, String isAdmin) {
        try {

            KoUser user = userService.getById(createUser);

            KoMapFileType entity = typeService.getById(treeId);

            if(entity==null){
                return R.error("type is null!");
            }

            if(isAdmin.equals("1")){
                if(entity.getParentId().equals("-1")){
                    return R.error("root node cannot bind model!");
                }
            }

            String ModelFileOldNmae = model.getOriginalFilename();
            String imgFileOldNmae = image.getOriginalFilename();

            String modelSuffixName = ModelFileOldNmae.substring(ModelFileOldNmae.lastIndexOf("."));
            String imgSuffixName = imgFileOldNmae.substring(imgFileOldNmae.lastIndexOf("."));

            if (modelSuffixName.equals(".glb")||modelSuffixName.equals(".gltf")) {

            }else{
                return R.error("the file is not a model file .glb/.gltf");
            }

            String modelFileNewName = CommonUtils.getUUID();
            String imgFileNewName = CommonUtils.getUUID();

            String basePath = appConfig.getMapFilePath() + "/" + createUser;

            File fileDir = new File(basePath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            File modelFile = new File(basePath + "/" + modelFileNewName + modelSuffixName);
            modelFile.getParentFile().mkdirs();
            model.transferTo(modelFile);

            File imgFile = new File(basePath + "/" + imgFileNewName + imgSuffixName);
            imgFile.getParentFile().mkdirs();
            image.transferTo(imgFile);

            KoMapFile item = new KoMapFile();
            item.setFileId(modelFileNewName);
            item.setTreeId(treeId);
            item.setFileSuffix(modelSuffixName);
            item.setCreateTime(new Date());
            item.setCreateUser(createUser);
            item.setFileName(ModelFileOldNmae.substring(0, ModelFileOldNmae.indexOf(".")));
            item.setFileSize((int) modelFile.length());
            item.setIsAdmin(isAdmin);
            item.setFileUrl("/" + createUser + "/" + modelFileNewName + modelSuffixName);
            item.setImgPath("/" + createUser + "/" + imgFileNewName + imgSuffixName);
            if(user!=null){
                item.setOrganizationId(user.getOrganizationId());
            }
            save(item);
            return R.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    @Transactional
    public R upload1(MultipartFile file, String treeId, String createUser, String isAdmin) {
        try {

            KoUser user = userService.getById(createUser);

            KoMapFileType entity = typeService.getById(treeId);

            if(entity==null){
                return R.error("type is null!");
            }

            if(isAdmin.equals("1")){
                if(entity.getParentId().equals("-1")){
                    return R.error("root node cannot bind model!");
                }
            }

            //process zip
            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));
            if (!suffixName.equalsIgnoreCase(".zip")) {
                return R.error("the uploaded file is not a zip file！");
            }
            String zipUuid = CommonUtils.getUUID();
            String zipFileNmae = zipUuid+".zip";
            String basePath = appConfig.getMapFilePath() + "/" + createUser +"/";
            String zipBase = basePath + zipUuid;
            String zipBasePath = zipBase + "/" + zipFileNmae;
            //chenck folder
            File fileDir =new File(zipBasePath);
            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                fileDir .mkdir();
            }
            //upload zip
            File zipFile = new File(zipBasePath);
            zipFile.getParentFile().mkdirs();
            if (zipFile.exists()) {
                zipFile.delete();
            }
            file.transferTo(zipFile);
            FSZipUtils.unzip(zipFile, zipBase, false);  //unzip
            //delFile(zipFile);  //delete zip

            List<File> modelList = new ArrayList<>();
            List<File> imglList = new ArrayList<>();

            File tempFile = new File(zipBase);
            File[] fs = tempFile.listFiles();

            for (File f : fs) {
                if (!f.isDirectory()) {
                    String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));
                    if (fileSuffix.toLowerCase().equals(".glb")||fileSuffix.toLowerCase().equals(".gltf")) {
                        modelList.add(f);
                    }else{
                        imglList.add(f);
                    }
                }
            }

            //check
            if(modelList.size()==0){
                return R.error("model file not found!");
            }

            //save model
            for (File item : modelList) {
                KoMapFile data = new KoMapFile();
                data.setFileId(CommonUtils.getUUID());
                data.setTreeId(treeId);
                data.setFileSuffix(item.getName().substring(item.getName().lastIndexOf(".")));
                data.setCreateTime(new Date());
                data.setCreateUser(createUser);
                data.setFileName(item.getName().substring(0,item.getName().indexOf(".")));
                data.setFileSize((int) item.length());
                data.setIsAdmin(isAdmin);
                if(user!=null){
                    data.setOrganizationId(user.getOrganizationId());
                }
                data.setFileUrl("/" + createUser + "/" + zipUuid +"/" + data.getFileId() + data.getFileSuffix());
                //copy model
                copyFile(item.getPath(),appConfig.getMapFilePath() + data.getFileUrl());

                for (File imgItem : imglList) {
                    String fName = imgItem.getName().substring(0,imgItem.getName().indexOf("."));
                    String fSuffix = imgItem.getName().substring(imgItem.getName().lastIndexOf("."));
                    if(fName.equals(data.getFileName())){
                        if (fSuffix.toLowerCase().equals(".png")||fSuffix.toLowerCase().equals(".jpg")) {
                            String fTempName = CommonUtils.getUUID();
                            data.setImgPath("/" + createUser + "/" + zipUuid + "/" + fTempName + fSuffix);
                            //copy img
                            copyFile(imgItem.getPath(),appConfig.getMapFilePath() + data.getImgPath());
                        }
                    }
                }
                this.save(data);
            }
            return R.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }



    public R uploadImg(MultipartFile file,String createUser) {

        try {
            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folder =  appConfig.getMapFilePath() + "/" + createUser;
            String filePath = folder + "/" + newName  + suffixName;
            String url = "/" + createUser + "/" + newName + suffixName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            File zipFile = new File(filePath);
            file.transferTo(zipFile);

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

    public R getPageUser1(PageDomain pageDomain, KoMapFile entity){

        Page<KoMapFile> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoMapFile> list =this.baseMapper.getPageUser1(
                page,
                entity
        );

        IPage<KoMapFile> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R getPageAdmin1(PageDomain pageDomain, KoMapFile entity){
        if (entity.getKssj() != null && !entity.getKssj().equals("")) {
            entity.setKssj(entity.getKssj() + " 00:00:00");
        }

        if (entity.getJssj() != null && !entity.getJssj().equals("")) {
            entity.setJssj(entity.getJssj() + " 23:59:59");
        }
        Page<KoMapFile> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoMapFile> list =this.baseMapper.getPageAdmin1(
                page,
                entity
        );
        IPage<KoMapFile> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R addCollection(KoMapFileCollection entity){
        entity.setCreateTime(new Date());
        collectionService.save(entity);
        return R.success();
    }

    public R deleteCollection(KoMapFileCollection entity){

        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("file_id",entity.getFileId());
        map1.put("create_user",entity.getCreateUser());
        collectionService.removeByMap(map1);

        return R.success();
    }

    public R getPageCollection(PageDomain pageDomain, KoMapFile entity){
        Page<KoMapFile> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoMapFile> list =this.baseMapper.getPageCollection(
                page,
                entity
        );
        IPage<KoMapFile> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R edit(KoMapFile entity){
        updateById(entity);
        return R.success();
    }

    public R delete(KoMapFile entity){

        removeById(entity.getId());



        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("file_id",entity.getId());
        collectionService.removeByMap(map1);

        return R.success();
    }

    public R get(KoMapFile entity){
        entity = getById(entity.getId());
        return R.success(entity);
    }

    //------------------------------------------------------------------

    @Transactional
    public R uploadZip(MultipartFile file, String treeId, String createUser) {
        try {

            if(createUser==null || createUser.isEmpty()){
                return R.error();
            }

            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));

            if (!suffixName.equalsIgnoreCase(".zip")) {
                return R.error();
            }

            String zipUuid = CommonUtils.getUUID();
            String zipFileNmae = zipUuid+".zip";

            String url = createUser + File.separator ;

            String basePath = appConfig.getMapFilePath() + File.separator + url;
            String zipBase = basePath + zipUuid;
            String zipBasePath =zipBase + File.separator + zipFileNmae;

            File fileDir =new File(zipBasePath);
            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                fileDir .mkdir();
            }

            File zipFile = new File(zipBasePath);

            zipFile.getParentFile().mkdirs();
            if (zipFile.exists()) {
                zipFile.delete();
            }
            file.transferTo(zipFile);

            FSZipUtils.unzip(zipFile, zipBase, false);

            delFile(zipFile);  //************

            List<KoMapFile> fileItem = new ArrayList<>();

            File tempFile = new File(zipBase);
            File[] fs = tempFile.listFiles();


            //check
            for (File f : fs) {
                if (!f.isDirectory()) {
                    String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));

                    if (fileSuffix.equals(".glb")||fileSuffix.equals(".gltf")) {

                    }else{
                        return R.error("the file is not a model file .glb/.gltf");
                    }
                }
            }


            for(File f:fs){
                if(!f.isDirectory()){

                    KoMapFile item = new KoMapFile();
                    //item.setTreeId(treeId);
                    item.setCreateTime(new Date());
                    item.setCreateUser(createUser);
                    item.setFileId(CommonUtils.getUUID());
                    item.setFileSuffix(f.getName().substring(f.getName().lastIndexOf(".")));
                    item.setFileUrl(url);
                    item.setFileName(f.getName().substring(0,f.getName().indexOf(".")));
                    item.setFileSize((int)f.length());
                    item.setFileType(2);  //

                    item.setFileUrl(File.separator + item.getFileUrl()+item.getFileId()+item.getFileSuffix());

                    if(createUser.equals("admin")){
                        item.setFileType(1);
                        item.setIsAdmin("1");
                    }else{
                        item.setIsAdmin("0");
                    }
                    fileItem.add(item);
                    String path = basePath  + File.separator + item.getFileId() + item.getFileSuffix();

                    f.renameTo(new File(path));

                    save(item);
                }
            }


            File deleteFile = new File(zipBase);   //*********
            deleteFile.delete();

            return R.success();

        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error();
        }

    }

    @Transactional
    public R uploadList(MultipartFile[] files, String treeId, String createUser) {
        try{

            if(createUser==null || createUser.isEmpty()){
                return R.error();  //
            }

            if (files == null) {
                return R.error();
            }

            if (files.length <= 0) {
                return R.error();
            }

            for (MultipartFile f : files) {
                if (f == null) {
                    return R.error();
                }
            }


            String url = createUser + File.separator ;//getSavePath(treeId);

            for (MultipartFile file : files) {


                String fileOldNmae = file.getOriginalFilename();

                String suffixName = fileOldNmae.substring(fileOldNmae.lastIndexOf("."));

                String fileNewName = CommonUtils.getUUID();

                String basePath =appConfig.getMapFilePath() + File.separator + url;

                String fileBasePath =basePath + File.separator + fileNewName + suffixName;


                File fileDir =new File(fileBasePath);
                if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                    fileDir .mkdir();
                }


                File f = new File(fileBasePath);
                f.getParentFile().mkdirs();
                file.transferTo(f);


                KoMapFile item = new KoMapFile();
                //item.setTreeId(treeId);
                item.setCreateTime(new Date());
                item.setCreateUser(createUser);
                item.setFileId(fileNewName);
                item.setFileSuffix(f.getName().substring(f.getName().lastIndexOf(".")));
                item.setFileUrl(url);
                item.setFileName(fileOldNmae.substring(0,fileOldNmae.indexOf(".")));
                item.setFileSize((int)f.length());
                item.setFileType(2);

                item.setFileUrl(File.separator + url +  fileNewName + suffixName);

                if(createUser.equals("admin")){
                    item.setFileType(1);
                    item.setIsAdmin("1");
                }else{
                    item.setIsAdmin("0");
                }

                save(item);
            }
            return R.success();
        }catch (Exception ex){
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error();
        }
    }



    public R getPageUser(PageDomain pageDomain, KoMapFileDto entity){

        Page<KoMapFile> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoMapFile> list =this.baseMapper.getPageUser(
                page,
                entity.getFile(),
                entity.getTypes(),
                entity.getLables()
        );

        IPage<KoMapFile> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }
    public R getPageAdmin(PageDomain pageDomain, KoMapFileDto entity){

        Page<KoMapFile> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoMapFile> list =this.baseMapper.getPageAdmin(
                page,
                entity.getFile(),
                entity.getTypes(),
                entity.getLables()
        );

        IPage<KoMapFile> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }


    //region new function



    @Transactional
    public R uploadZip1(MultipartFile file, String treeId, String createUser) {
        try {

            if(createUser==null || createUser.isEmpty()){
                return R.error();
            }

            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));

            if (!suffixName.equalsIgnoreCase(".zip")) {
                return R.error();
            }


            String zipUuid = CommonUtils.getUUID();
            String zipFileNmae = zipUuid+".zip";


            String url = createUser + File.separator ;

            String basePath = appConfig.getMapFilePath() + File.separator + url;
            String zipBase = basePath + zipUuid;
            String zipBasePath =zipBase + File.separator + zipFileNmae;


            File fileDir =new File(zipBasePath);
            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                fileDir .mkdir();
            }


            File zipFile = new File(zipBasePath);

            zipFile.getParentFile().mkdirs();
            if (zipFile.exists()) {
                zipFile.delete();
            }
            file.transferTo(zipFile);

            FSZipUtils.unzip(zipFile, zipBase, false);

            delFile(zipFile);

            List<KoMapFile> fileItem = new ArrayList<>();

            File tempFile = new File(zipBase);
            File[] fs = tempFile.listFiles();


            //check
            for (File f : fs) {
                if (!f.isDirectory()) {
                    String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));

                    if (fileSuffix.equals(".glb")||fileSuffix.equals(".gltf")) {

                    }else{
                        return R.error("the file is not a model file .glb/.gltf");
                    }
                }
            }


            for(File f:fs){
                if(!f.isDirectory()){

                    KoMapFile item = new KoMapFile();
                    //item.setTreeId(treeId);
                    item.setCreateTime(new Date());
                    item.setCreateUser(createUser);
                    item.setFileId(CommonUtils.getUUID());
                    item.setFileSuffix(f.getName().substring(f.getName().lastIndexOf(".")));
                    item.setFileUrl(url);
                    item.setFileName(f.getName().substring(0,f.getName().indexOf(".")));
                    item.setFileSize((int)f.length());
                    item.setFileType(2);  //

                    item.setFileUrl(File.separator + item.getFileUrl()+item.getFileId()+item.getFileSuffix());

                    if(createUser.equals("admin")){
                        item.setFileType(1);
                        item.setIsAdmin("1");
                    }else{
                        item.setIsAdmin("0");
                    }
                    fileItem.add(item);
                    String path = basePath  + File.separator + item.getFileId() + item.getFileSuffix();

                    f.renameTo(new File(path));

                    save(item);
                }
            }

            File deleteFile = new File(zipBase);   //*********
            deleteFile.delete();

            return R.success();

        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error();
        }

    }

    @Transactional
    public R uploadList1(MultipartFile[] files, String treeId, String createUser) {
        try{

            if(createUser==null || createUser.isEmpty()){
                return R.error();
            }

            if (files == null) {
                return R.error();
            }

            if (files.length <= 0) {
                return R.error();
            }

            for (MultipartFile f : files) {
                if (f == null) {
                    return R.error();
                }
            }

            String url = createUser + File.separator ;//getSavePath(treeId);

            for (MultipartFile file : files) {

                //
                String fileOldNmae = file.getOriginalFilename();
                //
                String suffixName = fileOldNmae.substring(fileOldNmae.lastIndexOf("."));
                //
                String fileNewName = CommonUtils.getUUID();
                //
                String basePath =appConfig.getMapFilePath() + File.separator + url;
                //
                String fileBasePath =basePath + File.separator + fileNewName + suffixName;

                /*  */
                File fileDir =new File(fileBasePath);
                if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                    fileDir .mkdir();
                }

                /*  */
                File f = new File(fileBasePath);
                f.getParentFile().mkdirs();
                file.transferTo(f);

                /*  */
                KoMapFile item = new KoMapFile();
                //item.setTreeId(treeId);
                item.setCreateTime(new Date());
                item.setCreateUser(createUser);
                item.setFileId(fileNewName);
                item.setFileSuffix(f.getName().substring(f.getName().lastIndexOf(".")));
                item.setFileUrl(url);
                item.setFileName(fileOldNmae.substring(0,fileOldNmae.indexOf(".")));
                item.setFileSize((int)f.length());
                item.setFileType(2);  //

                item.setFileUrl(File.separator + url +  fileNewName + suffixName);

                if(createUser.equals("admin")){
                    item.setFileType(1);
                    item.setIsAdmin("1");
                }else{
                    item.setIsAdmin("0");
                }
                //
                save(item);
            }
            return R.success();
        }catch (Exception ex){
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error();
        }
    }


    //endregion
















    //-----------------   not use  ----------

    @OperLog(operType = OperType.OTHER, operDesc = "/mapfile/mapfile/getFileBinary")
    public R getFileBinary(KoMapFile entity){

        KoMapFile data = getById(entity.getId());
        if(data==null){
            return R.error();
        }

        String filePath = appConfig.getMapFilePath() + File.separator +data.getFileUrl() +data.getFileId() +data.getFileSuffix();

        File file=new File(filePath);
        if(!file.exists())
        {
            return R.error();
        }

        String res = fileToBinStr(file);
        JSONObject object =new JSONObject();
        object.put("fileId",entity.getId());
        object.put("binary",res);
        return R.success(object);
    }

    @Transactional
    @OperLog(operType = OperType.UPLOAD, operDesc = "/mapfile/mapfile/upload3Dtiles")
    public R upload3Dtiles(MultipartFile file, String createUser,String serviceId){
        try {

//            if(createUser==null || createUser.isEmpty()){
//                return R.error();
//            }
//
//            if(serviceId==null || serviceId.isEmpty()){
//                return R.error();
//            }
//
//            if (file == null) {
//                return R.error();
//            }
//
//            KoLayerService koLayerService = koLayerServiceMapper.selectById(serviceId);
//
//            if(koLayerService == null ){
//                return R.error();
//            }
//
//            //
//            String fileDesc = file.getOriginalFilename();
//            //
//            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));
//            //
//            if (!suffixName.equalsIgnoreCase(".zip")) {
//                return R.error();   //
//            }
//
//
//            String zipUuid = CommonUtils.getUUID();
//            String zipFileNmae = zipUuid+".zip";
//
//
//            String zipBasePath = appConfig.getLayerServiceFilePath() + File.separator + "zipTemp" + File.separator +zipFileNmae;
//
//
//            File fileDir =new File(zipBasePath);
//            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
//                fileDir .mkdir();
//            }
//
//            /*  */
//            File zipFile = new File(zipBasePath);
//            zipFile.getParentFile().mkdirs();
//            if (zipFile.exists()) {
//                zipFile.delete();
//            }
//            file.transferTo(zipFile);
//
//
//            String decompressionPath = appConfig.getLayerServiceFilePath() + File.separator + createUser + File.separator + koLayerService.getServiceName() + "-" + CommonUtils.getUUID();
//
//            FSZipUtils.unzip(zipFile, decompressionPath, false);
//
//            delFile(zipFile);
//
//            String jsonPath = "";
//
//            List<String> pathList = new ArrayList<>();
//            findAllfile(decompressionPath,pathList);
//
//            for (String item:pathList) {
//                if(item.indexOf("tileset.json")>0) {
//                    jsonPath = item;
//                    break;
//                }
//            }
//
//            if(jsonPath.isEmpty()){
//                return R.error();
//            }
//
//
//            koLayerService.setTilesPath(jsonPath);
//            //String serviceUrl = jsonPath.replaceAll(appConfig.getLayerServiceFilePath(),"");
//            String serviceUrl = jsonPath.substring(appConfig.getLayerServiceFilePath().length(),jsonPath.length());
//            koLayerService.setServiceUri(serviceUrl);
//            koLayerService.setUploadUser(createUser);
//            koLayerService.setUploadTime(new Date());
//            koLayerServiceMapper.updateById(koLayerService);

            return R.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error();
        }
    }


    /* --------------------------- private function ---------------------------*/

    /**
     * Get the physical path of all files under the folder (including all subfolders)
     * @param path
     * @return
     */
    private void findAllfile(String path, List<String> pathList) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                //System.out.println("The folder is empty!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("folder:" + file2.getAbsolutePath());
                        findAllfile(file2.getAbsolutePath(), pathList);
                    } else {
                        pathList.add(file2.getAbsolutePath());
                        //System.out.println("file:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            //System.out.println("file is not find!");
        }
    }






    // The tree structure is no longer used
    /** Get file storage path **/
    private String getSavePath(String treeId){
        String path = "";
        List<KoMapFileType> list = new ArrayList<>();//koMapFileTypeMapper.getAllParent(treeId);

        if(list.size()==0){
            return null;
        }

        for (KoMapFileType item:list) {
            path += item.getName() + File.separator;
        }
        return path;
    }

    /* delete file */
    private static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }
    /**
     * Convert file to binary string
     * @param file
     * @return
     */
    private   String fileToBinStr(File file){
        try {

            byte[] fileData = new byte[(int)file.length()];
            FileInputStream in = new FileInputStream(file);
            in.read(fileData);
            in.close();

            String res = conver2HexStr(fileData);
            return res;
            //return new String(bytes,"utf-8");
        }catch (Exception ex){
            return null;
            //throw new RuntimeException("transform file into bin String ex",ex);

        }
    }

    private  String conver2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2) );
        }
        return result.toString();
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

    //region obj/fbx  ->  glb

    @Transactional
    public R uploadObj(MultipartFile file, String treeId, String createUser, String isAdmin) {
        try {

            KoMapFileType entity = typeService.getById(treeId);

            if(entity==null){
                return R.error("type is null!");
            }

            if(isAdmin.equals("1")){
                if(entity.getParentId().equals("-1")){
                    return R.error("root node cannot bind model!");
                }
            }

            //process zip
            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));
            if (!suffixName.equalsIgnoreCase(".zip")) {
                return R.error("the uploaded file is not a zip file！");
            }
            String zipUuid = CommonUtils.getUUID();
            String zipFileNmae = zipUuid+".zip";
            String basePath = appConfig.getMapFilePath() + "/" + createUser +"/";
            String zipBase = basePath + zipUuid;
            String zipBasePath = zipBase + "/" + zipFileNmae;
            //chenck folder
            File fileDir =new File(zipBasePath);
            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                fileDir .mkdir();
            }
            //upload zip
            File zipFile = new File(zipBasePath);
            zipFile.getParentFile().mkdirs();
            if (zipFile.exists()) {
                zipFile.delete();
            }
            file.transferTo(zipFile);
            FSZipUtils.unzip(zipFile, zipBase, false);  //unzip
            //delFile(zipFile);  //delete zip

            List<File> modelList = new ArrayList<>();
            List<File> imglList = new ArrayList<>();

            File tempFile = new File(zipBase);
            File[] fs = tempFile.listFiles();

            for (File f : fs) {
                if (!f.isDirectory()) {
                    String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));
                    if (fileSuffix.toLowerCase().equals(".obj")) {
                        modelList.add(f);
                    }else{
                        imglList.add(f);
                    }
                }
            }

            //check
            if(modelList.size()==0){
                return R.error("obj file not found!");
            }

            //save model
            for (File item : modelList) {
                KoMapFile data = new KoMapFile();
                data.setFileId(CommonUtils.getUUID());
                data.setTreeId(treeId);
                //data.setFileSuffix(item.getName().substring(item.getName().lastIndexOf(".")));
                data.setFileSuffix(".glb");
                data.setCreateTime(new Date());
                data.setCreateUser(createUser);
                data.setFileName(item.getName().substring(0,item.getName().indexOf(".")));
                data.setFileSize((int) item.length());
                data.setIsAdmin(isAdmin);
//                if(user!=null){
////                    data.setOrganizationId(user.getOrganizationId());
////                }

             //   data.setFileUrl("/" + createUser + "/" + zipUuid +"/" + data.getFileId() + data.getFileSuffix());

                data.setFileUrl("/" + createUser + "/" + zipUuid +"/" + data.getFileName() + data.getFileSuffix());
                String outPath = "/" + createUser + "/" + zipUuid;


                //update this
                String convertId = fmeObjToGlb(item.getPath(), appConfig.getMapFilePath() + outPath);
                if (convertId == null || convertId.isEmpty()) {
                    return R.error("convert fail");
                }
                boolean convertResult = false;
                while (true) {
                    JSONObject result = getFmeJobState(convertId);
                    if (result == null) {
                        break;
                    } else if (result.getString("status").equals("FME_FAILURE")) {
                        break;
                    } else if (result.getString("status").equals("SUCCESS")) {
                        convertResult = true;
                        break;
                    } else if (result.getString("status").equals("PULLED")) {
                        //wait
                    } else if (result.getString("status").equals("QUEUED")) {
                        //wait
                    } else {
                        break;
                    }
                    Thread.sleep(1500);
                }
                if (convertResult == false) {
                    return R.error("convert fail");
                }


                //copy model
               // copyFile(item.getPath(),appConfig.getMapFilePath() + data.getFileUrl());

                for (File imgItem : imglList) {
                    String fName = imgItem.getName().substring(0,imgItem.getName().indexOf("."));
                    String fSuffix = imgItem.getName().substring(imgItem.getName().lastIndexOf("."));
                    if(fName.equals(data.getFileName())){
                        if (fSuffix.toLowerCase().equals(".png")||fSuffix.toLowerCase().equals(".jpg")) {
                            String fTempName = CommonUtils.getUUID();
                            data.setImgPath("/" + createUser + "/" + zipUuid + "/" + fTempName + fSuffix);
                            //copy img
                            copyFile(imgItem.getPath(),appConfig.getMapFilePath() + data.getImgPath());
                        }
                    }
                }
                this.save(data);
            }
            return R.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }


    @Transactional
    public R uploadFbx(MultipartFile file, String treeId, String createUser, String isAdmin){
        try {

            KoMapFileType entity = typeService.getById(treeId);

            if(entity==null){
                return R.error("type is null!");
            }

            if(isAdmin.equals("1")){
                if(entity.getParentId().equals("-1")){
                    return R.error("root node cannot bind model!");
                }
            }

            //process zip
            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));
            if (!suffixName.equalsIgnoreCase(".zip")) {
                return R.error("the uploaded file is not a zip file！");
            }
            String zipUuid = CommonUtils.getUUID();
            String zipFileNmae = zipUuid+".zip";
            String basePath = appConfig.getMapFilePath() + "/" + createUser +"/";
            String zipBase = basePath + zipUuid;
            String zipBasePath = zipBase + "/" + zipFileNmae;
            //chenck folder
            File fileDir =new File(zipBasePath);
            if  (!fileDir .exists()  && !fileDir .isDirectory()) {
                fileDir .mkdir();
            }
            //upload zip
            File zipFile = new File(zipBasePath);
            zipFile.getParentFile().mkdirs();
            if (zipFile.exists()) {
                zipFile.delete();
            }
            file.transferTo(zipFile);
            FSZipUtils.unzip(zipFile, zipBase, false);  //unzip
            //delFile(zipFile);  //delete zip

            List<File> modelList = new ArrayList<>();
            List<File> imglList = new ArrayList<>();

            File tempFile = new File(zipBase);
            File[] fs = tempFile.listFiles();

            for (File f : fs) {
                if (!f.isDirectory()) {
                    String fileSuffix = f.getName().substring(f.getName().lastIndexOf("."));
                    if (fileSuffix.toLowerCase().equals(".fbx")) {
                        modelList.add(f);
                    }else{
                        imglList.add(f);
                    }
                }
            }

            //check
            if(modelList.size()==0){
                return R.error("fbx file not found!");
            }

            //save model
            for (File item : modelList) {
                KoMapFile data = new KoMapFile();
                data.setFileId(CommonUtils.getUUID());
                data.setTreeId(treeId);
                //data.setFileSuffix(item.getName().substring(item.getName().lastIndexOf(".")));
                data.setFileSuffix(".glb");
                data.setCreateTime(new Date());
                data.setCreateUser(createUser);
                data.setFileName(item.getName().substring(0,item.getName().indexOf(".")));
                data.setFileSize((int) item.length());
                data.setIsAdmin(isAdmin);
//                if(user!=null){
////                    data.setOrganizationId(user.getOrganizationId());
////                }

                //data.setFileUrl("/" + createUser + "/" + zipUuid +"/" + data.getFileId() +  data.getFileSuffix());
                data.setFileUrl("/" + createUser + "/" + zipUuid +"/" + data.getFileName() + data.getFileSuffix());
                String outPath = "/" + createUser + "/" + zipUuid;

                //update this
                String convertId = fmeFbxToGlb(item.getPath(), appConfig.getMapFilePath() + outPath);
                if (convertId == null || convertId.isEmpty()) {
                    return R.error("convert fail");
                }
                boolean convertResult = false;
                while (true) {
                    JSONObject result = getFmeJobState(convertId);
                    if (result == null) {
                        break;
                    } else if (result.getString("status").equals("FME_FAILURE")) {
                        break;
                    } else if (result.getString("status").equals("SUCCESS")) {
                        convertResult = true;
                        break;
                    } else if (result.getString("status").equals("PULLED")) {
                        //wait
                    } else if (result.getString("status").equals("QUEUED")) {
                        //wait
                    } else {
                        break;
                    }
                    Thread.sleep(1500);
                }
                if (convertResult == false) {
                    return R.error("convert fail");
                }


                //copy model
                // copyFile(item.getPath(),appConfig.getMapFilePath() + data.getFileUrl());

                for (File imgItem : imglList) {
                    String fName = imgItem.getName().substring(0,imgItem.getName().indexOf("."));
                    String fSuffix = imgItem.getName().substring(imgItem.getName().lastIndexOf("."));
                    if(fName.equals(data.getFileName())){
                        if (fSuffix.toLowerCase().equals(".png")||fSuffix.toLowerCase().equals(".jpg")) {
                            String fTempName = CommonUtils.getUUID();
                            data.setImgPath("/" + createUser + "/" + zipUuid + "/" + fTempName + fSuffix);
                            //copy img
                            copyFile(imgItem.getPath(),appConfig.getMapFilePath() + data.getImgPath());
                        }
                    }
                }
                this.save(data);
            }
            return R.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    private JSONObject getFmeJobState(String convertId) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpPost = new HttpGet(appConfig.getFmeConvertUrl()+"/fmerest/v3/transformations/jobs/id/" + convertId);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
//        String jsonString =object.toJSONString();
//        StringEntity body = new StringEntity(jsonString, "UTF-8");
//        httpPost.setEntity(body);

        //header
        httpPost.addHeader("Content-Type", "application/json;charset=utf8");
        //httpPost.addHeader("Authorization", "fmetoken token=52786d92ce4cc93a89a14048764782a2603ddb3b");
        httpPost.addHeader("Authorization", appConfig.getFmeAuthorization());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String resBody = EntityUtils.toString(responseEntity);
                JSONObject result = JSON.parseObject(resBody);
                return result;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
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

    private String fmeObjToGlb(String inPath,String outPath) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(appConfig.getFmeConvertUrl()+"/fmerest/v3/transformations/submit/LX_SERVICE_TEST/obj-glb-20230207.fmw");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //request format
        String temp = "{\"publishedParameters\":[],\"workspacePath\":\"\\\"LX_SERVICE_TEST/obj-glb-20230207/obj-glb-20230207.fmw\\\"\",\"TMDirectives\":{\"rtc\":false,\"ttc\":-1,\"description\":\"\",\"tag\":\"Default\",\"priority\":-1,\"ttl\":-1},\"NMDirectives\":{\"directives\":[{\"name\":\"logFullPath\",\"value\":\"{logHome}/{@logFileName(id)}\"}],\"successTopics\":[],\"failureTopics\":[]}}";
        JSONObject object = JSON.parseObject(temp);

        JSONObject p1 = new JSONObject();     //FEATURE_TYPES
        p1.put("name", "FEATURE_TYPES");
        p1.put("value", "");

        JSONObject p2 = new JSONObject();    //DestDataset_GLTF
        p2.put("name", "DestDataset_GLTF");
        p2.put("value", outPath);

        JSONObject p3 = new JSONObject();    //SourceDataset_OBJ
        p3.put("name", "SourceDataset_OBJ");
        p3.put("value", new String[]{inPath});

        object.getJSONArray("publishedParameters").add(p1);
        object.getJSONArray("publishedParameters").add(p2);
        object.getJSONArray("publishedParameters").add(p3);

        //body
        String jsonString =object.toJSONString();
        StringEntity body = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(body);

        //header
        httpPost.addHeader("Content-Type", "application/json;charset=utf8");
        //httpPost.addHeader("Authorization", "fmetoken token=52786d92ce4cc93a89a14048764782a2603ddb3b");
        httpPost.addHeader("Authorization", appConfig.getFmeAuthorization());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String resBody = EntityUtils.toString(responseEntity);
                JSONObject result = JSON.parseObject(resBody);
                return result.getString("id");
            }else{
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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

    private String fmeFbxToGlb(String inPath,String outPath) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(appConfig.getFmeConvertUrl()+"/fmerest/v3/transformations/submit/LX_SERVICE_TEST/fbx2gltf.fmw");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //request format
        String temp = "{\"publishedParameters\":[],\"workspacePath\":\"\\\"LX_SERVICE_TEST/fbx2gltf/fbx2gltf.fmw\\\"\",\"TMDirectives\":{\"rtc\":false,\"ttc\":-1,\"description\":\"\",\"tag\":\"Default\",\"priority\":-1,\"ttl\":-1},\"NMDirectives\":{\"directives\":[{\"name\":\"logFullPath\",\"value\":\"{logHome}/{@logFileName(id)}\"}],\"successTopics\":[],\"failureTopics\":[]}}";
        JSONObject object = JSON.parseObject(temp);

//        JSONObject p1 = new JSONObject();     //FEATURE_TYPES
//        p1.put("name", "FEATURE_TYPES");
//        p1.put("value", "");

        JSONObject p2 = new JSONObject();    //DestDataset_GLTF
        p2.put("name", "DestDataset_GLTF");
        p2.put("value", outPath);

        JSONObject p3 = new JSONObject();    //SourceDataset_FBX
        p3.put("name", "SourceDataset_FBX");
        p3.put("value", new String[]{inPath});

        //object.getJSONArray("publishedParameters").add(p1);
        object.getJSONArray("publishedParameters").add(p2);
        object.getJSONArray("publishedParameters").add(p3);

        //body
        String jsonString =object.toJSONString();
        StringEntity body = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(body);

        //header
        httpPost.addHeader("Content-Type", "application/json;charset=utf8");
        //httpPost.addHeader("Authorization", "fmetoken token=52786d92ce4cc93a89a14048764782a2603ddb3b");
        httpPost.addHeader("Authorization", appConfig.getFmeAuthorization());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String resBody = EntityUtils.toString(responseEntity);
                JSONObject result = JSON.parseObject(resBody);
                return result.getString("id");
            }else{
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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

    //endregion


}
