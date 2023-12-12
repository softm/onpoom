package com.quickex.mapper.stage2;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.stage2.KoTextureLibraryType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;


public interface KoTextureLibraryTypeMapper extends BaseMapper<KoTextureLibraryType> {


    List<LinkedHashMap<String, Object>> publiclist(
            @Param("page") Page<LinkedHashMap<String, Object>> page,
            @Param("typeId") String typeId,
            @Param("name") String name,
            @Param("sortType") Integer sortType
    );

    List<LinkedHashMap<String, Object>> userlist(
            @Param("page") Page<LinkedHashMap<String, Object>> page,
            @Param("typeId") String typeId,
            @Param("name") String name,
            @Param("sortType") Integer sortType,
            @Param("userAccount") String userAccount
    );

}
