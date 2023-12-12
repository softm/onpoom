package com.quickex.web.doc;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.doc.KoUserCulturalHeritageDoc;
import com.quickex.service.doc.IKoUserCulturalHeritageDocService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-user-cultural-heritage-doc")
public class KoUserCulturalHeritageDocController extends BaseController {

    @Resource
    private IKoUserCulturalHeritageDocService service;

    @PostMapping("/add")
    public R add(@RequestBody KoUserCulturalHeritageDoc entity) {
        return service.add(entity);
    }

    @PostMapping("/deletes")
    public R deletes(@RequestBody KoUserCulturalHeritageDoc entity) {
        return service.deletes(entity);
    }

    @PostMapping("/page")
    public R page(@RequestBody KoUserCulturalHeritageDoc entity) {
        return service.page(startPage(),entity);
    }

}

