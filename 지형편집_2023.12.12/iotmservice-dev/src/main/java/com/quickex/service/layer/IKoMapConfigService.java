package com.quickex.service.layer;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoMapConfig;

import java.util.List;

public interface IKoMapConfigService extends IBaseService<KoMapConfig> {

    R getById(KoMapConfig koMapConfig);

    R add(KoMapConfig koMapConfig);

    R edit(KoMapConfig koMapConfig);

    R deleteIds(List<String> ids);

}
