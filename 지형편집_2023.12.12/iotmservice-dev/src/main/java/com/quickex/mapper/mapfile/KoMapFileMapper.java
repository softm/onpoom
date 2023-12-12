package com.quickex.mapper.mapfile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.mapfile.KoMapFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.mapfile.KoMapFileType;
import org.apache.ibatis.annotations.Param;

import javax.lang.model.element.NestingKind;
import java.util.LinkedHashMap;
import java.util.List;


public interface KoMapFileMapper extends BaseMapper<KoMapFile> {

    List<KoMapFile> getPageUser(
            @Param("page") Page<KoMapFile> page,
            @Param("file") KoMapFile file,
            @Param("types") List<Integer> types,
            @Param("lables") List<String> lables
            );

    List<KoMapFile> getPageAdmin(
            @Param("page") Page<KoMapFile> page,
            @Param("file") KoMapFile file,
            @Param("types") List<Integer> types,
            @Param("lables") List<String> lables
    );


    List<KoMapFile> getPageUser1(
            @Param("page") Page<KoMapFile> page,
            @Param("file") KoMapFile file
    );

    List<KoMapFile> getPageAdmin1(
            @Param("page") Page<KoMapFile> page,
            @Param("file") KoMapFile file
    );

    List<KoMapFile> getPageCollection(
            @Param("page") Page<KoMapFile> page,
            @Param("file") KoMapFile file
    );


    List<KoMapFile> getAdminRootTypeFiles(@Param("file") KoMapFile file);



}
