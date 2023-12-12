package com.quickex.service.doc.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.doc.KoUserCulturalHeritagePdf;
import com.quickex.mapper.doc.KoUserCulturalHeritagePdfMapper;
import com.quickex.service.doc.IKoUserCulturalHeritagePdfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;


@Service
public class KoUserCulturalHeritagePdfServiceImpl extends BaseServiceImpl<KoUserCulturalHeritagePdfMapper, KoUserCulturalHeritagePdf> implements IKoUserCulturalHeritagePdfService {

    @Autowired
    private AppConfig appConfig;

    //
    public R docxToPdf(String docxPath, String createUser) {

        try {
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
            String folder = appConfig.getUavModelPath() + "/" + createUser + "/" + folderName;
            String filePath = folder + "/" + newName + ".pdf";
            String url = "/" + createUser + "/" + folderName + "/" + newName + ".pdf";

            File folderFile = new File(folder);
            if (!folderFile.exists() && !folderFile.isDirectory()) {
                folderFile.mkdirs();
            }

            File docxFile = new File(appConfig.getUavModelPath() + docxPath);
            FileInputStream docxInputStream = new FileInputStream(docxFile);

            FileOutputStream pdfOutputStream = new FileOutputStream(filePath);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(pdfOutputStream).as(DocumentType.PDF).execute();
            pdfOutputStream.close();
            docxInputStream.close();

            converter.kill();

            JSONObject result = new JSONObject();
            result.put("url", url);
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R add(MultipartFile file, String docName, String regionName, String createUser){

        try {

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String folder = appConfig.getUavModelPath()  + "/" + createUser + "/" +folderName;
            String filePath = folder + "/" + newName + suffixName;
            String url = "/" + createUser + "/" + folderName  + "/" + newName + suffixName;

            File fileDir = new File(filePath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File zipFile = new File(filePath);
            file.transferTo(zipFile);

            KoUserCulturalHeritagePdf data = new KoUserCulturalHeritagePdf();
            data.setCreateTime(new Date());
            data.setDocName(docName);
            data.setCreateUser(createUser);
            data.setRegionName(regionName);
            data.setDocPath(url);
            this.save(data);
            return R.success();

        } catch (Exception ex) {
            return R.error();
        }
    }

    public R delete(KoUserCulturalHeritagePdf entity){
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoUserCulturalHeritagePdf entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoUserCulturalHeritagePdf entity){
        KoUserCulturalHeritagePdf data = this.getById(entity.getId());
        return R.success(data);
    }

    public R list(KoUserCulturalHeritagePdf entity){
        QueryWrapper<KoUserCulturalHeritagePdf> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("region_name",entity.getRegionName());
        queryWrapper.eq("create_user",entity.getCreateUser());
        List<KoUserCulturalHeritagePdf> list = this.list(queryWrapper);
        return R.success(list);
    }



}
