package com.quickex.web.mapfile;

import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.service.mapfile.IShpFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/api/ko-shpFile")
//public class ShpFileController{
//
//    @Resource
//    private IShpFileService service;
//
//    @PostMapping("/pointToGeojsonList")
//    public R pointToGeojsonList(@RequestBody String body) {
//        return service.pointToGeojsonList(body);
//    }
//
//}
