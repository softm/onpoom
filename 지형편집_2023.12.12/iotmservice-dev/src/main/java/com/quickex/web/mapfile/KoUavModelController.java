package com.quickex.web.mapfile;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.KoUavModel;
import com.quickex.service.mapfile.IKoUavModelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-uav-model")
public class KoUavModelController extends BaseController {

    @Resource
    private IKoUavModelService service;

    @PostMapping("/add")
    public R add(@RequestBody KoUavModel entity) {
        return service.add(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoUavModel entity) {
        return service.delete(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoUavModel entity) {
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoUavModel entity) {
        return service.get(entity);
    }

    @PostMapping("/page")
    public R page(@RequestBody KoUavModel entity) {
        return service.page(startPage(),entity);
    }

    @PostMapping("/upload")
    public R upload(@RequestParam(value = "file",required = false) MultipartFile file){
        return service.upload(file);
    }

    @PostMapping("/editPicture")
    public R editPicture(MultipartFile file, Integer width, Integer height) {
        return service.editPicture(file, width, height);
    }

}

