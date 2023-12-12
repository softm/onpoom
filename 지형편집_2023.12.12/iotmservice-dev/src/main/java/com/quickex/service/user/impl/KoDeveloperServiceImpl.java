package com.quickex.service.user.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoDeveloper;
import com.quickex.domain.user.KoUser;
import com.quickex.mapper.user.KoDeveloperMapper;
import com.quickex.service.user.IKoDeveloperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.utils.MD5Utils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class KoDeveloperServiceImpl extends BaseServiceImpl<KoDeveloperMapper, KoDeveloper> implements IKoDeveloperService {

    public R add(KoDeveloper entity){
        this.save(entity);
        return R.success();
    }

    public R delete(KoDeveloper entity){
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoDeveloper entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoDeveloper entity){
        KoDeveloper data = this.baseMapper.developerInfo(entity.getId());
        return R.success(data);
    }

    public R page(PageDomain pageDomain, KoDeveloper entity){

        Page<KoDeveloper> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoDeveloper> list =this.baseMapper.developerPage(page,entity);

        IPage<KoDeveloper> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }
}
