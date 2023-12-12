package com.quickex.web.user;


import com.quickex.core.result.R;
import com.quickex.domain.user.KoServiceCollectionMenu;
import com.quickex.service.user.IKoServiceCollectionMenuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-service-collection-menu")
public class KoServiceCollectionMenuController {

    @Resource
    private IKoServiceCollectionMenuService service;

    @PostMapping("/add")
    public R add(@RequestBody KoServiceCollectionMenu entity){
        return service.add(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoServiceCollectionMenu entity){
        return service.edit(entity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody KoServiceCollectionMenu entity){
        return service.delete(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoServiceCollectionMenu entity){
        return service.list(entity);
    }

    @PostMapping("/checkState")
    public R checkState(@RequestBody KoServiceCollectionMenu entity){
        return service.checkState(entity);
    }

}

