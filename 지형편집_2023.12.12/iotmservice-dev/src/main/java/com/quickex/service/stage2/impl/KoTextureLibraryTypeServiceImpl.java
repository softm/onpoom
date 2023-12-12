package com.quickex.service.stage2.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoTextureLibraryData;
import com.quickex.domain.stage2.KoTextureLibraryType;
import com.quickex.mapper.stage2.KoTextureLibraryTypeMapper;
import com.quickex.service.stage2.IKoTextureLibraryDataService;
import com.quickex.service.stage2.IKoTextureLibraryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class KoTextureLibraryTypeServiceImpl extends BaseServiceImpl<KoTextureLibraryTypeMapper, KoTextureLibraryType> implements IKoTextureLibraryTypeService {

    @Autowired
    private IKoTextureLibraryDataService dataService;

    public R publicType(){
        QueryWrapper<KoTextureLibraryType> q1 = new QueryWrapper<>();
        q1.eq("pid","-1");
        q1.eq("is_public",1);
        q1.eq("type_level",1);
        q1.orderByAsc("sort");
        List<KoTextureLibraryType> list1 = this.list(q1);

        for (KoTextureLibraryType item1:list1) {
            QueryWrapper<KoTextureLibraryType> q2 = new QueryWrapper<>();
            q2.eq("pid",item1.getId());
            q2.eq("is_public",1);
            q2.eq("type_level",2);
            q2.orderByAsc("sort");
            List<KoTextureLibraryType> list2 = this.list(q2);
            item1.setChildren(list2);

//            for (KoTextureLibraryType item2:list2) {
//                QueryWrapper<KoTextureLibraryType> q3 = new QueryWrapper<>();
//                q3.eq("pid",item2.getId());
//                q3.eq("is_public",1);
//                q3.eq("type_level",3);
//                q3.orderByAsc("sort");
//                List<KoTextureLibraryType> list3 = this.list(q3);
//                item2.setChildren(list3);
//            }
        }
        return R.success(list1);
    }

    public R userType(KoTextureLibraryType entity){
        QueryWrapper<KoTextureLibraryType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",entity.getUserAccount());
        queryWrapper.eq("is_public",0);
        queryWrapper.eq("type_level",0);
        queryWrapper.orderByAsc("create_time");
        List<KoTextureLibraryType> list = this.list(queryWrapper);
        return R.success(list);
    }

    public R publiclist(PageDomain pageDomain, JSONObject par){
        String typeId = par.getString("treeId");
        String name = par.getString("name");
        Integer sortType = par.getInteger("sortType");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.publiclist(page,typeId,name,sortType);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }
    public R userlist(PageDomain pageDomain, JSONObject par){
        String typeId = par.getString("treeId");
        String name = par.getString("name");
        Integer sortType = par.getInteger("sortType");
        String userAccount = par.getString("userAccount");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.userlist(page,typeId,name,sortType,userAccount);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R addType(KoTextureLibraryType entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R deleteType(KoTextureLibraryType entity){

        QueryWrapper<KoTextureLibraryType> q1 = new QueryWrapper<>();
        q1.eq("pid", entity.getId());
        Integer check1 = this.count(q1);
        if (check1 != 0) {
            return R.error("there is still data under the category that cannot be deleted!");
        }

        QueryWrapper<KoTextureLibraryData> q2 = new QueryWrapper<>();
        q2.eq("tree_id", entity.getId());
        Integer check2 = dataService.count(q2);
        if (check2 != 0) {
            return R.error("there is still data under the category that cannot be deleted!");
        }

        this.removeById(entity);
        return R.success();
    }

    public R editType(KoTextureLibraryType entity){
        this.updateById(entity);
        return R.success();
    }

    public R deleteTexture(KoTextureLibraryData entity){
        dataService.removeById(entity);
        return R.success();
    }

    public R editTexture(KoTextureLibraryData entity){
        dataService.updateById(entity);
        return R.success();
    }

    public R addTexture(KoTextureLibraryData entity){
        entity.setCreateTime(new Date());
        dataService.save(entity);
        return R.success();
    }
}
