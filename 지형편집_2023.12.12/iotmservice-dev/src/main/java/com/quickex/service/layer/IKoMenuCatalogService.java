package com.quickex.service.layer;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.layer.KoUserLayerCollection;
import com.quickex.domain.layer.KoUserLayerType;

import java.util.List;


public interface IKoMenuCatalogService extends IService<KoMenuCatalog> {

    R treeList(KoMenuCatalog koMenuCatalog);

    R userWebTree(KoMenuCatalog koMenuCatalog);

    R userWebTree1(KoMenuCatalog koMenuCatalog);

    R getPage(PageDomain pageDomain,KoMenuCatalog koMenuCatalog);

    R getById(KoMenuCatalog koMenuCatalog);

    R add(KoMenuCatalog koMenuCatalog);

    R edit(KoMenuCatalog koMenuCatalog);

    R deleteIds(List<String> ids);

    R treeRootList();


    R addCollection(KoUserLayerCollection entity);
    R deleteCollection(KoUserLayerCollection entity);
    R listCollection(KoUserLayerCollection entity);

    R addUserType(KoUserLayerType entity);
    R deleteUserType(KoUserLayerType entity);
    R deleteUserType1(KoUserLayerType entity);
    R rNameUserType(KoUserLayerType entity);
    R listUserType(KoUserLayerType entity);


    R userCsTree(KoMenuCatalog koMenuCatalog);

    R userWebTreeType(KoMenuCatalog entity);
    R userWebTreeLayer(KoMenuCatalog entity);


    R allAndRoleCheckedLayer(JSONObject par);
    R updateRoleLayer(JSONObject par);
}
