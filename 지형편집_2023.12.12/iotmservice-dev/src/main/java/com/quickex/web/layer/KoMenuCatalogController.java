package com.quickex.web.layer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoLayerCatalog;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.layer.KoUserLayerCollection;
import com.quickex.domain.layer.KoUserLayerType;
import com.quickex.service.layer.IKoLayerCatalogService;
import com.quickex.service.layer.IKoMenuCatalogService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Log4j
@RestController
@RequestMapping("/koMenuCatalog/MenuCatalog")
public class KoMenuCatalogController extends BaseController
{
    @Resource
    private IKoMenuCatalogService koMenuCatalogService;

    @Resource
    private IKoLayerCatalogService layerCatalogService;


    @PostMapping("/list")
    public R list(@RequestBody KoMenuCatalog koMenuCatalog){
//    public R list(@RequestBody String json){
//        JSONObject object = JSON.parseObject(json);
//        PageDomain pageDomain = JSON.parseObject(object.getString("pageDomain"),PageDomain.class);
//        KoMenuCatalog koMenuCatalog = JSON.parseObject(object.getString("koMenuCatalog"),KoMenuCatalog.class);
//        return koMenuCatalogService.getPage(pageDomain,koMenuCatalog);
        return koMenuCatalogService.getPage(startPage(),koMenuCatalog);
    }


    @PostMapping("/treeList")
    public R treeList(@RequestBody KoMenuCatalog koMenuCatalog){
        return koMenuCatalogService.treeList(koMenuCatalog);
    }


    @PostMapping("/get")
    public R getInfo(@RequestBody KoMenuCatalog koMenuCatalog)
    {
        return koMenuCatalogService.getById(koMenuCatalog);
    }


    @PostMapping("/add")
    public R add(@RequestBody KoMenuCatalog koMenuCatalog)
    {
        return koMenuCatalogService.add(koMenuCatalog);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoMenuCatalog koMenuCatalog)
    {
        return koMenuCatalogService.edit(koMenuCatalog);
    }


	@DeleteMapping("/delete")
    public R remove(@RequestBody List<String> ids)
    {
        return koMenuCatalogService.deleteIds(ids);
    }

    @PostMapping("/userWebTree")
    //public R userWebTree(@RequestBody KoLayerCatalog koMenuCatalog){  //new
        public R userWebTree(@RequestBody KoMenuCatalog koMenuCatalog){  //old

       // return layerCatalogService.userWebTree(koMenuCatalog);  //new       uses  authority
        return koMenuCatalogService.userWebTree1(koMenuCatalog);  //old       not   authority


        //return koMenuCatalogService.userWebTree(koMenuCatalog);  //no
    }

    //test
    @PostMapping("/userWebTree1")
    public R userWebTree1(@RequestBody KoMenuCatalog koMenuCatalog){
        return koMenuCatalogService.userWebTree1(koMenuCatalog);
    }

    @PostMapping("/treeRootList")
    public R treeRootList(){
        return koMenuCatalogService.treeRootList();
    }





    @PostMapping("/addCollection")
    public R addCollection(@RequestBody KoUserLayerCollection entity){
        return koMenuCatalogService.addCollection(entity);
    }

    @PostMapping("/deleteCollection")
    public R deleteCollection(@RequestBody KoUserLayerCollection entity){
        return koMenuCatalogService.deleteCollection(entity);
    }

    @PostMapping("/listCollection")
    public R listCollection(@RequestBody KoUserLayerCollection entity){
        return koMenuCatalogService.listCollection(entity);
    }

    @PostMapping("/addUserType")
    public R addUserType(@RequestBody KoUserLayerType entity){
        return koMenuCatalogService.addUserType(entity);
    }

    @PostMapping("/deleteUserType")
    public R deleteUserType(@RequestBody KoUserLayerType entity){
        return koMenuCatalogService.deleteUserType(entity);
    }

    @PostMapping("/deleteUserType1")
    public R deleteUserType1(@RequestBody KoUserLayerType entity){
        return koMenuCatalogService.deleteUserType1(entity);
    }


    @PostMapping("/rNameUserType")
    public R rNameUserType(@RequestBody KoUserLayerType entity){
        return koMenuCatalogService.rNameUserType(entity);
    }

    @PostMapping("/listUserType")
    public R listUserType(@RequestBody KoUserLayerType entity){
        return koMenuCatalogService.listUserType(entity);
    }




    @PostMapping("/userCsTree")
    public R userCsTree(@RequestBody KoMenuCatalog koMenuCatalog){
        return koMenuCatalogService.userCsTree(koMenuCatalog);
        //return koMenuCatalogService.userWebTree(koMenuCatalog);
    }


    @PostMapping("/userWebTreeType")
    public R userWebTreeType(@RequestBody KoMenuCatalog koMenuCatalog){

        return koMenuCatalogService.userWebTreeType(koMenuCatalog);
    }

    @PostMapping("/userWebTreeLayer")
    public R userWebTreeLayer(@RequestBody KoMenuCatalog koMenuCatalog){
        return koMenuCatalogService.userWebTreeLayer(koMenuCatalog);
    }

    @PostMapping("/allAndRoleCheckedLayer")
    public R allAndRoleCheckedLayer(@RequestBody String body){
        JSONObject par = JSON.parseObject(body);
        return koMenuCatalogService.allAndRoleCheckedLayer(par);
    }

    @PostMapping("/updateRoleLayer")
    public R updateRoleLayer(@RequestBody String body){
        JSONObject par = JSON.parseObject(body);
        return koMenuCatalogService.updateRoleLayer(par);
    }

}
