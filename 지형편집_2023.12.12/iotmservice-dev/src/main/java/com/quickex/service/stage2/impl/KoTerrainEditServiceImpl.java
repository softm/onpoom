package com.quickex.service.stage2.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoTerrainEdit;
import com.quickex.mapper.stage2.KoTerrainEditMapper;
import com.quickex.service.stage2.IKoTerrainEditService;
import com.quickex.utils.WKTUtil;
import com.vividsolutions.jts.geom.Geometry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class KoTerrainEditServiceImpl extends BaseServiceImpl<KoTerrainEditMapper, KoTerrainEdit> implements IKoTerrainEditService {

    public R add(KoTerrainEdit entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R delete(KoTerrainEdit entity){
        this.removeByIds(entity.getIds());
        return R.success();
    }

    public R edit(KoTerrainEdit entity){
        this.updateById(entity);
        return R.success();
    }

    public R page(PageDomain pageDomain, KoTerrainEdit entity){
        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.page(page,entity);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R get(KoTerrainEdit entity){
        KoTerrainEdit data = this.getById(entity.getId());
        return R.success(data);
    }


}
