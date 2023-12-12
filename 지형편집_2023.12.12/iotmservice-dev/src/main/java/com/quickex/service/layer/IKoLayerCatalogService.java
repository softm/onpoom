package com.quickex.service.layer;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoLayerCatalog;
import com.quickex.domain.layer.KoMenuCatalog;


public interface IKoLayerCatalogService extends IBaseService<KoLayerCatalog> {

    R add(KoLayerCatalog entity);
    R delete(KoLayerCatalog entity);
    R edit(KoLayerCatalog entity);
    R get(KoLayerCatalog entity);
    R list(KoLayerCatalog entity);
    R allTree(KoLayerCatalog entity);
    R allAndRoleChecked(KoLayerCatalog entity);
    R allAndUserChecked(KoLayerCatalog entity);
    R updateRoleCatalog(KoLayerCatalog entity);
    R updateUserCatalog(KoLayerCatalog entity);

    R userWebTree(KoLayerCatalog koMenuCatalog);

}
