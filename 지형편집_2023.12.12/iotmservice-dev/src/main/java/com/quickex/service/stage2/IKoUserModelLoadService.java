package com.quickex.service.stage2;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoUserModelLoad;

public interface IKoUserModelLoadService extends IBaseService<KoUserModelLoad> {

    R add(KoUserModelLoad entity);

    R deletes(JSONObject entity);

    R list(KoUserModelLoad entity);

    R adds(JSONObject entity);

}
