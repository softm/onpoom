package com.quickex.service.doc.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.doc.KoAnalysisDoc;
import com.quickex.mapper.doc.KoAnalysisDocMapper;
import com.quickex.service.doc.IKoAnalysisDocService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class KoAnalysisDocServiceImpl extends BaseServiceImpl<KoAnalysisDocMapper, KoAnalysisDoc> implements IKoAnalysisDocService {

    public R page(PageDomain pageDomain, KoAnalysisDoc entity){
        if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
            entity.setStartDate(entity.getStartDate()+" 00:00:00");
        }
        if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
            entity.setEndDate(entity.getEndDate()+" 23:59:59");
        }
        Page<KoAnalysisDoc> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoAnalysisDoc> list =this.baseMapper.page(page,entity);
        IPage<KoAnalysisDoc> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R add(KoAnalysisDoc entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R deletes(KoAnalysisDoc entity){
        this.removeByIds(entity.getIds());
        return R.success();
    }

}
