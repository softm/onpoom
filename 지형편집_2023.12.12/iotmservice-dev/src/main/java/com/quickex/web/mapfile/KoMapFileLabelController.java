package com.quickex.web.mapfile;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.KoMapFileLabel;
import com.quickex.service.mapfile.IKoMapFileLabelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * not use
 * </p>
 *
 * @author ffzh
 * @since 2021-12-24
 */
@RestController
@RequestMapping("/api/ko-map-file-label")
public class KoMapFileLabelController extends BaseController {

    @Resource
    private IKoMapFileLabelService service;


    @PostMapping("/list")
    public R list(@RequestBody KoMapFileLabel entity) {
        return service.getPage(startPage(),entity);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoMapFileLabel entity) {
        return service.add(entity);
    }


    @PostMapping("/get")
    public R get(@RequestBody KoMapFileLabel entity) {
        return service.getById(entity);
    }



    @DeleteMapping("/delete")
    public R delete(@RequestBody KoMapFileLabel entity) {
        return service.deleteIds(entity);
    }


}

