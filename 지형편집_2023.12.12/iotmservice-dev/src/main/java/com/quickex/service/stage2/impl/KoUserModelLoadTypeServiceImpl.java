package com.quickex.service.stage2.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoUserModelLoad;
import com.quickex.domain.stage2.KoUserModelLoadType;
import com.quickex.mapper.stage2.KoUserModelLoadTypeMapper;
import com.quickex.service.stage2.IKoUserModelLoadService;
import com.quickex.service.stage2.IKoUserModelLoadTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class KoUserModelLoadTypeServiceImpl extends BaseServiceImpl<KoUserModelLoadTypeMapper, KoUserModelLoadType> implements IKoUserModelLoadTypeService {

    @Autowired
    private IKoUserModelLoadService modelLoadService;

    @Transactional
    public R add(JSONObject entity){
        KoUserModelLoadType type = new KoUserModelLoadType();
        type.setCreateTime(new Date());
        type.setName(entity.getString("name"));
        type.setUserAccount(entity.getString("userAccount"));
        this.save(type);
        List<KoUserModelLoad> list = entity.getJSONArray("models").toJavaList(KoUserModelLoad.class);
        for (KoUserModelLoad item : list) {
            item.setCreateTime(type.getCreateTime());
            item.setUserAccount(type.getUserAccount());
            item.setTypeId(type.getId());
            modelLoadService.save(item);
        }
        return R.success();
    }

    @Transactional
    public R delete(JSONObject entity){
        String id = entity.getString("id");
        this.removeById(id);
        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("type_id", id);
        modelLoadService.removeByMap(deleteMap1);
        return R.success();
    }

    @Transactional
    public R editModel(JSONObject entity){
        KoUserModelLoadType type = this.getById(entity.getString("id"));
        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("type_id", type.getId());
        modelLoadService.removeByMap(deleteMap1);
        List<KoUserModelLoad> list = entity.getJSONArray("models").toJavaList(KoUserModelLoad.class);
        for (KoUserModelLoad item : list) {
            item.setCreateTime(type.getCreateTime());
            item.setUserAccount(type.getUserAccount());
            item.setTypeId(type.getId());
            modelLoadService.save(item);
        }
        return R.success();
    }

    public R editTypeName(JSONObject entity){
        UpdateWrapper<KoUserModelLoadType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", entity.getString("id"));
        KoUserModelLoadType type = new KoUserModelLoadType();
        type.setName(entity.getString("name"));
        this.baseMapper.update(type,updateWrapper);
        return R.success();
    }

    public R page(PageDomain pageDomain, JSONObject entity) {
        Page<KoUserModelLoadType> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<KoUserModelLoadType> query = new QueryWrapper<>();

        query.eq("user_account",entity.getString("userAccount"));
        if (entity.getString("name") != null && !entity.getString("name").equals("")) {
            query.like("name", entity.getString("name"));
        }
        query.select().orderByDesc("create_time");
        IPage<KoUserModelLoadType> typePage = this.baseMapper.selectPage(page, query);
        for (KoUserModelLoadType item : typePage.getRecords()) {
            QueryWrapper<KoUserModelLoad> q1 = new QueryWrapper<>();
            q1.eq("type_id", item.getId());
            q1.orderByAsc("model_name");
            List<KoUserModelLoad> models = modelLoadService.list(q1);
            item.setModels(models);
        }
        return R.success(typePage);
    }

}
