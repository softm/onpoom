package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.layer.KoProvinceCatalog;
import com.quickex.mapper.layer.KoMenuCatalogMapper;
import com.quickex.mapper.layer.KoProvinceCatalogMapper;
import com.quickex.service.layer.IKoProvinceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KoProvinceCatalogServiceImpl extends BaseServiceImpl<KoProvinceCatalogMapper, KoProvinceCatalog> implements IKoProvinceCatalogService {

    @Autowired
    private KoMenuCatalogMapper koMenuCatalogMapper;

    public R getPage(PageDomain pageDomain, KoProvinceCatalog koProvince){

        List<String> plist = new ArrayList<>();

        QueryWrapper<KoProvinceCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_code",koProvince.getProvinceCode());
        List<KoProvinceCatalog> glList = this.baseMapper.selectList(queryWrapper);

        if(glList.size()==0){
            return R.success(new Page<>());
        }

        for (int i = 0; i < glList.size(); i++) {
            plist.add(glList.get(i).getCatalogId());
        }

        Page<KoMenuCatalog> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoMenuCatalog> query = new QueryWrapper<>();
        query.in("id", plist);
        IPage<KoMenuCatalog> list = koMenuCatalogMapper.selectPage(page,query);
        return R.success(list);
    }

    public R add(KoProvinceCatalog koProvince){

        KoProvinceCatalog check = getById(koProvince.getProvinceCode());
        if(check!=null){
            return R.error();
        }

        save(koProvince);
        return R.success();
    }

    public R deleteIds(List<String> ids){
        removeByIds(ids);
        return R.success();
    }
}
