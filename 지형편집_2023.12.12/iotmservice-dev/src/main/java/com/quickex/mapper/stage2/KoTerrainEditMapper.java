package com.quickex.mapper.stage2;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.stage2.KoTerrainEdit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface KoTerrainEditMapper extends BaseMapper<KoTerrainEdit> {

    List<LinkedHashMap<String, Object>> page(
            @Param("page") Page<LinkedHashMap<String, Object>> page,
            @Param("entity") KoTerrainEdit entity
    );

}
