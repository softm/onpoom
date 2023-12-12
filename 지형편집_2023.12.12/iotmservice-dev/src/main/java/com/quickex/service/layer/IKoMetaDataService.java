package com.quickex.service.layer;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoMetaData;

import java.util.List;

public interface IKoMetaDataService extends IBaseService<KoMetaData> {

    R getPage(PageDomain pageDomain, KoMetaData koMetaData);

    R getById(KoMetaData koMetaData);

    R add(KoMetaData koMetaData);

    R edit(KoMetaData koMetaData);

    R deleteIds(List<String> ids);

}
