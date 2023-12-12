package com.quickex.mapper.layer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.layer.KoLayerService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface KoLayerServiceMapper extends BaseMapper<KoLayerService> {


    Integer getMaxSort(String treeId);

    /* All layer services that need to affect the comparison under the province */
    List<KoLayerService> getImpactComparisonList(@Param("page") Page<KoLayerService> page, @Param("provinceId") String provinceId);


    List<KoLayerService> listNew(@Param("page") Page<KoLayerService> page,@Param("entity") KoLayerService entity);
}
