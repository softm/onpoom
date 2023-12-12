package com.quickex.web.user;


import com.quickex.core.result.R;
import com.quickex.domain.user.KoServiceCollectionType;
import com.quickex.service.user.IKoServiceCollectionTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-service-collection-type")
public class KoServiceCollectionTypeController {

    @Resource
    private IKoServiceCollectionTypeService service;

    @PostMapping("/add")
    public R add(@RequestBody KoServiceCollectionType entity){
        return service.add(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoServiceCollectionType entity){
        return service.edit(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoServiceCollectionType entity){
        return service.delete(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoServiceCollectionType entity){
        return service.list(entity);
    }

}

