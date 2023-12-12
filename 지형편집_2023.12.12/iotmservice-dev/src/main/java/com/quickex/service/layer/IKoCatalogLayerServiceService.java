package com.quickex.service.layer;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoCatalogLayerService;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.layer.KoMenuCatalog;

import java.util.List;


public interface IKoCatalogLayerServiceService extends IBaseService<KoCatalogLayerService> {

    R getPage(PageDomain pageDomain, KoMenuCatalog koMenuCatalog, KoLayerService koLayerService);

    R getById(KoCatalogLayerService koCatalogLayerService);

    R add(KoCatalogLayerService koCatalogLayerService);

    R edit(KoCatalogLayerService koCatalogLayerService);

    R deleteIds(List<String> ids);

}
