package com.quickex.service.stage2.impl;

import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoTerrainPolygon;
import com.quickex.mapper.stage2.KoTerrainPolygonMapper;
import com.quickex.service.stage2.IKoTerrainBidService;
import com.quickex.service.stage2.IKoTerrainPolygonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.service.stage2.IKoTerrainTidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KoTerrainPolygonServiceImpl extends BaseServiceImpl<KoTerrainPolygonMapper, KoTerrainPolygon> implements IKoTerrainPolygonService {

    @Autowired
    private IKoTerrainBidService bidService;

}
