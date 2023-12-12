package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoMetaData;
import com.quickex.domain.layer.KoProvince;
import com.quickex.mapper.layer.KoMetaDataMapper;
import com.quickex.service.layer.IKoMetaDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoMetaDataServiceImpl extends BaseServiceImpl<KoMetaDataMapper, KoMetaData> implements IKoMetaDataService {

    @OperLog(operType = OperType.SELECT, operDesc = "/api/koMetaData/list")
    public R getPage(PageDomain pageDomain, KoMetaData koMetaData){
        Page<KoMetaData> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoMetaData> query = new QueryWrapper<>();

        query.eq("layer_service_id",koMetaData.getLayerServiceId());

        if(koMetaData.getPublisher()!=null && !koMetaData.getPublisher().isEmpty()){
            query.like("publisher", koMetaData.getPublisher());
        }
        if(koMetaData.getComment()!=null && !koMetaData.getComment().isEmpty()){
            query.like("comment", koMetaData.getComment());
        }

        IPage<KoMetaData> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/koMetaData/get")
    public R getById(KoMetaData koMetaData){
        KoMetaData data = getById(koMetaData.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.ADD, operDesc = "/api/koMetaData/add")
    public R add(KoMetaData koMetaData){
        save(koMetaData);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/koMetaData/edit")
    public R edit(KoMetaData koMetaData){
        updateById(koMetaData);
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/koMetaData/delete")
    public R deleteIds(List<String> ids){
        removeByIds(ids);
        return R.success();
    }
}
