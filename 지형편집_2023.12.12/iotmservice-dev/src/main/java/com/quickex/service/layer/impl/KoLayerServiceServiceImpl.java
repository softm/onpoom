package com.quickex.service.layer.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoLayerService;
import com.quickex.domain.layer.KoMetaData;
import com.quickex.mapper.layer.KoLayerServiceMapper;
import com.quickex.mapper.layer.KoMetaDataMapper;
import com.quickex.service.layer.IKoLayerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KoLayerServiceServiceImpl extends BaseServiceImpl<KoLayerServiceMapper, KoLayerService> implements IKoLayerServiceService {

    @Autowired
    private KoLayerServiceMapper koLayerServiceMapper;

    @Autowired
    private KoMetaDataMapper koMetaDataMapper;



    @OperLog(operType = OperType.SELECT, operDesc = "/koLayerService/layer/list")
    public R getPage(PageDomain pageDomain, KoLayerService koLayerService){
        Page<KoLayerService> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoLayerService> query = new QueryWrapper<>();
        query.eq("tree_id",koLayerService.getTreeId());
        if(koLayerService.getServiceName()!=null && !koLayerService.getServiceName().isEmpty()){
            query.like("service_name", koLayerService.getServiceName());
        }
        query.orderByAsc("sort");
        IPage<KoLayerService> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/koLayerService/layer/get")
    public R getById(KoLayerService koLayerService){
        KoLayerService data = getById(koLayerService.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.ADD, operDesc = "/koLayerService/layer/add")
    public R add(KoLayerService koLayerService){
        koLayerService.setCreateTime(new Date());
        save(koLayerService);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/koLayerService/layer/edit")
    public R edit(KoLayerService koLayerService){
        updateById(koLayerService);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/koLayerService/layer/delete")
    public R deleteIds(List<String> ids){

        //check KoMetaData
        for (int i = 0; i < ids.size(); i++) {
            QueryWrapper<KoMetaData> query = new QueryWrapper<>();
            query.eq("layer_service_id",ids.get(i));
            int count = koMetaDataMapper.selectCount(query);
            if(count>0){
                return R.error("the data also contains sub data");
            }
        }

        removeByIds(ids);
        return R.success();
    }

    @Override
    public R getMaxSort(String treeId) {
        Integer maxSort = koLayerServiceMapper.getMaxSort(treeId);
        if (maxSort != null){
            return R.success(maxSort);
        }else {
            return R.success(1);
        }
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/koLayerService/layer/impactComparisonList")
    public R getImpactComparisonList(PageDomain pageDomain,String provinceId){

        Page<KoLayerService> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoLayerService> list = this.baseMapper.getImpactComparisonList(page,provinceId);

        IPage<KoLayerService> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }

    public R updateShpHeight(KoLayerService koLayerService){
        UpdateWrapper<KoLayerService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",koLayerService.getId());
        KoLayerService data = new KoLayerService();
        data.setShpHeight(koLayerService.getShpHeight());
        this.update(data, updateWrapper);
        return R.success();
    }

    public R listNew(PageDomain pageDomain,KoLayerService entity){

        if (entity.getKssj() != null && !entity.getKssj().equals("")) {
            entity.setKssj(entity.getKssj() + " 00:00:00");
        }

        if (entity.getJssj() != null && !entity.getJssj().equals("")) {
            entity.setJssj(entity.getJssj() + " 23:59:59");
        }

        Page<KoLayerService> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoLayerService> list = this.baseMapper.listNew(page,entity);

        IPage<KoLayerService> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }

    public R updateLayerType(JSONObject obj){
        String typeId = obj.getString("typeId");
        List<String> ids = obj.getJSONArray("ids").toJavaList(String.class);
        UpdateWrapper<KoLayerService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",ids);
        KoLayerService layer = new KoLayerService();
        layer.setTreeId(typeId);
        this.baseMapper.update(layer, updateWrapper);
        return R.success();
    }

}
