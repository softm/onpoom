package com.quickex.service.stage2.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoTerrainTid;
import com.quickex.mapper.stage2.KoTerrainTidMapper;
import com.quickex.service.stage2.IKoTerrainTidService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class KoTerrainTidServiceImpl extends BaseServiceImpl<KoTerrainTidMapper, KoTerrainTid> implements IKoTerrainTidService {

    public String getNumber(){

        String year = DateTimeFormatter.ofPattern("yy").format(LocalDate.now());;

        QueryWrapper<KoTerrainTid> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("number","T" + year + "-");
        int count = this.count(queryWrapper) + 1;
        String no = String.format("%05d", count);

        KoTerrainTid data = new KoTerrainTid();
        data.setNumber("T" + year + "-" + no);
        this.save(data);

        return data.getNumber();
    }

}
