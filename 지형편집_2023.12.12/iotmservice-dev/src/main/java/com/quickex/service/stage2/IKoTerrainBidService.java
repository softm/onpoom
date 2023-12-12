package com.quickex.service.stage2;

import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoTerrainBid;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoTerrainBidService extends IBaseService<KoTerrainBid> {

    String getNumber(String taskNumber);

}
