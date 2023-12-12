package com.quickex.web.stage2;


import com.quickex.core.controller.BaseController;
import com.quickex.service.stage2.IKoTerrainModelService;
import com.quickex.service.stage2.IKoTextureLibraryTypeService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-terrain-model")
public class KoTerrainModelController  extends BaseController {

    @Resource
    private IKoTerrainModelService service;

}

