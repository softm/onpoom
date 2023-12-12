package com.quickex.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.layer.KoProvince;
import com.quickex.domain.user.KoLayerAccessLog;
import com.quickex.domain.user.KoOrganization;
import com.quickex.mapper.layer.KoProvinceMapper;
import com.quickex.mapper.user.KoLayerAccessLogMapper;
import com.quickex.service.user.IKoLayerAccessLogService;
import com.quickex.service.user.IKoOrganizationService;
import com.quickex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class KoLayerAccessLogServiceImpl extends BaseServiceImpl<KoLayerAccessLogMapper, KoLayerAccessLog> implements IKoLayerAccessLogService {

    @Autowired
    private KoProvinceMapper koProvinceMapper;

    @Autowired
    private IKoOrganizationService organizationService;

    public R addRecord(KoLayerAccessLog entity){
        entity.setTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R RecordPage(PageDomain pageDomain, KoLayerAccessLog entity){
        Page<LinkedHashMap<String,Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());


        if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
            entity.setStartDate(entity.getStartDate()+" 00:00:00");
        }

        if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
            entity.setEndDate(entity.getEndDate()+" 23:59:59");
        }

        List<LinkedHashMap<String,Object>> list =this.baseMapper.RecordPage(
                page,
                entity.getLayerProvinceCode(),
                entity.getOrigin(),
                entity.getStartDate(),
                entity.getEndDate()
        );

        IPage<LinkedHashMap<String,Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());
        return R.success(data);
    }

    public R serviceCount(String provinceCode){
        LinkedHashMap<String,Object> map = this.baseMapper.serviceCount(provinceCode);
        return R.success(map);
    }

    public R serviceCount1(){
        JSONObject result = new JSONObject();
        result.put("all",this.baseMapper.serviceCount2());
        result.put("items",this.baseMapper.serviceCount1());
        return R.success(result);
    }

    public R useCountByType(String provinceCode,String startDate,String endDate){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.useCountByType(provinceCode,startDate,endDate);
        return R.success(list);
    }

    public R useCountByService(String provinceCode,String startDate,String endDate){
        List<LinkedHashMap<String,Object>> list = this.baseMapper.useCountByService(provinceCode,startDate,endDate);
        return R.success(list);
    }


    public R allserviceUseCountByToday() {
        List<LinkedHashMap<String, Object>> list = this.baseMapper.allserviceUseCountByToday();
        return R.success(list);
    }

    public R allserviceUseCountByThismonth() {
        String start = CommonUtils.currentDateToStr(1, CommonUtils.getmindate());
        String end = CommonUtils.currentDateToStr(1, CommonUtils.getmaxdate());
        List<String> dateList = CommonUtils.getDayList(start, end);
        List<LinkedHashMap<String, Object>> list = this.baseMapper.allserviceUseCountByThismonth(dateList);
        return R.success(list);
    }

    public R allserviceUseCountByHistory(String startDate, String endDate) {
        if(startDate==null||endDate==null || startDate.equals("")||endDate.equals("")){
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            startDate = year+"-01";
            endDate  = year+"-12";
        }
        List<String> dateList = CommonUtils.getMonthList(startDate, endDate);
        List<LinkedHashMap<String, Object>> list = this.baseMapper.allserviceUseCountByHistory(dateList);
        return R.success(list);
    }

    public R cityserviceUseCountByToday() {
//        List<KoProvince> provinceList = koProvinceMapper.selectList(new QueryWrapper<>());
//        JSONArray result = new JSONArray();
//        for (KoProvince item : provinceList) {
//            JSONObject object = new JSONObject();
//            object.put("name", item.getName());
//            object.put("code", item.getProvinceCode());
//            List<LinkedHashMap<String, Object>> list = this.baseMapper.cityserviceUseCountByToday(item.getProvinceCode());
//            object.put("data", list);
//            result.add(object);
//        }
//        return R.success(result);

        QueryWrapper<KoOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_type", 1);
        List<KoOrganization> olist = organizationService.list(queryWrapper);
        JSONArray result = new JSONArray();
        for (KoOrganization item : olist) {
            JSONObject object = new JSONObject();
            object.put("name", item.getItemName());
            object.put("code", item.getItemCode());
            List<LinkedHashMap<String, Object>> list = this.baseMapper.cityserviceUseCountByToday(item.getId());
            object.put("data", list);
            result.add(object);
        }
        return R.success(result);
    }

    public R cityserviceUseCountByThismonth() {
//        List<KoProvince> provinceList = koProvinceMapper.selectList(new QueryWrapper<>());
//        JSONArray result = new JSONArray();
//        String start = CommonUtils.currentDateToStr(1, CommonUtils.getmindate());
//        String end = CommonUtils.currentDateToStr(1, CommonUtils.getmaxdate());
//        List<String> dateList = CommonUtils.getDayList(start, end);
//        for (KoProvince item : provinceList) {
//            JSONObject object = new JSONObject();
//            object.put("name", item.getName());
//            object.put("code", item.getProvinceCode());
//            List<LinkedHashMap<String, Object>> list = this.baseMapper.cityserviceUseCountByThismonth(dateList,item.getProvinceCode());
//            object.put("data", list);
//            result.add(object);
//        }
//        return R.success(result);

        QueryWrapper<KoOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_type", 1);
        List<KoOrganization> olist = organizationService.list(queryWrapper);

        JSONArray result = new JSONArray();
        String start = CommonUtils.currentDateToStr(1, CommonUtils.getmindate());
        String end = CommonUtils.currentDateToStr(1, CommonUtils.getmaxdate());
        List<String> dateList = CommonUtils.getDayList(start, end);
        for (KoOrganization item : olist) {
            JSONObject object = new JSONObject();
            object.put("name", item.getItemName());
            object.put("code", item.getItemCode());
            List<LinkedHashMap<String, Object>> list = this.baseMapper.cityserviceUseCountByThismonth(dateList,item.getId());
            object.put("data", list);
            result.add(object);
        }
        return R.success(result);
    }

    public R cityserviceUseCountByHistory(String startDate, String endDate) {
//        List<KoProvince> provinceList = koProvinceMapper.selectList(new QueryWrapper<>());
//        JSONArray result = new JSONArray();
//
//        if(startDate.isEmpty()||endDate.isEmpty()){
//            Calendar date = Calendar.getInstance();
//            String year = String.valueOf(date.get(Calendar.YEAR));
//            startDate = year+"-01";
//            endDate  = year+"-12";
//        }
//
//        List<String> dateList = CommonUtils.getMonthList(startDate, endDate);
//        for (KoProvince item : provinceList) {
//            JSONObject object = new JSONObject();
//            object.put("name", item.getName());
//            object.put("code", item.getProvinceCode());
//            List<LinkedHashMap<String, Object>> list = this.baseMapper.cityserviceUseCountByHistory(dateList,item.getProvinceCode());
//            object.put("data", list);
//            result.add(object);
//        }
//        return R.success(result);

        QueryWrapper<KoOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_type", 1);
        List<KoOrganization> olist = organizationService.list(queryWrapper);
        JSONArray result = new JSONArray();

        if(startDate==null||endDate==null || startDate.equals("")||endDate.equals("")){
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            startDate = year+"-01";
            endDate  = year+"-12";
        }

        List<String> dateList = CommonUtils.getMonthList(startDate, endDate);
        for (KoOrganization item : olist) {
            JSONObject object = new JSONObject();
            object.put("name", item.getItemName());
            object.put("code", item.getItemCode());
            List<LinkedHashMap<String, Object>> list = this.baseMapper.cityserviceUseCountByHistory(dateList,item.getId());
            object.put("data", list);
            result.add(object);
        }
        return R.success(result);
    }

}
