package com.quickex.service.stage2;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoTerrainTid;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoTerrainTidService extends IBaseService<KoTerrainTid> {

    String getNumber();

}
