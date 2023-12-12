package com.quickex.web.stage2;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.service.stage2.IKoTerrainTaskService;
import com.quickex.service.stage2.IKoTextureLibraryTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-terrain-task")
public class KoTerrainTaskController  extends BaseController {

    @Resource
    private IKoTerrainTaskService service;

    @PostMapping("/taskList")
    public R taskList(PageDomain pageDomain, @RequestBody String body){
        return service.taskList(pageDomain, JSON.parseObject(body));
    }

    @PostMapping("/addTask")
    public R addTask(@RequestBody String body){
        return service.addTask(JSON.parseObject(body));
    }

    @PostMapping("/deleteTask")
    public R deleteTask(@RequestBody String body){
        return service.deleteTask(JSON.parseObject(body));
    }

    @PostMapping("/terrainList")
    public R terrainList(PageDomain pageDomain,@RequestBody String body){
        return service.terrainList(pageDomain,JSON.parseObject(body));
    }

    @PostMapping("/addTerrain")
    public R addTerrain(@RequestBody String body){
        return service.addTerrain(JSON.parseObject(body));
    }

    @PostMapping("/editTerrain")
    public R editTerrain(@RequestBody String body){
        return service.editTerrain(JSON.parseObject(body));
    }

    @PostMapping("/deleteTerrain")
    public R deleteTerrain(@RequestBody String body){
        return service.deleteTerrain(JSON.parseObject(body));
    }

    @PostMapping("/terrainPoints")
    public R terrainPoints(@RequestBody String body){
        return service.terrainPoints(JSON.parseObject(body));
    }

    @PostMapping("/terrainModels")
    public R terrainModels(@RequestBody String body){
        return service.terrainModels(JSON.parseObject(body));
    }

    @PostMapping("/downloadTaskGeojson")
    public R downloadTaskGeojson(@RequestBody String body){
        return service.downloadTaskGeojson(JSON.parseObject(body));
    }

}

