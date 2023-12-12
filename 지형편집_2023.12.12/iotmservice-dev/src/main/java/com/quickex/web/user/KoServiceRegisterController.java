package com.quickex.web.user;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoServiceRegister;
import com.quickex.service.user.IKoServiceRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-service-register")
public class KoServiceRegisterController  extends BaseController {

    @Resource
    private IKoServiceRegisterService service;

    @PostMapping("/add")
    public R add(@RequestBody KoServiceRegister entity){
        return service.add(entity);
    }
    @PostMapping("/delete")
    public R delete(@RequestBody KoServiceRegister entity){
        return service.delete(entity);
    }
    @PostMapping("/approval")
    public R edit(@RequestBody KoServiceRegister entity){
        return service.edit(entity);
    }
    @PostMapping("/get")
    public R get(@RequestBody KoServiceRegister entity){
        return service.get(entity);
    }
    @PostMapping("/page")
    public R list(@RequestBody KoServiceRegister entity){
        return service.page(startPage(),entity);
    }
    @PostMapping("/update")
    public R update(@RequestBody KoServiceRegister entity){
        return service.update(entity);
    }
}

