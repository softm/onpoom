package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoLayerType;
import com.quickex.mapper.layer.KoLayerTypeMapper;
import com.quickex.service.layer.IKoLayerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoLayerTypeServiceImpl extends BaseServiceImpl<KoLayerTypeMapper, KoLayerType> implements IKoLayerTypeService {

    @Autowired
    private KoLayerTypeMapper koLayerTypeMapper;

    public R getPage(PageDomain pageDomain, KoLayerType koLayerType){
        Page<KoLayerType> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoLayerType> query = new QueryWrapper<>();
        if(koLayerType.getTypeName()!=null && !koLayerType.getTypeName().isEmpty()){
            query.like("type_name", koLayerType.getTypeName());
        }
        IPage<KoLayerType> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    public R getById(KoLayerType koLayerType){
        KoLayerType data = getById(koLayerType.getId());
        return R.success(data);
    }

    public R add(KoLayerType koLayerType){
        save(koLayerType);
        return R.success();
    }

    public R edit(KoLayerType koLayerType){
        updateById(koLayerType);
        return R.success();
    }

    public R deleteIds(List<String> ids){
        removeByIds(ids);
        return R.success();
    }
}
