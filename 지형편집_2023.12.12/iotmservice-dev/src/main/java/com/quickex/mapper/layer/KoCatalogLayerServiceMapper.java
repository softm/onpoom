package com.quickex.mapper.layer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.layer.KoCatalogLayerService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.layer.KoMenuCatalog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;


@Mapper
@Repository
public interface KoCatalogLayerServiceMapper extends BaseMapper<KoCatalogLayerService> {

    /**
     *  Querying layer services using the directory tree
     * @param page
     * @param koMenuCatalog
     * @param koLayerService
     * @return
     */
    List<LinkedHashMap<String, Object>> getKoLayerServicePage(
            @Param("page") Page<LinkedHashMap<String, Object>> page,
            @Param("koMenuCatalog") KoMenuCatalog koMenuCatalog,
            @Param("koLayerService") KoLayerService koLayerService
    );
}
