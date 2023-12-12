package com.quickex.web.layer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.service.layer.IKoLayerServiceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;



@RestController
@RequestMapping("/koLayerService/layer")
public class KoLayerServiceController extends BaseController {
    @Resource
    private IKoLayerServiceService koLayerServiceService;


    @PostMapping("/list")
    public R list(@RequestBody KoLayerService koLayerService) {
//    public R list(@RequestBody String json) {
//        JSONObject object = JSON.parseObject(json);
//        PageDomain pageDomain = JSON.parseObject(object.getString("pageDomain"), PageDomain.class);
//        KoLayerService koLayerService = JSON.parseObject(object.getString("koLayerService"), KoLayerService.class);
//        return koLayerServiceService.getPage(pageDomain,koLayerService);
        return koLayerServiceService.getPage(startPage(),koLayerService);
    }


    @PostMapping(value = "/get")
    public R getInfo(@RequestBody KoLayerService koLayerService) {
        return koLayerServiceService.getById(koLayerService);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoLayerService koLayerService) {
        return koLayerServiceService.add(koLayerService);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoLayerService koLayerService) {
        return koLayerServiceService.edit(koLayerService);
    }


    @DeleteMapping("/delete")
    public R remove(@RequestBody List<String> ids) {
        return koLayerServiceService.deleteIds(ids);
    }


    @PostMapping("/sort/{treeId}")
    public R sort(@PathVariable String treeId) {
        return koLayerServiceService.getMaxSort(treeId);
    }

    @PostMapping("/impactComparisonList")
    public R impactComparisonList(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        String provinceId = object.getString("provinceId");
        return koLayerServiceService.getImpactComparisonList(startPage(),provinceId);
    }


    @PostMapping("/updateShpHeight")
    public R impactComparisonList(@RequestBody KoLayerService koLayerService) {
        return koLayerServiceService.updateShpHeight(koLayerService);
    }

    @PostMapping("/listNew")
    public R listNew(@RequestBody KoLayerService koLayerService){
        return koLayerServiceService.listNew(startPage(),koLayerService);
    }


    @PostMapping("/updateLayerType")
    public R updateLayerType(@RequestBody String body) {
        return koLayerServiceService.updateLayerType(JSON.parseObject(body));
    }
}
