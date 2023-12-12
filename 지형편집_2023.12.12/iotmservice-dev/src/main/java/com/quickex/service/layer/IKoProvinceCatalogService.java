package com.quickex.service.layer;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoProvinceCatalog;

import java.util.List;

public interface IKoProvinceCatalogService extends IBaseService<KoProvinceCatalog> {

    R getPage(PageDomain pageDomain, KoProvinceCatalog koProvince);

    R add(KoProvinceCatalog koProvince);

    R deleteIds(List<String> ids);
}
