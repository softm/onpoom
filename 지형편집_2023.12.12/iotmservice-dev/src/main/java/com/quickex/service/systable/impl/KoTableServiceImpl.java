package com.quickex.service.systable.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.systable.KoTable;
import com.quickex.domain.systable.RequestDto.*;
import com.quickex.mapper.systable.KoTableMapper;
import com.quickex.service.systable.IKoTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;


@Service
public class KoTableServiceImpl extends BaseServiceImpl<KoTableMapper, KoTable> implements IKoTableService {

    public R sysTablelist(String tableName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.sysTablelist(tableName);
        return R.success(list);
    }

    public R userTableList(String tableName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.userTableList(tableName);
        return R.success(list);
    }

    public R tableFieldList(String tableName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.tableFieldList(tableName);
        return R.success(list);
    }

    public R tableIndexList(String tableName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.tableIndexList(tableName);
        return R.success(list);
    }

    public R tableForeignkeyList(String tableName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.tableForeignkeyList(tableName);
        return R.success(list);
    }

    public R updateTableComment(String tableName,String comment){
        this.baseMapper.updateTableComment(tableName,"'"+comment+"'");
        return R.success();
    }

    public R updateTableFieldComment(String tableName,String fieldName,String comment){
        this.baseMapper.updateTableFieldComment(tableName,fieldName,"'"+comment+"'");
        return R.success();
    }

    public R selectTableData(PageDomain pageDomain, String tableName){
        Page<LinkedHashMap<String,Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String,Object>> list =this.baseMapper.selectTableData(
                page,
                tableName
        );

        IPage<LinkedHashMap<String,Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    @Transactional
    public R createTable(TableEntity entity){

        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }

        String sql ="";
        sql+="CREATE TABLE \"public\".\""+ entity.getTableName() +"\" (";

        for(TableRowsEntity item : entity.getRows()){

            if(item.getIsNull()==true){
                sql+="\""+item.getName()+"\"  "+item.getType()+"  NOT NULL,";
            }else{
                sql+="\""+item.getName()+"\"  "+item.getType()+" ,";
            }

        }

        for(TableRowsEntity item : entity.getRows()) {
            if (item.getIsPK() == true) {
                sql += " PRIMARY KEY (\"" + item.getName() + "\")";
            }
        }
        sql+= ");\n";

        for(TableRowsEntity item : entity.getRows()){

            sql+=" COMMENT ON COLUMN public."+entity.getTableName()+"."+item.getName()+" IS '"+ item.getComment() +"'; \n";
        }
        if(entity.getComment()!=null){
            sql +=" COMMENT ON TABLE public."+entity.getTableName()+" IS '"+entity.getComment()+"'; \n";
        }

        this.baseMapper.updateSql(sql);
        return R.success();
    }

    //not use
    public R updateTable(TableRowsEntity entity){
        return R.success();
    }

    public R addField(TableRowsEntity entity){
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql ="ALTER table public."+entity.getTableName()+" add "+entity.getName()+" "+entity.getType()+"; \n";

        if(entity.getComment()!=null){
            sql +="comment on COLUMN "+entity.getTableName()+"."+entity.getName()+" IS '"+entity.getComment()+"'";
        }

        this.baseMapper.updateSql(sql);
        return R.success();
    }
    public R deleteField(TableRowsEntity entity){
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql ="alter table public."+entity.getTableName()+" drop column if exists "+entity.getName()+";";
        this.baseMapper.updateSql(sql);
        return R.success();
    }
    public R editFieldName(TableRowsEntity entity){
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql ="ALTER TABLE public."+entity.getTableName()+"  RENAME  "+entity.getName()+"  to "+entity.getNewName()+" ;";
        this.baseMapper.updateSql(sql);
        return R.success();
    }
    public R editFieldType(TableRowsEntity entity){
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql ="alter table public."+entity.getTableName()+" alter column "+entity.getName()+" type "+entity.getType()+";";
        this.baseMapper.updateSql(sql);
        return R.success();
    }

    public R deleteTable(String tableName){
        if(tableName.length()<3){
            return R.error();
        }
        if(tableName.substring(0,3).equals("ko_") || tableName.substring(0,3).equals("com")){
            return R.error();
        }
        String sql = "DROP TABLE public." + tableName;
        this.baseMapper.deleteSql(sql);
        return R.success();
    }

