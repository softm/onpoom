package com.quickex.service.stage2.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoTerrainBid;
import com.quickex.domain.stage2.KoTerrainTid;
import com.quickex.mapper.stage2.KoTerrainBidMapper;
import com.quickex.service.stage2.IKoTerrainBidService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class KoTerrainBidServiceImpl extends BaseServiceImpl<KoTerrainBidMapper, KoTerrainBid> implements IKoTerrainBidService {

    public String getNumber(String taskNumber){

        QueryWrapper<KoTerrainBid> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("number",taskNumber + "-");
        int count = this.count(queryWrapper) + 1;
        String no = String.format("%05d", count);

        KoTerrainBid data = new KoTerrainBid();
        data.setNumber(taskNumber + "-B" + no);
        this.save(data);

        return data.getNumber();
    }

}
