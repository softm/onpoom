package com.quickex.mapper.systable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.systable.KoTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.systable.RequestDto.TableDataEntity;
import com.quickex.domain.systable.RequestDto.TableEntity;
import com.quickex.domain.systable.RequestDto.TableRowsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;


@Mapper
@Repository
public interface KoTableMapper extends BaseMapper<KoTable> {

    List<LinkedHashMap<String,Object>> sysTablelist(@Param("tableName")String tableName);

    List<LinkedHashMap<String,Object>> userTableList(@Param("tableName")String tableName);

    List<LinkedHashMap<String,Object>> tableFieldList(@Param("tableName")String tableName);

    List<LinkedHashMap<String,Object>> tableIndexList(@Param("tableName")String tableName);

    List<LinkedHashMap<String,Object>> tableForeignkeyList(@Param("tableName")String tableName);

    int updateTableComment(
            @Param("tableName")String tableName,
            @Param("comment")String comment
    );

    int updateTableFieldComment(
            @Param("tableName")String tableName,
            @Param("fieldName")String fieldName,
            @Param("comment")String comment
    );

    List<LinkedHashMap<String,Object>>  selectTableData(
            @Param("page") Page<LinkedHashMap<String,Object>> page,
            @Param("tableName")String tableName
    );

    int insertSql(@Param("sql")String sql);

    int updateSql(@Param("sql")String sql);

    int deleteSql(@Param("sql")String sql);


    // user info
    List<LinkedHashMap<String,Object>> ordinaryUserList(@Param("userName")String userName);

    List<LinkedHashMap<String,Object>> userTableAuthorityList(@Param("userName")String userName);

    LinkedHashMap<String,Object> TableAuthority(@Param("userName")String userName,@Param("tableName")String tableName);



    List<LinkedHashMap<String,Object>> lod25GridList(@Param("administrative")String administrative,@Param("city")String city);

    List<LinkedHashMap<String,Object>> lod25GridFileNameList(@Param("gridid")Integer gridid);


}
