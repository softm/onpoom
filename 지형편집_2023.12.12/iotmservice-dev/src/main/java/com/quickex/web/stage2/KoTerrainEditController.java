package com.quickex.web.stage2;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.stage2.KoTerrainEdit;
import com.quickex.service.stage2.IKoTerrainEditService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/ko-terrain-edit")
public class KoTerrainEditController extends BaseController {

    @Resource
    private IKoTerrainEditService service;

    @PostMapping("/add")
    public R add(@RequestBody KoTerrainEdit entity) {
        return service.add(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoTerrainEdit entity){
        return service.delete(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoTerrainEdit entity){
        return service.edit(entity);
    }

    @PostMapping("/page")
    public R page(@RequestBody KoTerrainEdit entity){
        return service.page(startPage(),entity);
    }

    @PostMapping("/get")
    public R delegette(@RequestBody KoTerrainEdit entity){
        return service.get(entity);
    }

}

