package com.quickex.web.doc;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.doc.KoAnalysisDoc;
import com.quickex.service.doc.IKoAnalysisDocService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-analysis-doc")
public class KoAnalysisDocController extends BaseController {

    @Resource
    private IKoAnalysisDocService service;

    @PostMapping("/page")
    public R page(@RequestBody KoAnalysisDoc entity){
        return service.page(startPage(),entity);
    }

    @PostMapping("/add")
    public R add(@RequestBody KoAnalysisDoc entity){
        return service.add(entity);
    }

    @PostMapping("/deletes")
    public R deletes(@RequestBody KoAnalysisDoc entity){
        return service.deletes(entity);
    }

}

