package com.quickex.web.layer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.layer.KoMetaData;
import com.quickex.service.layer.IKoLayerServiceService;
import com.quickex.service.layer.IKoMetaDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/koMetaData")
public class KoMetaDataController extends BaseController {

    @Resource
    private IKoMetaDataService koMetaDataService;


    @PostMapping("/list")
    public R list(@RequestBody KoMetaData koMetaData) {
        return koMetaDataService.getPage(startPage(),koMetaData);
//    public R list(@RequestBody String json) {
//        JSONObject object = JSON.parseObject(json);
//        PageDomain pageDomain = JSON.parseObject(object.getString("pageDomain"), PageDomain.class);
//        KoMetaData koMetaData = JSON.parseObject(object.getString("koMetaData"), KoMetaData.class);
//        return koMetaDataService.getPage(pageDomain,koMetaData);
    }


    @PostMapping(value = "/get")
    public R getInfo(@RequestBody KoMetaData koMetaData) {
        return koMetaDataService.getById(koMetaData);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoMetaData koMetaData) {
        return koMetaDataService.add(koMetaData);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoMetaData koMetaData) {
        return koMetaDataService.edit(koMetaData);
    }


    @DeleteMapping("/delete")
    public R remove(@RequestBody List<String> ids) {
        return koMetaDataService.deleteIds(ids);
    }

}

