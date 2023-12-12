package com.quickex.web.layer;

import com.quickex.config.log.OperLog;
import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoProvince;
import com.quickex.service.layer.IKoProvinceService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Log4j
@RestController
@RequestMapping("/api/province")
public class KoProvinceController extends BaseController {

    @Resource
    private IKoProvinceService service;

    @PostMapping("/log")
    public R log() {
        log.info("====== info ======");
        log.error("====== error ======");
        return R.success();
    }


    @PostMapping("/list")
    public R list(@RequestBody KoProvince koProvince) {
        return service.getPage(startPage(),koProvince);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoProvince koProvince) {
        return service.add(koProvince);
    }


    @PostMapping("/get")
    public R get(@RequestBody KoProvince koProvince) {
        return service.getById(koProvince);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody KoProvince koProvince) {
        return service.edit(koProvince);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoProvince koProvince) {
        return service.deleteIds(koProvince);
    }

    @PostMapping("/getDefault")
    public R getDefault() {
        return service.getDefault();
    }

}

