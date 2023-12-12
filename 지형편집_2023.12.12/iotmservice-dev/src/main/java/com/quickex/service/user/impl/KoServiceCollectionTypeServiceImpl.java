package com.quickex.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoServiceCollectionType;
import com.quickex.mapper.user.KoServiceCollectionTypeMapper;
import com.quickex.service.user.IKoServiceCollectionMenuService;
import com.quickex.service.user.IKoServiceCollectionTypeService;
import com.quickex.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class KoServiceCollectionTypeServiceImpl extends BaseServiceImpl<KoServiceCollectionTypeMapper, KoServiceCollectionType> implements IKoServiceCollectionTypeService {

    @Autowired
    private IKoServiceCollectionMenuService menuService;

    public R add(KoServiceCollectionType entity){
        entity.setUserAccount(UserContext.getUserAccount());
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R edit(KoServiceCollectionType entity){
        this.updateById(entity);
        return R.success();
    }

    public R delete(KoServiceCollectionType entity){
        this.removeById(entity.getId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid", entity.getId());
        menuService.removeByMap(map);
        return R.success();
    }

    public R list(KoServiceCollectionType entity){
        QueryWrapper<KoServiceCollectionType> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_account",UserContext.getUserAccount());
        //queryWrapper.orderByDesc("create_time");
        List<KoServiceCollectionType> list = this.list(queryWrapper);
        return R.success(list);
    }
}