    //table data --------
    public R addTableData(TableDataEntity entity) {
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql = "";
        sql+="insert into " + entity.getTableName()+" (";
        for (TableDataDetailEntity item:entity.getData()) {
            sql+=item.getKey() + ",";
        }
        sql=sql.substring(0,sql.length()-1);
        sql+=") values (";
        for (TableDataDetailEntity item:entity.getData()) {
            if(item.getType()==1){
                sql+= "'" +item.getValue()+ "'" + ",";
            }else{
                sql+=item.getValue() + ",";
            }
        }
        sql=sql.substring(0,sql.length()-1);
        sql+=");";

        int res = this.baseMapper.insertSql(sql);
        if (res > 0) {
            return R.success();
        }
        return R.error();
    }
    public R updateTableData(TableDataEntity entity){
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql = "update "+entity.getTableName()+" set ";

        for (TableDataDetailEntity item:entity.getData()) {
            if(item.getType()==1){
                sql+=item.getKey() + "='"+item.getValue()+"',";
            }else{
                sql+=item.getKey() + "="+item.getValue()+",";
            }
        }
        sql=sql.substring(0,sql.length()-1);
        if(entity.getType()==1){
            sql += " where "+entity.getKey()+"='"+entity.getValue()+"'";
        }else{
            sql += " where "+entity.getKey()+"="+entity.getValue();
        }

        int res = this.baseMapper.updateSql(sql);
        if (res > 0) {
            return R.success();
        }
        return R.error();
    }
    public R deleteTableData(TableDataEntity entity){
        if(entity.getTableName().length()<3){
            return R.error();
        }
        if(entity.getTableName().substring(0,3).equals("ko_") || entity.getTableName().substring(0,3).equals("com")){
            return R.error();
        }
        String sql = "";
        if(entity.getType()==1){
            sql = "delete from "+entity.getTableName()+" where "+entity.getKey()+"='"+entity.getValue()+"'";
        }else{
            sql = "delete from "+entity.getTableName()+" where "+entity.getKey()+"="+entity.getValue();
        }

        int res = this.baseMapper.deleteSql(sql);
        if (res > 0) {
            return R.success();
        }
        return R.error();
    }

    //-------------------- user info

    public R ordinaryUserList(String userName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.ordinaryUserList(userName);
        return R.success(list);
    }

    public R userTableAuthorityList(String tableName){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.userTableAuthorityList(tableName);
        return R.success(list);
    }

    public R TableAuthority(String userName,String tableName){
        LinkedHashMap<String,Object> data = this.baseMapper.TableAuthority(userName,tableName);
        if(data==null){
            data =new LinkedHashMap<>();
            data.put("tableName",tableName);
            data.put("insert","N");
            data.put("delete","N");
            data.put("update","N");
            data.put("select","N");
        }
        return R.success(data);
    }

    public R addUser(String userName, String userPwd) {
        String sql = "CREATE USER " + userName + " WITH ENCRYPTED PASSWORD '" + userPwd + "';";
        this.baseMapper.updateSql(sql);
        return R.success();
    }

    @Transactional
    public R userPwd(String userName, String userPwd) {
        String sql = "ALTER USER " + userName + " WITH PASSWORD '" + userPwd + "';";
        this.baseMapper.updateSql(sql);
        return R.success();
    }

    @Transactional
    public R deleteUser(String userName) {
        String sql = "revoke SELECT ON ALL TABLES IN SCHEMA public from " + userName + "; \n";
        sql += "revoke INSERT ON ALL TABLES IN SCHEMA public from " + userName + "; \n";
        sql += "revoke DELETE ON ALL TABLES IN SCHEMA public from " + userName + "; \n";
        sql += "revoke UPDATE ON ALL TABLES IN SCHEMA public from " + userName + "; \n";
        sql += "revoke SELECT ON ALL TABLES IN SCHEMA public from " + userName + "; \n";
        sql += "drop user " + userName + ";";
        this.baseMapper.updateSql(sql);
        return R.success();
    }

    @Transactional
    public R userAuthority(TableUserAuthorityEntity entity) {
        String sql = "";

        if (entity.getInsert().equals("Y")) {
            sql += "GRANT insert ON  " + entity.getTableName() + " TO " + entity.getUserName() + "; \n";
        } else {
            sql += "revoke insert ON " + entity.getTableName() + " from " + entity.getUserName() + "; \n";
        }

        if (entity.getDelete().equals("Y")) {
            sql += "GRANT delete ON  " + entity.getTableName() + " TO " + entity.getUserName() + "; \n";
        } else {
            sql += "revoke delete ON " + entity.getTableName() + " from " + entity.getUserName() + "; \n";
        }

        if (entity.getUpdate().equals("Y")) {
            sql += "GRANT update ON  " + entity.getTableName() + " TO " + entity.getUserName() + "; \n";
        } else {
            sql += "revoke update ON " + entity.getTableName() + " from " + entity.getUserName() + "; \n";
        }

        if (entity.getSelect().equals("Y")) {
            sql += "GRANT select ON  " + entity.getTableName() + " TO " + entity.getUserName() + "; \n";
        } else {
            sql += "revoke select ON " + entity.getTableName() + " from " + entity.getUserName() + "; \n";
        }

        this.baseMapper.updateSql(sql);
        return R.success();
    }

    //------------ not use --------------------------------
    @OperLog(operType = OperType.ADD, operDesc = "/api/ko-table/add")
    public R add(KoTable entity){
        this.save(entity);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/ko-table/delete")
    public R delete(KoTable entity){
        this.removeById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/ko-table/edit")
    public R edit(KoTable entity){
        this.updateById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table/get")
    public R get(KoTable entity){
        KoTable data = this.getById(entity.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table/list")
    public R list(KoTable entity){
        QueryWrapper<KoTable> query = new QueryWrapper<>();
        List<KoTable> list = this.list(query);
        return R.success(list);
    }

}
