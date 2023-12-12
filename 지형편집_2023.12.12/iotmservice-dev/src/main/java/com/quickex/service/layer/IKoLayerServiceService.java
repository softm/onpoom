package com.quickex.service.layer;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoLayerService;

public interface IKoLayerServiceService  extends IBaseService<KoLayerService> {

    R getPage(PageDomain pageDomain,KoLayerService koLayerService);

    R getById(KoLayerService koLayerService);

    R add(KoLayerService koLayerService);

    R edit(KoLayerService koLayerService);

    R deleteIds(List<String> ids);

    R getMaxSort(String  treeId);

    R getImpactComparisonList(PageDomain pageDomain,String provinceId);

    R updateShpHeight(KoLayerService koLayerService);

    R listNew(PageDomain pageDomain,KoLayerService entity);

    R updateLayerType(JSONObject obj);

}
