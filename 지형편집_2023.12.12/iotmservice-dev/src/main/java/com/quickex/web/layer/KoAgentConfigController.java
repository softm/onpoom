package com.quickex.web.layer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoAgentConfig;
import com.quickex.service.layer.IKoAgentConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/koAgentConfig")
public class KoAgentConfigController extends BaseController {

    @Resource
    private IKoAgentConfigService service;


    @PostMapping("/list")
    public R list(@RequestBody KoAgentConfig koAgentConfig) {
        return service.getPage(startPage(),koAgentConfig);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoAgentConfig koAgentConfig) {
        return service.add(koAgentConfig);
    }


    @PostMapping("/get")
    public R get(@RequestBody KoAgentConfig koAgentConfig) {
        return service.getById(koAgentConfig);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoAgentConfig koAgentConfig) {
        return service.edit(koAgentConfig);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody List<String> ids) {
        return service.deleteIds(ids);
    }

}

