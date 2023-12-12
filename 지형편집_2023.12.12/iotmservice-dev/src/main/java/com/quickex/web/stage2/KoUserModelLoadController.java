package com.quickex.web.stage2;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import com.quickex.domain.stage2.KoUserModelLoad;
import com.quickex.service.stage2.IKoUserModelLoadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-user-model-load")
public class KoUserModelLoadController {

    @Resource
    private IKoUserModelLoadService service;

    @PostMapping("/add")
    public R add(@RequestBody KoUserModelLoad entity){
        return service.add(entity);
    }

    @PostMapping("/deletes")
    public R deletes(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.deletes(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoUserModelLoad entity){
        return service.list(entity);
    }

    @PostMapping("/adds")
    public R adds(@RequestBody String body){
        JSONObject entity = JSON.parseObject(body);
        return service.adds(entity);
    }


}

