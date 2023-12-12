package com.quickex.service.layer;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoProvince;


public interface IKoProvinceService extends IBaseService<KoProvince> {

    R getPage(PageDomain pageDomain, KoProvince koProvince);

    R getById(KoProvince koProvince);

    R add(KoProvince koProvince);

    R edit(KoProvince koProvince);

    R deleteIds(KoProvince koProvince);

    R getDefault();

}
