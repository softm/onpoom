package com.quickex.web.systable;

import com.quickex.core.controller.BaseController;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.systable.KoTable;
import com.quickex.domain.systable.RequestDto.TableDataEntity;
import com.quickex.domain.systable.RequestDto.TableEntity;
import com.quickex.domain.systable.RequestDto.TableRowsEntity;
import com.quickex.domain.systable.RequestDto.TableUserAuthorityEntity;
import com.quickex.service.systable.IKoTableService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 *   table - Controller
 * </p>
 *
 * @author
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/api/ko-table")
public class KoTableController  extends BaseController {

    @Resource
    private IKoTableService service;

    @PostMapping("/add")
    public R add(@RequestBody KoTable entity) {
        return service.add(entity);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody KoTable entity) {
        return service.delete(entity);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody KoTable entity) {
        return service.edit(entity);
    }

    @PostMapping("/get")
    public R get(@RequestBody KoTable entity) {
        return service.get(entity);
    }

    @PostMapping("/list")
    public R list(@RequestBody KoTable entity) {
        return service.list(entity);
    }


    //-------------------------  None of the above  -------------------

    @PostMapping("/sysTablelist")
    public R sysTablelist(@RequestParam(value = "tableName", required = false) String tableName) {
        return service.sysTablelist(tableName);
    }

    @PostMapping("/userTableList")
    public R userTableList(@RequestParam(value = "tableName", required = false) String tableName) {
        return service.userTableList(tableName);
    }

    @PostMapping("/tableFieldList")
    public R tableFieldList(String tableName) {
        return service.tableFieldList(tableName);
    }

    @PostMapping("/tableIndexList")
    public R tableIndexList(String tableName) {
        return service.tableIndexList(tableName);
    }

    @PostMapping("/tableForeignkeyList")
    public R tableForeignkeyList(String tableName) {
        return service.tableForeignkeyList(tableName);
    }

    @PostMapping("/updateTableComment")
    public R updateTableComment(String tableName,String comment) {
        return service.updateTableComment(tableName,comment);
    }

    @PostMapping("/updateTableFieldComment")
    public R updateTableFieldComment(String tableName,String fieldName,String comment) {
        return service.updateTableFieldComment(tableName,fieldName,comment);
    }

    @PostMapping("/selectTableData")
    public R selectTableData(String tableName) {
        return service.selectTableData(startPage(),tableName);
    }

    @PostMapping("/createTable")
    public R createTable(@RequestBody TableEntity entity) {
        return service.createTable(entity);
    }

    //not use
    @PostMapping("/updateTable")
    public R updateTable(@RequestBody TableRowsEntity entity) {
        return service.updateTable(entity);
    }


    //Field operation
    @PostMapping("/addField")
    public R addField(@RequestBody TableRowsEntity entity) {
        return service.addField(entity);
    }

    @PostMapping("/deleteField")
    public R deleteField(@RequestBody TableRowsEntity entity) {
        return service.deleteField(entity);
    }

    @PostMapping("/editFieldName")
    public R editFieldName(@RequestBody TableRowsEntity entity) {
        return service.editFieldName(entity);
    }

    @PostMapping("/editFieldType")
    public R editFieldType(@RequestBody TableRowsEntity entity) {
        return service.editFieldType(entity);
    }




    @PostMapping("/deleteTable")
    public R deleteTable(String tableName) {
        return service.deleteTable(tableName);
    }

    @PostMapping("/addTableData")
    public R addTableData(@RequestBody TableDataEntity entity) {
        return service.addTableData(entity);
    }

    @PostMapping("/updateTableData")
    public R updateTableData(@RequestBody TableDataEntity entity) {
        return service.updateTableData(entity);
    }

    @PostMapping("/deleteTableData")
    public R deleteTableData(@RequestBody TableDataEntity entity) {
        return service.deleteTableData(entity);
    }


    //----------------  user info

    @PostMapping("/ordinaryUserList")
    public R ordinaryUserList(@RequestParam(value = "userName", required = false) String userName){
        return service.ordinaryUserList(userName);
    }

    @PostMapping("/userTableAuthorityList")
    public R userTableAuthorityList(String userName){
        return service.userTableAuthorityList(userName);
    }

    @PostMapping("/TableAuthority")
    public R TableAuthority(String userName,String tableName){
        return service.TableAuthority(userName,tableName);
    }

    @PostMapping("/addUser")
    public R addUser(String userName,String userPwd){
        return service.addUser(userName,userPwd);
    }

    @PostMapping("/userPwd")
    public R userPwd(String userName,String userPwd){
        return service.userPwd(userName,userPwd);
    }

    @PostMapping("/deleteUser")
    public R deleteUser(String userName){
        return service.deleteUser(userName);
    }

    @PostMapping("/userAuthority")
    public R userAuthority(@RequestBody TableUserAuthorityEntity entity){
        return service.userAuthority(entity);
    }

}

