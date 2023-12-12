package com.quickex.service.mapfile.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoAgentConfig;
import com.quickex.domain.mapfile.KoMapFileLabel;
import com.quickex.mapper.mapfile.KoMapFileLabelMapper;
import com.quickex.service.mapfile.IKoMapFileLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class KoMapFileLabelServiceImpl extends BaseServiceImpl<KoMapFileLabelMapper, KoMapFileLabel> implements IKoMapFileLabelService {

    public R getPage(PageDomain pageDomain, KoMapFileLabel entity){
        Page<KoMapFileLabel> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoMapFileLabel> query = new QueryWrapper<>();
        if(entity.getName()!=null && !entity.getName().isEmpty()){
            query.like("name", entity.getName());
        }
        if(entity.getComment()!=null && !entity.getComment().isEmpty()){
            query.like("comment", entity.getComment());
        }
        IPage<KoMapFileLabel> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    public R getById(KoMapFileLabel entity){
        return R.success();
    }

    public R add(KoMapFileLabel entity){
        return R.success();
    }

    public R deleteIds(KoMapFileLabel entity){
        return R.success();
    }
}
