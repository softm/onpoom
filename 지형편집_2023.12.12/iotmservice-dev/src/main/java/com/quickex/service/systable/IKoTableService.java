package com.quickex.service.systable;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.systable.KoTable;
import com.quickex.domain.systable.RequestDto.TableDataEntity;
import com.quickex.domain.systable.RequestDto.TableEntity;
import com.quickex.domain.systable.RequestDto.TableRowsEntity;
import com.quickex.domain.systable.RequestDto.TableUserAuthorityEntity;


/**
 * <p>
 *  Interface definition
 * </p>
 *
 * @author ffzh
 * @since 2022-01-10
 */
public interface IKoTableService extends IBaseService<KoTable> {

    R add(KoTable entity);

    R delete(KoTable entity);

    R edit(KoTable entity);

    R get(KoTable entity);

    R list(KoTable entity);

    //------------------------

     R sysTablelist(String tableName);

     R userTableList(String tableName);

     R tableFieldList(String tableName);

     R tableIndexList(String tableName);

     R tableForeignkeyList(String tableName);

     R updateTableComment(String tableName,String comment);

     R updateTableFieldComment(String tableName,String fieldName,String comment);

     R selectTableData(PageDomain pageDomain, String tableName);

     R createTable(TableEntity entity);
     R deleteTable(String tableName);

     //not use
     R updateTable(TableRowsEntity entity);

    //Field operation
    R addField(TableRowsEntity entity);
    R deleteField(TableRowsEntity entity);
    R editFieldName(TableRowsEntity entity);
    R editFieldType(TableRowsEntity entity);


     R addTableData(TableDataEntity entity);

     R updateTableData(TableDataEntity tableName);

     R deleteTableData(TableDataEntity tableName);

     //----------------  user info

     R ordinaryUserList(String userName);

     R userTableAuthorityList(String userName);

     R TableAuthority(String userName,String tableName);

     R addUser(String userName,String userPwd);

     R userPwd(String userName,String userPwd);

     R deleteUser(String userName);

     R userAuthority(TableUserAuthorityEntity entity);

}
