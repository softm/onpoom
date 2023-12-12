package com.quickex.web.layer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoProvince;
import com.quickex.domain.layer.KoProvinceCatalog;
import com.quickex.service.layer.IKoProvinceCatalogService;
import com.quickex.service.layer.IKoProvinceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/koProvinceCatalog")
public class KoProvinceCatalogController {

    @Resource
    private IKoProvinceCatalogService service;

    @PostMapping("/list")
    public R list(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        PageDomain pageDomain = JSON.parseObject(object.getString("pageDomain"),PageDomain.class);
        KoProvinceCatalog koProvince = JSON.parseObject(object.getString("koProvince"),KoProvinceCatalog.class);
        return service.getPage(pageDomain,koProvince);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoProvinceCatalog koProvince) {
        return service.add(koProvince);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody List<String> ids) {
        return service.deleteIds(ids);
    }

    @PostMapping("/addOrUpdata")
    public R addOrUpdata(@RequestBody KoProvinceCatalog koProvince) {
        boolean b = service.saveOrUpdate(koProvince);
        if (b){
            return R.success();
        }else{
            return R.error();
        }
    }

}

