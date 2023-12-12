package com.quickex.web.log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.log.KoMapserviceRecord;
import com.quickex.service.log.IKoMapserviceRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-mapservice-record")
public class KoMapserviceRecordController  extends BaseController {

    @Resource
    private IKoMapserviceRecordService service;

    @PostMapping("/add")
    public R add(@RequestBody KoMapserviceRecord entity){
        return service.add(entity);
    }

    @PostMapping("/page")
    public R page(@RequestBody KoMapserviceRecord entity) {
        return service.page(startPage(),entity);
    }

    @PostMapping("/toExcel")
    public R toExcel(@RequestBody KoMapserviceRecord entity){
        return service.toExcel(entity);
    }



    @PostMapping("/apiPag")
    public R apiPag(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiPag(startPage(),entity);
    }

    @PostMapping("/apiYear")
    public R apiYear(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiYear(startPage(),entity);
    }

    @PostMapping("/apiMonth")
    public R apiMonth(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiMonth(startPage(),entity);
    }

    @PostMapping("/apiDay")
    public R apiDay(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiDay(startPage(),entity);
    }


    @PostMapping("/menuPag")
    public R menuPag(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuPag(startPage(),entity);
    }

    @PostMapping("/menuYear")
    public R menuYear(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuYear(startPage(),entity);
    }

    @PostMapping("/menuMonth")
    public R menuMonth(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuMonth(startPage(),entity);
    }

    @PostMapping("/menuDay")
    public R menuDay(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuDay(startPage(),entity);
    }





    @PostMapping("/apiPagExcel")
    public R apiPagExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiPagExcel(startPage(),entity);
    }

    @PostMapping("/apiYearExcel")
    public R apiYearExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiYearExcel(startPage(),entity);
    }

    @PostMapping("/apiMonthExcel")
    public R apiMonthExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiMonthExcel(startPage(),entity);
    }

    @PostMapping("/apiDayExcel")
    public R apiDayExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.apiDayExcel(startPage(),entity);
    }


    @PostMapping("/menuPagExcel")
    public R menuPagExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuPagExcel(startPage(),entity);
    }

    @PostMapping("/menuYearExcel")
    public R menuYearExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuYearExcel(startPage(),entity);
    }

    @PostMapping("/menuMonthExcel")
    public R menuMonthExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuMonthExcel(startPage(),entity);
    }

    @PostMapping("/menuDayExcel")
    public R menuDayExcel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.menuDayExcel(startPage(),entity);
    }


}

