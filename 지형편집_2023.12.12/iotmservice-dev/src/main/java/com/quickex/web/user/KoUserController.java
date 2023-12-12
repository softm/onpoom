package com.quickex.web.user;


import com.alibaba.fastjson.JSON;
import com.quickex.api.verification.PassToken;
import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoRole;
import com.quickex.domain.user.KoUser;
import com.quickex.service.user.IKoRoleService;
import com.quickex.service.user.IKoUserService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Log4j
@RestController
@RequestMapping("/api/ko-user")
public class KoUserController  extends BaseController {

    @Resource
    private IKoUserService service;

    @PostMapping("/add")
    public R add(@RequestBody KoUser entity){
        return service.add(entity);
    }
    @PostMapping("/delete")
    public R delete(@RequestBody KoUser entity){
        return service.delete(entity);
    }
    @PostMapping("/edit")
    public R edit(@RequestBody KoUser entity){
        return service.edit(entity);
    }
    @PostMapping("/get")
    public R get(@RequestBody KoUser entity){
        return service.get(entity);
    }
    @PostMapping("/page")
    public R list(@RequestBody KoUser entity){
        return service.page(startPage(),entity);
    }

    @PassToken
    @PostMapping("/mapLogin")
    public R mapLogin(@RequestBody KoUser entity){
        return service.mapLogin(entity);
    }

    @PassToken
    @PostMapping("/adminLogin")
    public R adminLogin(@RequestBody KoUser entity){
        return service.adminLogin(entity);
    }


    @PostMapping("/editPwd")
    public R editPwd(@RequestBody KoUser entity){
        return service.editPwd(entity);
    }


    @PostMapping("/getMenuByAccount")
    public R getMenuByAccount(@RequestBody KoUser entity){
        return service.getMenuByAccount(entity);
    }


    //Lazy loading
    @PostMapping("/MapGetMenuByAccount")
    public R MapGetMenuByAccount(@RequestBody String json){
        return service.MapGetMenuByAccount(JSON.parseObject(json));
    }


    @PostMapping("/all-route")
    public R getAllRoute(){
        return service.getAllRoute();
    }
}

