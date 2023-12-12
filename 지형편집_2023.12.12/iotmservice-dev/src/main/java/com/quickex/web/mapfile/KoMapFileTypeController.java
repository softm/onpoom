package com.quickex.web.mapfile;

import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.KoMapFileType;
import com.quickex.service.mapfile.IKoMapFileTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/mapfile/mapfiletype")
public class KoMapFileTypeController  extends BaseController {

    @Resource
    private IKoMapFileTypeService service;

    @PostMapping("/add")
    public R add(@RequestBody KoMapFileType entity) {
        return service.add(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoMapFileType entity){
        return service.delete(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoMapFileType entity){
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoMapFileType entity){
        return service.get(entity);
    }

    @PostMapping("/tree")
    public R tree(@RequestBody KoMapFileType entity){
        return service.tree(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoMapFileType entity){
        return service.list(entity);
    }

}

