package com.quickex.web.layer;


import com.quickex.core.result.R;
import com.quickex.domain.layer.KoMapConfig;
import com.quickex.domain.layer.KoProvince;
import com.quickex.service.layer.IKoMapConfigService;
import com.quickex.service.layer.IKoProvinceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/koMapConfig")
public class KoMapConfigController {

    @Resource
    private IKoMapConfigService service;


    @PostMapping("/add")
    public R add(@RequestBody KoMapConfig koMapConfig) {
        return service.add(koMapConfig);
    }


    @PostMapping("/get")
    public R get(@RequestBody KoMapConfig koMapConfig) {
        return service.getById(koMapConfig);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoMapConfig koMapConfig) {
        return service.edit(koMapConfig);
    }


    @DeleteMapping("/delete")
    public R delete(@RequestBody List<String> ids) {
        return service.deleteIds(ids);
    }


}

