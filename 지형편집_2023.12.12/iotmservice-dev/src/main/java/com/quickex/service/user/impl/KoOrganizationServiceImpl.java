package com.quickex.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoOrganization;
import com.quickex.domain.user.KoUser;
import com.quickex.mapper.user.KoOrganizationMapper;
import com.quickex.service.user.IKoOrganizationService;
import com.quickex.service.user.IKoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class KoOrganizationServiceImpl extends BaseServiceImpl<KoOrganizationMapper, KoOrganization> implements IKoOrganizationService {

    @Autowired
    private IKoUserService userService;

    public R add(KoOrganization entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }
    public R delete(KoOrganization entity){
        QueryWrapper<KoUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_id",entity.getId()).
                or().eq("area_id",entity.getId())
               .or().eq("organization_id",entity.getId())
               .or().eq("department_id",entity.getId());
        int count = userService.count(queryWrapper);
        if(count!=0){
            return R.error("this category is referenced by the user and cannot be deleted !");
        }
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoOrganization entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoOrganization entity){
        KoOrganization data = this.getById(entity.getId());
        return R.success(data);
    }

    public R list(KoOrganization entity){
        QueryWrapper<KoOrganization> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("id",entity.getId());
        queryWrapper.eq("pid",entity.getPid());
        queryWrapper.eq("item_type",entity.getItemType());
        List<KoOrganization> list = this.list(queryWrapper);
        return R.success(list);
    }

    public R tree(KoOrganization entity){

//        if(entity.getItemName()!=null && !entity.getItemName().equals("")){
//            List<KoOrganization> list = this.baseMapper.searchList(entity);
//            for (KoOrganization item: list) {
//                item.setChildren(new ArrayList<>());
//            }
//            return R.success(list);
//        }


        List<KoOrganization> list = this.baseMapper.root(entity);
        for (KoOrganization item : list) {
            List<KoOrganization> cList = this.baseMapper.children(item);
            item.setChildren(cList);
            for (KoOrganization item1: cList) {
                item1.setChildren(new ArrayList<>());
            }
        }
        return R.success(list);
    }
}
