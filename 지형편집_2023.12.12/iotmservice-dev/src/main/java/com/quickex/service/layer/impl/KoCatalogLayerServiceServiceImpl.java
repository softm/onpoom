package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoCatalogLayerService;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.mapper.layer.KoCatalogLayerServiceMapper;
import com.quickex.service.layer.IKoCatalogLayerServiceService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class KoCatalogLayerServiceServiceImpl extends BaseServiceImpl<KoCatalogLayerServiceMapper, KoCatalogLayerService> implements IKoCatalogLayerServiceService {


    public R getPage(PageDomain pageDomain, KoMenuCatalog koMenuCatalog, KoLayerService koLayerService){

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.getKoLayerServicePage(page,koMenuCatalog,koLayerService);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }

    public R getById(KoCatalogLayerService koCatalogLayerService){
        KoCatalogLayerService data = getById(koCatalogLayerService.getId());
        return R.success(data);
    }

    public R add(KoCatalogLayerService koCatalogLayerService){
        save(koCatalogLayerService);
        return R.success();
    }

    public R edit(KoCatalogLayerService koCatalogLayerService){
        updateById(koCatalogLayerService);
        return R.success();
    }

    public R deleteIds(List<String> ids){
        removeByIds(ids);
        return R.success();
    }
}
