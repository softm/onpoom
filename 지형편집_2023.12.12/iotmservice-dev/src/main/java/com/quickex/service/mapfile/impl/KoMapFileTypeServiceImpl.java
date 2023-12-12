package com.quickex.service.mapfile.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.mapfile.KoMapFileType;
import com.quickex.mapper.mapfile.KoMapFileTypeMapper;
import com.quickex.service.mapfile.IKoMapFileCollectionService;
import com.quickex.service.mapfile.IKoMapFileService;
import com.quickex.service.mapfile.IKoMapFileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class KoMapFileTypeServiceImpl extends BaseServiceImpl<KoMapFileTypeMapper, KoMapFileType> implements IKoMapFileTypeService {

    @Autowired
    private IKoMapFileService fileService;

    @Autowired
    private IKoMapFileCollectionService collectionService;

    public R add(KoMapFileType entity){

        if(entity.getIsAdmin()==0){
            entity.setParentId("-1");
        }

        if(!entity.getParentId().equals("-1")){
            KoMapFileType check = this.getById(entity.getParentId());
            if(check==null){
                return R.error("parent is null!");
            }
            if(!check.getParentId().equals("-1")){
                return R.error("only two levels of classification can be created !");
            }
        }
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R delete(KoMapFileType entity){

        //delete node
        this.removeById(entity.getId());

        //delete nodes
        HashMap<String,Object> map3 = new HashMap<>();
        map3.put("parent_id",entity.getId());
        this.removeByMap(map3);

        //delete collection
        QueryWrapper<KoMapFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tree_id",entity.getId());
        List<KoMapFile> fileList = fileService.list(queryWrapper);
        for (KoMapFile item : fileList) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("file_id", item.getId());
            collectionService.removeByMap(map1);
        }

        //delete file
        HashMap<String,Object> map = new HashMap<>();
        map.put("tree_id",entity.getId());
        fileService.removeByMap(map);

        return R.success();
    }

    public R edit(KoMapFileType entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoMapFileType entity){
        KoMapFileType data = this.getById(entity.getId());
        return R.success(data);
    }

    public R tree(KoMapFileType entity){
        List<KoMapFileType> list = this.baseMapper.getTreeByParentId(entity.getId());
        return R.success(list);
    }

    public R list(KoMapFileType entity){
        List<KoMapFileType> list = this.baseMapper.getUserList(entity.getCreateUser());
        return R.success(list);
    }

}
