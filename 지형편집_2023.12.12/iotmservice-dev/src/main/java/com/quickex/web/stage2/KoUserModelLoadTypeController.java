package com.quickex.web.stage2;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.service.stage2.IKoUserModelLoadTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-user-model-load-type")
public class KoUserModelLoadTypeController   extends BaseController {

    @Resource
    private IKoUserModelLoadTypeService service;

    @PostMapping("/add")
    public R add(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.add(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.delete(entity);
    }

    @PostMapping("/editModel")
    public R editModel(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.editModel(entity);
    }

    @PostMapping("/editTypeName")
    public R editTypeName(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.editTypeName(entity);
    }

    @PostMapping("/page")
    public R page(PageDomain pageDomain, @RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.page(startPage(),entity);
    }

}

