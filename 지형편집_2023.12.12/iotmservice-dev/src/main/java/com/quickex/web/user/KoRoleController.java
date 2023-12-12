package com.quickex.web.user;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoLayerAccessLog;
import com.quickex.domain.user.KoRole;
import com.quickex.service.user.IKoOrganizationService;
import com.quickex.service.user.IKoRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-role")
public class KoRoleController  extends BaseController {

    @Resource
    private IKoRoleService service;

    @PostMapping("/add")
    public R add(@RequestBody KoRole entity){
        return service.add(entity);
    }
    @PostMapping("/delete")
    public R delete(@RequestBody KoRole entity){
        return service.delete(entity);
    }
    @PostMapping("/edit")
    public R edit(@RequestBody KoRole entity){
        return service.edit(entity);
    }
    @PostMapping("/get")
    public R get(@RequestBody KoRole entity){
        return service.get(entity);
    }
    @PostMapping("/list")
    public R list(@RequestBody KoRole entity){
        return service.list(entity);
    }
    @PostMapping("/userList")
    public R userList(@RequestBody KoRole entity){
        return service.userList(entity);
    }

    @PostMapping("/updateUserRole")
    public R updateUserRole(@RequestBody KoRole entity){
        return service.updateUserRole(entity);
    }

}

