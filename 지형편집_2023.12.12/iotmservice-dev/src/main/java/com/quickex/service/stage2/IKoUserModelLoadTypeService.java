package com.quickex.service.stage2;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoUserModelLoadType;


public interface IKoUserModelLoadTypeService extends IBaseService<KoUserModelLoadType> {

    R add(JSONObject entity);

    R delete(JSONObject entity);

    R editModel(JSONObject entity);

    R editTypeName(JSONObject entity);

    R page(PageDomain pageDomain,JSONObject entity);

}
