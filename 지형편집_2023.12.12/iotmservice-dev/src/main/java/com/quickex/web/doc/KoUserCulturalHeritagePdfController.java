package com.quickex.web.doc;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.doc.KoUserCulturalHeritagePdf;
import com.quickex.service.doc.IKoUserCulturalHeritagePdfService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-user-cultural-heritage-pdf")
public class KoUserCulturalHeritagePdfController extends BaseController {

    @Resource
    private IKoUserCulturalHeritagePdfService service;

    @PostMapping("/add")
    public R add(MultipartFile file, String docName, String regionName, String createUser){
        return service.add(file,docName,regionName,createUser);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoUserCulturalHeritagePdf entity){
        return service.delete(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoUserCulturalHeritagePdf entity){
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoUserCulturalHeritagePdf entity){
        return service.get(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoUserCulturalHeritagePdf entity){
        return service.list(entity);
    }

    @PostMapping("/docxToPdf")
    public R docxToPdf(String docxPath,String createUser) {
        return service.docxToPdf(docxPath,createUser);
    }

}

