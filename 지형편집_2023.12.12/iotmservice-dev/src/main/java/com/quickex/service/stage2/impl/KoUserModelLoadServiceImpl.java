package com.quickex.service.stage2.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoUserModelLoad;
import com.quickex.mapper.stage2.KoUserModelLoadMapper;
import com.quickex.service.stage2.IKoUserModelLoadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class KoUserModelLoadServiceImpl extends BaseServiceImpl<KoUserModelLoadMapper, KoUserModelLoad> implements IKoUserModelLoadService {

    public R add(KoUserModelLoad entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R deletes(JSONObject entity){
        List<String> ids = entity.getJSONArray("ids").toJavaList(String.class);
        this.removeByIds(ids);
        return R.success();
    }

    public R list(KoUserModelLoad entity){
        QueryWrapper<KoUserModelLoad> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",entity.getUserAccount());
        queryWrapper.orderByDesc("create_time");
        List<KoUserModelLoad> list = this.list(queryWrapper);
        return R.success(list);
    }

    @Transactional
    public R adds(JSONObject entity) {
        List<KoUserModelLoad> list = entity.getJSONArray("list").toJavaList(KoUserModelLoad.class);
        String userAccount = entity.getString("userAccount");

        Date date = new Date();
        for (KoUserModelLoad item : list) {
            item.setCreateTime(date);
        }

        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("user_account", userAccount);
        this.removeByMap(deleteMap1);

        for (KoUserModelLoad item : list) {
           this.save(item);
        }

        return R.success();
    }
}
