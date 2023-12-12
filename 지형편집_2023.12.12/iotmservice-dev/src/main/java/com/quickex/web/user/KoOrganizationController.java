package com.quickex.web.user;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoOrganization;
import com.quickex.service.user.IKoOrganizationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-organization")
public class KoOrganizationController extends BaseController {

    @Resource
    private IKoOrganizationService service;

    @PostMapping("/add")
    public R add(@RequestBody KoOrganization entity){
        return service.add(entity);
    }
    @PostMapping("/delete")
    public R delete(@RequestBody KoOrganization entity){
        return service.delete(entity);
    }
    @PostMapping("/edit")
    public R edit(@RequestBody KoOrganization entity){
        return service.edit(entity);
    }
    @PostMapping("/get")
    public R get(@RequestBody KoOrganization entity){
        return service.get(entity);
    }
    @PostMapping("/list")
    public R list(@RequestBody KoOrganization entity){
        return service.list(entity);
    }


    @PostMapping("/tree")
    public R tree(@RequestBody KoOrganization entity){
        return service.tree(entity);
    }

}

