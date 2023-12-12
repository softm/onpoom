package com.quickex.service.layer;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoLayerType;

import java.util.List;

public interface IKoLayerTypeService extends IBaseService<KoLayerType> {

    R getPage(PageDomain pageDomain,KoLayerType koLayerType);

    R getById(KoLayerType koLayerType);

    R add(KoLayerType koLayerType);

    R edit(KoLayerType koLayerType);

    R deleteIds(List<String> ids);

}
