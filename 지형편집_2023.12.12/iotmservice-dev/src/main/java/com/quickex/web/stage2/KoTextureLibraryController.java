package com.quickex.web.stage2;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.stage2.KoTextureLibraryData;
import com.quickex.domain.stage2.KoTextureLibraryType;
import com.quickex.service.stage2.IKoTextureLibraryTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/ko-texture-library")
public class KoTextureLibraryController  extends BaseController {

    @Resource
    private IKoTextureLibraryTypeService service;

    @PostMapping("/publicType")
    public R publicType(){
        return service.publicType();
    }

    @PostMapping("/userType")
    public R userType(@RequestBody KoTextureLibraryType entity){
        return service.userType(entity);
    }

    @PostMapping("/publiclist")
    public R publiclist(@RequestBody String body){
        JSONObject par = JSON.parseObject(body);
        return service.publiclist(startPage(),par);
    }

    @PostMapping("/userlist")
    public R userlist(@RequestBody String body){
        JSONObject par = JSON.parseObject(body);
        return service.userlist(startPage(),par);
    }

    @PostMapping("/addType")
    public R addType(@RequestBody KoTextureLibraryType entity){
        return service.addType(entity);
    }

    @PostMapping("/deleteType")
    public R deleteType(@RequestBody KoTextureLibraryType entity){
        return service.deleteType(entity);
    }

    @PostMapping("/editType")
    public R editType(@RequestBody KoTextureLibraryType entity){
        return service.editType(entity);
    }

    @PostMapping("/deleteTexture")
    public R deleteTexture(@RequestBody KoTextureLibraryData entity){
        return service.deleteTexture(entity);
    }

    @PostMapping("/editTexture")
    public R editTexture(@RequestBody KoTextureLibraryData entity){
        return service.editTexture(entity);
    }

    @PostMapping("/addTexture")
    public R addTexture(@RequestBody KoTextureLibraryData entity){
        return service.addTexture(entity);
    }

}

