package com.quickex.web.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import com.quickex.service.other.GisInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/other/gisInfo")
public class GisInfoController {

    @Resource
    private GisInfoService service;

//    @PostMapping("/ShpFileToGeojson")
//    public R readShpToGeojson(@RequestBody String request) {
//        JSONObject par = JSON.parseObject(request);
//        return service.pointBuffer(par);
//    }

    @PostMapping("/pointBuffer")
    public R pointBuffer(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.pointBuffer(par);
    }

    @PostMapping("/lineBuffer")
    public R lineBuffer(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.lineBuffer(par);
    }

    @PostMapping("/polygonBuffer")
    public R polygonBuffer(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.polygonBuffer(par);
    }

    @PostMapping("/contain1")
    public R contain1(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.contain1(par);
    }

    @PostMapping("/contain2")
    public R contain2(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.contain2(par);
    }

    @PostMapping("/getArea")
    public R getArea(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.getArea(par);
    }



    @PostMapping("/dissolver")
    public R dissolver(@RequestBody String request) {
        JSONObject par = JSON.parseObject(request);
        return service.dissolver(par);
    }

}
