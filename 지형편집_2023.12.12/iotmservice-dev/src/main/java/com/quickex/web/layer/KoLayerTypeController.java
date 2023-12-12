package com.quickex.web.layer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoLayerType;
import com.quickex.service.layer.IKoLayerTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/KoLayerType/type")
public class KoLayerTypeController extends BaseController {

    @Resource
    private IKoLayerTypeService koLayerTypeService;


    @PostMapping("/list")
    public R list(@RequestBody KoLayerType koLayerType) {
//        public R list(@RequestBody String json) {
//        JSONObject object = JSON.parseObject(json);
//        PageDomain pageDomain = JSON.parseObject(object.getString("pageDomain"),PageDomain.class);
//        KoLayerType koLayerType = JSON.parseObject(object.getString("koLayerType"),KoLayerType.class);
//        return koLayerTypeService.getPage(pageDomain,koLayerType);
        return koLayerTypeService.getPage(startPage(),koLayerType);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoLayerType koLayerType) {
        return koLayerTypeService.add(koLayerType);
    }


    @PostMapping("/get")
    public R get(@RequestBody KoLayerType koLayerType) {
        return koLayerTypeService.getById(koLayerType);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoLayerType koLayerType) {
        return koLayerTypeService.edit(koLayerType);
    }


    @DeleteMapping("/delete")
    public R delete(@RequestBody List<String> ids) {
        return koLayerTypeService.deleteIds(ids);
    }


}
