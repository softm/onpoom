package com.quickex.web.doc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.doc.KoUserCulturalHeritageShp;
import com.quickex.service.doc.IKoUserCulturalHeritageShpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/ko-user-cultural-heritage-shp")
public class KoUserCulturalHeritageShpController extends BaseController {

    @Resource
    private IKoUserCulturalHeritageShpService service;

    @PostMapping("/add")
    public R add(@RequestBody KoUserCulturalHeritageShp entity){
        return service.add(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoUserCulturalHeritageShp entity){
        return service.delete(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoUserCulturalHeritageShp entity){
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoUserCulturalHeritageShp entity){
        return service.get(entity);
    }

    @PostMapping("/page")
    public R page(PageDomain pageDomain,@RequestBody  KoUserCulturalHeritageShp entity){
        return service.page(startPage(),entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoUserCulturalHeritageShp entity){
        return service.list(entity);
    }

    @PostMapping("/downloadBase")
    public R downloadBase(@RequestBody KoUserCulturalHeritageShp entity){
        return service.downloadBase(entity);
    }

    @PostMapping("/downloadChildren")
    public R downloadChildren(@RequestBody KoUserCulturalHeritageShp entity) {
        return service.downloadChildren(entity);
    }


    @PostMapping("/downloadShp")
    public R downloadShp(@RequestBody List<KoUserCulturalHeritageShp> entity){
        return service.downloadShp(entity);
    }



    @PostMapping("/downloadMultiPolygon")
    public R downloadMultiPolygon(@RequestBody String json){
        JSONObject data = JSON.parseObject(json);
        return service.downloadMultiPolygon(data);
    }


}

