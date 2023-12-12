package com.quickex.service.layer.impl;

import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoMapConfig;
import com.quickex.mapper.layer.KoMapConfigMapper;
import com.quickex.service.layer.IKoMapConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoMapConfigServiceImpl extends BaseServiceImpl<KoMapConfigMapper, KoMapConfig> implements IKoMapConfigService {

    public R getById(KoMapConfig koMapConfig){
        KoMapConfig data = getById(koMapConfig.getProvince());
        return R.success(data);
    }

    public R add(KoMapConfig koMapConfig){
        KoMapConfig data = getById(koMapConfig.getProvince());
        if(data!=null){return R.error();}
        save(koMapConfig);
        return R.success();
    }

    public R edit(KoMapConfig koMapConfig){
        updateById(koMapConfig);
        return R.success();
    }

    public R deleteIds(List<String> ids){
        removeByIds(ids);
        return R.success();
    }
}
