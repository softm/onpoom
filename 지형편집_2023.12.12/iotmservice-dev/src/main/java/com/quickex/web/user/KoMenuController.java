package com.quickex.web.user;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoMenu;
import com.quickex.domain.user.KoMenuSort;
import com.quickex.domain.user.KoRole;
import com.quickex.service.user.IKoMenuService;
import com.quickex.service.user.IKoRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-menu")
public class KoMenuController extends BaseController {
    @Resource
    private IKoMenuService service;

    @PostMapping("/add")
    public R add(@RequestBody KoMenu entity){
        return service.add(entity);
    }
    @PostMapping("/delete")
    public R delete(@RequestBody KoMenu entity){
        return service.delete(entity);
    }
    @PostMapping("/edit")
    public R edit(@RequestBody KoMenu entity){
        return service.edit(entity);
    }
    @PostMapping("/get")
    public R get(@RequestBody KoMenu entity){
        return service.get(entity);
    }
    @PostMapping("/allMenu")
    public R allMenu(@RequestBody KoMenu entity){
        return service.allMenu(entity);
    }

    @PostMapping("/allAndRoleChecked")
    public R allAndRoleChecked(@RequestBody KoMenu entity){
        return service.allAndRoleChecked(entity);
    }

    @PostMapping("/allAndUserChecked")
    public R allAndUserChecked(@RequestBody KoMenu entity){
        return service.allAndUserChecked(entity);
    }

    @PostMapping("/updateRoleMenu")
    public R updateRoleMenu(@RequestBody KoMenu entity){
        return service.updateRoleMenu(entity);
    }

    @PostMapping("/updateUserMenu")
    public R updateUserMenu(@RequestBody KoMenu entity){
        return service.updateUserMenu(entity);
    }

    @PostMapping("/updateRoleMenuSort")
    public R updateRoleMenuSort(@RequestBody KoMenuSort entity){
        return service.updateRoleMenuSort(entity);
    }
    @PostMapping("/updateUserMenuSort")
    public R updateUserMenuSort(@RequestBody KoMenuSort entity){
        return service.updateUserMenuSort(entity);
    }


    @PostMapping("/check-route-name")
    public R checkRouteName(@RequestBody KoMenu entity){
        return service.checkRouteName(entity);
    }


}

