package com.quickex.web.mapfile;


import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.Dto.KoMapFileDto;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.mapfile.KoMapFileCollection;
import com.quickex.service.mapfile.IKoMapFileService;
import com.quickex.service.mapfile.IShpFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * <p>
 * Map file information front-end controller
 * </p>
 *
 * @author ffzh
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/mapfile/mapfile")
public class KoMapFileController extends BaseController {

    @Resource
    private IKoMapFileService service;

    @Resource
    private AppConfig config;

    @Resource
    private IShpFileService service1;

    @PostMapping("/geojsonToShp")
    public R geojsonToShp(@RequestBody String body) {
        return service1.geojsonToShp(body);
    }

    /**
     * File upload zip (common before and after. At present, CREATEUSER = admin is considered as an administrator)
     * @param data
     * @return
     */
    @PostMapping("/uploadzip")
    public R uploadZip(@RequestParam(value = "file",required = false) MultipartFile file,
                         @RequestParam(value = "treeId",required = false) String treeId ,
                         @RequestParam(value = "createUser",required = false) String createUser
    ){
        return service.uploadZip(file,treeId,createUser);
    }

    /**
     * File upload list (common before and after. At present, CREATEUSER = admin is considered as an administrator)
     * @param data
     * @return
     */
    @PostMapping("/uploadzlist")
    public R uploadzList(@RequestParam(value = "files",required = false) MultipartFile[] files,
                         @RequestParam(value = "treeId",required = false) String treeId ,
                         @RequestParam(value = "createUser",required = false) String createUser
    ){
        return service.uploadList(files,treeId,createUser);
    }


    /**
     * Layer upload 3dtiles (common before and after. At present, CREATEUSER = admin is considered as an administrator)
     * @param data
     * @return
     */
    @PostMapping("/upload3Dtiles")
    public R upload3Dtiles(@RequestParam(value = "file",required = false) MultipartFile file,
                           @RequestParam(value = "createUser",required = false) String createUser,
                           @RequestParam(value = "serviceId",required = false) String serviceId
    ){
        return service.upload3Dtiles(file,createUser,serviceId);
    }


    /**
     * Query list (paging) front end user
     */
    @PostMapping("/listUser")
    public R listUser(@RequestBody KoMapFileDto entity) {
        return service.getPageUser(startPage(),entity);
    }

    /**
     * Query list (paging) for background management
     */
    @PostMapping("/listAdmin")
    public R listAdmin(@RequestBody KoMapFileDto entity) {
        return service.getPageAdmin(startPage(),entity);
    }


    @PutMapping("/edit")
    public R edit(@RequestBody KoMapFile entity) {
        return service.edit(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoMapFile entity) {
        return service.delete(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoMapFile entity) {
        return service.get(entity);
    }

    //not use
    @PostMapping("/getUrl")
    public R getUrl() {
        JSONObject object =new JSONObject();
//        object.put("url",config.getMapFileUrl());
        object.put("url",null);
        return R.success(object);
    }


    //not use
    @PostMapping("/getFileBinary")
    public R getFileBinary(@RequestBody KoMapFile file) {
        return service.getFileBinary(file);
    }




    //------------------------------   None of the above  --------------------------------------

    @PostMapping("/upload")
    public R upload(MultipartFile image,MultipartFile model, String treeId, String createUser,String isAdmin){
        return service.upload(image,model,treeId,createUser,isAdmin);
    }

    @PostMapping("/upload1")
    public R upload1(MultipartFile zipFile,String treeId, String createUser,String isAdmin){
        return service.upload1(zipFile,treeId,createUser,isAdmin);
    }

    @PostMapping("/uploadImg")
    public R uploadImg(MultipartFile image,String createUser){
        return service.uploadImg(image,createUser);
    }

    @PostMapping("/listUser1")
    public R listUser(@RequestBody KoMapFile entity) {
        return service.getPageUser1(startPage(),entity);
    }


    @PostMapping("/listAdmin1")
    public R listAdmin(@RequestBody KoMapFile entity) {
        if (entity.getTreeId() != null && entity.getTreeId().equals("1")) {
            return service.getPageCollection(startPage(), entity);
        }
        return service.getPageAdmin1(startPage(), entity);
    }

    @PostMapping("/addCollection")
    public R addCollection(@RequestBody KoMapFileCollection entity){
        return service.addCollection(entity);
    }

    @PostMapping("/deleteCollection")
    public R deleteCollection(@RequestBody KoMapFileCollection entity){
        return service.deleteCollection(entity);
    }

    @PostMapping("/getPageCollection")
    public R getPageCollection(@RequestBody  KoMapFile entity){
        return service.getPageCollection(startPage(),entity);
    }

    @PostMapping("/getAdminRootTypeFiles")
    public R getAdminRootTypeFiles(@RequestBody KoMapFile entity){
        return service.getAdminRootTypeFiles(entity);
    }


    @PostMapping("/uploadObj")
    public R uploadObj(MultipartFile zipFile,String treeId, String createUser,String isAdmin){
        return service.uploadObj(zipFile,treeId,createUser,isAdmin);
    }

    @PostMapping("/uploadFbx")
    public R uploadFbx(MultipartFile zipFile,String treeId, String createUser,String isAdmin){
        return service.uploadFbx(zipFile,treeId,createUser,isAdmin);
    }
}

