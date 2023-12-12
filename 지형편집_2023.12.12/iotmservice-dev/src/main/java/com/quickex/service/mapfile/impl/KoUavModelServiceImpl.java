package com.quickex.service.mapfile.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.AppConfig;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.mapfile.KoUavModel;
import com.quickex.mapper.mapfile.KoUavModelMapper;
import com.quickex.service.mapfile.IKoUavModelService;
import com.quickex.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class KoUavModelServiceImpl extends BaseServiceImpl<KoUavModelMapper, KoUavModel> implements IKoUavModelService {

    @Autowired
    private AppConfig appConfig;

    public R upload(MultipartFile file) {
        try {
            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;
            String filePath = folder + File.separator + newName  + suffixName;
            String url = "/" + folderName + "/" + newName + suffixName;

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

    public R add(KoUavModel entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R delete(KoUavModel entity){
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoUavModel entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoUavModel entity){
        KoUavModel data = this.getById(entity.getId());
        return R.success(data);
    }

    public R page(PageDomain pageDomain, KoUavModel entity){

        if (entity.getKssj() != null && !entity.getKssj().equals("")) {
            entity.setKssj(entity.getKssj() + " 00:00:00");
        }

        if (entity.getJssj() != null && !entity.getJssj().equals("")) {
            entity.setJssj(entity.getJssj() + " 23:59:59");
        }

        Page<KoUavModel> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoUavModel> list =this.baseMapper.page(
                page,
                entity
        );

        IPage<KoUavModel> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);

//        Page<KoUavModel> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
//        QueryWrapper<KoUavModel> query = new QueryWrapper<>();
//        if(entity.getName()!=null && !entity.getName().isEmpty()){
//            query.like("name", entity.getName());
//        }
//        IPage<KoUavModel> list =this.baseMapper.selectPage(page,query);
//        return R.success(list);
    }


    public R editPicture(MultipartFile file, Integer width, Integer height) {
        try {

            if (file == null) {
                return R.error();
            }

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;
            String filePath = folder + File.separator + newName + suffixName;
            String url = "/" + folderName + "/" + newName + suffixName;

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

//            File srcFile = new File(srcPath);
            Image srcImg = ImageIO.read(zipFile);
            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //use type_ INT_ RGB modified pictures will change color
            buffImg.getGraphics().drawImage(
                    srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                    0, null);

            ImageIO.write(buffImg, "JPEG", new File(filePath));
            return R.success(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }

    }

}
