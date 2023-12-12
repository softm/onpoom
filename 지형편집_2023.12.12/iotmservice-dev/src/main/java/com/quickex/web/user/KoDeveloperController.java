package com.quickex.web.user;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoDeveloper;
import com.quickex.domain.user.KoUser;
import com.quickex.service.user.IKoDeveloperService;
import com.quickex.service.user.IKoUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-developer")
public class KoDeveloperController extends BaseController {
    @Resource
    private IKoDeveloperService service;

    @PostMapping("/add")
    public R add(@RequestBody KoDeveloper entity){
        return service.add(entity);
    }
    @PostMapping("/delete")
    public R delete(@RequestBody KoDeveloper entity){
        return service.delete(entity);
    }
    @PostMapping("/edit")
    public R edit(@RequestBody KoDeveloper entity){
        return service.edit(entity);
    }
    @PostMapping("/get")
    public R get(@RequestBody KoDeveloper entity){
        return service.get(entity);
    }
    @PostMapping("/page")
    public R list(@RequestBody KoDeveloper entity){
        return service.page(startPage(),entity);
    }
}

