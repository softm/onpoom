package com.quickex.mapper.layer;

import com.quickex.domain.layer.KoLayerCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.layer.KoMenuCatalogUserWebDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoLayerCatalogMapper extends BaseMapper<KoLayerCatalog> {

    List<KoLayerCatalog> allTree(@Param("name") String name);

    List<KoLayerCatalog> allAndRoleChecked(@Param("roleId") String roleId);

    List<KoLayerCatalog> allAndUserChecked(@Param("userId") String userId);


    List<KoMenuCatalogUserWebDto> getTypeList(@Param("pid") String pid,@Param("cataloType") Integer cataloType, @Param("ids") List<String> ids);
    List<KoMenuCatalogUserWebDto> getServiceList(@Param("id") String id, @Param("createUser") String createUser);

}
