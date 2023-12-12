package com.quickex.web.layer;


import com.quickex.core.result.R;
import com.quickex.domain.layer.KoLayerCatalog;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.user.KoMenu;
import com.quickex.service.layer.IKoLayerCatalogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-layer-catalog")
public class KoLayerCatalogController {

    @Resource
    private IKoLayerCatalogService service;

    @PostMapping("/get")
    public R getInfo(@RequestBody KoLayerCatalog entity)
    {
        return service.get(entity);
    }

    @PostMapping("/add")
    public R add(@RequestBody KoLayerCatalog entity)
    {
        return service.add(entity);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody KoLayerCatalog entity)
    {
        return service.edit(entity);
    }

    @PostMapping("/delete")
    public R remove(@RequestBody KoLayerCatalog entity)
    {
        return service.delete(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoLayerCatalog entity)
    {
        return service.list(entity);
    }

    @PostMapping("/allTree")
    public R allTree(@RequestBody KoLayerCatalog entity)
    {
        return service.allTree(entity);
    }

    @PostMapping("/allAndRoleChecked")
    public R allAndRoleChecked(@RequestBody KoLayerCatalog entity){
        return service.allAndRoleChecked(entity);
    }

    @PostMapping("/allAndUserChecked")
    public R allAndUserChecked(@RequestBody KoLayerCatalog entity){
        return service.allAndUserChecked(entity);
    }

    @PostMapping("/updateRoleCatalog")
    public R updateRoleCatalog(@RequestBody KoLayerCatalog entity){
        return service.updateRoleCatalog(entity);
    }

    @PostMapping("/updateUserCatalog")
    public R updateUserCatalog(@RequestBody KoLayerCatalog entity){
        return service.updateUserCatalog(entity);
    }

    @PostMapping("/userWebTree")
    public R userWebTree(@RequestBody KoLayerCatalog entity){
        return service.userWebTree(entity);
    }

}

