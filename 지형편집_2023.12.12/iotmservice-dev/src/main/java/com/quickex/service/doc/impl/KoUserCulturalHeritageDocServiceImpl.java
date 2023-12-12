package com.quickex.service.doc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.doc.KoUserCulturalHeritageDoc;
import com.quickex.mapper.doc.KoUserCulturalHeritageDocMapper;
import com.quickex.service.doc.IKoUserCulturalHeritageDocService;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class KoUserCulturalHeritageDocServiceImpl extends BaseServiceImpl<KoUserCulturalHeritageDocMapper, KoUserCulturalHeritageDoc> implements IKoUserCulturalHeritageDocService {

    public R add(KoUserCulturalHeritageDoc entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R deletes(KoUserCulturalHeritageDoc entity){
        this.removeByIds(entity.getIds());
        return R.success();
    }

    public R page(PageDomain pageDomain, KoUserCulturalHeritageDoc entity) {
        Page<KoUserCulturalHeritageDoc> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<KoUserCulturalHeritageDoc> query = new QueryWrapper<>();
        if (entity.getDocName() != null && !entity.getDocName().equals("")) {
            query.like("doc_name", entity.getDocName());
        }
        if (entity.getDocType() != null) {
            query.eq("doc_type", entity.getDocType());
        }
        query.eq("create_user",entity.getCreateUser());
        query.orderByDesc("create_time");
        IPage<KoUserCulturalHeritageDoc> list = this.baseMapper.selectPage(page, query);
        return R.success(list);
    }

}
