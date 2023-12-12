package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.layer.KoProvince;
import com.quickex.mapper.layer.KoMenuCatalogMapper;
import com.quickex.mapper.layer.KoProvinceMapper;
import com.quickex.service.layer.IKoProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class KoProvinceServiceImpl extends BaseServiceImpl<KoProvinceMapper, KoProvince> implements IKoProvinceService {

    @Autowired
    private KoMenuCatalogMapper koMenuCatalogMapper;

    @OperLog(operType = OperType.SELECT, operDesc = "/api/province/list")
    public R getPage(PageDomain pageDomain, KoProvince koProvince){
        Page<KoProvince> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoProvince> query = new QueryWrapper<>();
        if(koProvince.getName()!=null && !koProvince.getName().isEmpty()){
            query.like("name", koProvince.getName());
        }
        IPage<KoProvince> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/province/get")
    public R getById(KoProvince koProvince){
        KoProvince data = getById(koProvince.getId());
        return R.success(data);
    }

    @Transactional
    @OperLog(operType = OperType.ADD, operDesc = "/api/province/add")
    public R add(KoProvince koProvince){

        //check province_code
        QueryWrapper<KoProvince> query = new QueryWrapper<>();
        query.eq("province_code",koProvince.getProvinceCode());
        int count = this.baseMapper.selectCount(query);
        if(count>0){
            return R.error("province code already exists");
        }

        //If this item is added by default, change other items to not by default
        if(koProvince.getIsDefault()!=null && koProvince.getIsDefault()==1){
            UpdateWrapper<KoProvince> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("is_default",1);
            KoProvince uw = new KoProvince();
            uw.setIsDefault(0);
            update(uw, updateWrapper);
        }

        koProvince.setCreateTime(new Date());
        save(koProvince);

        return R.success();
    }

    @Transactional
    @OperLog(operType = OperType.EDIT, operDesc = "/api/province/edit")
    public R edit(KoProvince koProvince){

        //check province_code
//        QueryWrapper<KoProvince> query = new QueryWrapper<>();
//        query.eq("province_code",koProvince.getProvinceCode());
//        int count = this.baseMapper.selectCount(query);
//        if(count>0){
//            return R.error("province code already exists");
//        }

        //If the modified item is the default, change the others to be not the default
        if(koProvince.getIsDefault()!=null && koProvince.getIsDefault()==1){
            UpdateWrapper<KoProvince> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("is_default",1);
            KoProvince uw = new KoProvince();
            uw.setIsDefault(0);
            update(uw, updateWrapper);
        }

        updateById(koProvince);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/province/delete")
    public R deleteIds(KoProvince koProvince){

        //check KoMenuCatalog
        QueryWrapper<KoMenuCatalog> query = new QueryWrapper<>();
        query.eq("province_id",koProvince.getId());
        int count = koMenuCatalogMapper.selectCount(query);
        if(count>0){
            return R.error("the data also contains sub data");
        }

        removeById(koProvince.getId());
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/province/getDefault")
    public R getDefault(){
        QueryWrapper<KoProvince> query = new QueryWrapper<>();
        query.eq("is_default",1);
        KoProvince data = getOne(query);
        return R.success(data);
    }
}
