package com.quickex.web.layer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoCatalogLayerService;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.layer.KoLayerType;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.service.layer.IKoCatalogLayerServiceService;
import com.quickex.service.layer.IKoLayerServiceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/apid/koCatalogLayerService")
public class KoCatalogLayerServiceController {

    @Resource
    private IKoCatalogLayerServiceService koCatalogLayerServicService;


    @PostMapping("/list")
    public R list(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        PageDomain pageDomain = JSON.parseObject(object.getString("pageDomain"), PageDomain.class);
        KoMenuCatalog koMenuCatalog = JSON.parseObject(object.getString("koMenuCatalog"), KoMenuCatalog.class);
        KoLayerService koLayerService = JSON.parseObject(object.getString("koLayerService"), KoLayerService.class);
        return koCatalogLayerServicService.getPage(pageDomain,koMenuCatalog,koLayerService);
    }

    @PostMapping("/add")
    public R add(@RequestBody KoCatalogLayerService koCatalogLayerServic) {
        return koCatalogLayerServicService.add(koCatalogLayerServic);
    }


    @PostMapping("/get")
    public R get(@RequestBody KoCatalogLayerService koCatalogLayerServic) {
        return koCatalogLayerServicService.getById(koCatalogLayerServic);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoCatalogLayerService koCatalogLayerServic) {
        return koCatalogLayerServicService.edit(koCatalogLayerServic);
    }


    @DeleteMapping("/delete")
    public R delete(@RequestBody List<String> ids) {
        return koCatalogLayerServicService.deleteIds(ids);
    }


}

