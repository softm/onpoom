package com.quickex.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoDeveloper;
import com.quickex.domain.user.KoServiceRegister;
import com.quickex.mapper.user.KoServiceRegisterMapper;
import com.quickex.service.user.IKoServiceRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class KoServiceRegisterServiceImpl extends BaseServiceImpl<KoServiceRegisterMapper, KoServiceRegister> implements IKoServiceRegisterService {

    public R add(KoServiceRegister entity){
        entity.setCreateTime(new Date());
        entity.setApprovalState(0);
        this.save(entity);
        return R.success();
    }

    public R delete(KoServiceRegister entity){
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoServiceRegister entity){

        UpdateWrapper<KoServiceRegister> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",entity.getId());

        KoServiceRegister data = new KoServiceRegister();
        data.setApprovalTime(new Date());
        data.setApprovalUser(entity.getApprovalUser());
        data.setApprovalState(entity.getApprovalState());

        this.update(data,updateWrapper);

        return R.success();
    }

    public R get(KoServiceRegister entity){
        KoServiceRegister data = this.baseMapper.get(entity.getId());
        return R.success(data);
    }

    public R page(PageDomain pageDomain, KoServiceRegister entity){

        Page<KoServiceRegister> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoServiceRegister> list =this.baseMapper.page(page,entity);

        IPage<KoServiceRegister> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }

    public R update(KoServiceRegister entity){
        this.updateById(entity);
        return R.success();
    }
}
