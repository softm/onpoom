package com.quickex.mapper.layer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.quickex.domain.entity.LayerAndTypeSelect;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.layer.KoMenuCatalogUserWebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface KoMenuCatalogMapper extends BaseMapper<KoMenuCatalog> {

    /**
     *  Use of tree menu management end
     * @param id province ID
     * @return
     */
     List<KoMenuCatalog> getTreeByParentId(@Param("id") String id,@Param("name") String name);

    /**
     *  The front end of the query tree structure is not used to show the user
     * @param id pk_id
     * @return
     */
//    List<KoMenuCatalog> getTreeByParentIdForUserWeb(@Param("id") String id);


    /**
     *  Tree menu client use
     * @param id
     * @return
     */
    List<KoMenuCatalogUserWebDto> getTreeByParentIdForUserWebDto(@Param("id") String id);

    //test
    List<KoMenuCatalogUserWebDto> getTreeByParentIdForUserWebDto1(@Param("id") String id);
    List<KoMenuCatalogUserWebDto> getServiceList(@Param("id") String id,@Param("createUser") String createUser,@Param("layerIds") List<String> layerIds);


    List<KoMenuCatalogUserWebDto> getCollectionList(@Param("createUser") String createUser);


    //cs
    //List<KoMenuCatalogUserWebDto> getCsTreeType(@Param("id") String id);
    List<KoMenuCatalogUserWebDto> getCsTreeServiceList(@Param("id") String id);
    List<KoMenuCatalogUserWebDto> getTreeByParentIdForUserWebDto11();


    List<LayerAndTypeSelect> getTreeLayerAndTypeSelectRoot();
    List<LayerAndTypeSelect> getTreeLayerAndTypeSelectChildren(@Param("id") String id);
    List<LayerAndTypeSelect> getTreeLayerAndTypeSelectService(@Param("roleId") String roleId,@Param("treeId") String treeId);

}
