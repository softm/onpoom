package com.quickex.service.doc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.AppConfig;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.doc.KoUserCulturalHeritageShp;
import com.quickex.domain.doc.UserCulturalHeritageCreateSHPModel;
import com.quickex.mapper.doc.KoUserCulturalHeritageShpMapper;
import com.quickex.service.doc.IKoUserCulturalHeritageShpService;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.ShapeUtil;
import com.quickex.utils.WKTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class KoUserCulturalHeritageShpServiceImpl extends BaseServiceImpl<KoUserCulturalHeritageShpMapper, KoUserCulturalHeritageShp> implements IKoUserCulturalHeritageShpService {

    @Autowired
    private AppConfig appConfig;

    public R add(KoUserCulturalHeritageShp entity) {
        entity.setCreateTime(new Date());
        entity.setPid("-1");
        this.save(entity);
        for (KoUserCulturalHeritageShp item : entity.getChildren()) {
            item.setPid(entity.getId());
            item.setCreateTime(entity.getCreateTime());
            this.save(item);
        }
        return R.success();
    }

    public R delete(KoUserCulturalHeritageShp entity) {
        this.removeById(entity.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("pid",entity.getId());
        this.removeByMap(map);
        return R.success();
    }

    public R edit(KoUserCulturalHeritageShp entity){
        this.updateById(entity);
        Map<String,Object> map = new HashMap<>();
        map.put("pid",entity.getId());
        this.removeByMap(map);
        for (KoUserCulturalHeritageShp item : entity.getChildren()) {
            item.setPid(entity.getId());
            item.setCreateTime(entity.getCreateTime());
            this.save(item);
        }
        return R.success();
    }

    public R get(KoUserCulturalHeritageShp entity){
        KoUserCulturalHeritageShp data = this.getById(entity.getId());

        QueryWrapper<KoUserCulturalHeritageShp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",entity.getId());
        List<KoUserCulturalHeritageShp> list = this.list(queryWrapper);

        data.setChildren(list);

        return R.success(data);
    }

    public R page(PageDomain pageDomain, KoUserCulturalHeritageShp entity){
        Page<KoUserCulturalHeritageShp> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<KoUserCulturalHeritageShp> query = new QueryWrapper<>();
        if (entity.getAreaName() != null && !entity.getAreaName().equals("")) {
            query.like("area_name", entity.getAreaName());
        }
        query.eq("pid", "-1");
        query.eq("create_user",entity.getCreateUser());
        query.orderByDesc("create_time");
        IPage<KoUserCulturalHeritageShp> list = this.baseMapper.selectPage(page, query);
        return R.success(list);
    }

    public R list(KoUserCulturalHeritageShp entity){
        QueryWrapper<KoUserCulturalHeritageShp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",entity.getId());
        List<KoUserCulturalHeritageShp> list = this.list(queryWrapper);
        return R.success(list);
    }

    public R downloadBase(KoUserCulturalHeritageShp entity){
        try {

            KoUserCulturalHeritageShp data = this.getById(entity.getId());
            QueryWrapper<KoUserCulturalHeritageShp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid",entity.getId());
            List<KoUserCulturalHeritageShp> cList = this.list(queryWrapper);

            List<UserCulturalHeritageCreateSHPModel> poinList = new ArrayList<>();
            List<UserCulturalHeritageCreateSHPModel> lineStringList = new ArrayList<>();
            List<UserCulturalHeritageCreateSHPModel> polygonList = new ArrayList<>();

            String pointUrl = "";
            String lineStringUrl = "";
            String polygonUrl = "";

            polygonList.add(
                    new UserCulturalHeritageCreateSHPModel()
                            .setGeom(WKTUtil.wktToGeom(data.getWkt()))
                            .setAreaName(data.getAreaName())
                            .setPlaneValue(data.getPlaneValue())
                            .setTiltValue(data.getTiltValue())
                            .setEpsg(data.getEpsg())
            );

            for (KoUserCulturalHeritageShp item : cList) {
                if (item.getFileType() == 1) {
                    poinList.add(
                            new UserCulturalHeritageCreateSHPModel()
                                    .setGeom(WKTUtil.wktToGeom(item.getWkt()))
                                    .setAreaName(item.getAreaName())
                                    .setPlaneValue(item.getPlaneValue())
                                    .setTiltValue(item.getTiltValue())
                                    .setEpsg(item.getEpsg())
                    );
                } else if (item.getFileType() == 2) {
                    lineStringList.add(
                            new UserCulturalHeritageCreateSHPModel()
                                    .setGeom(WKTUtil.wktToGeom(item.getWkt()))
                                    .setAreaName(item.getAreaName())
                                    .setPlaneValue(item.getPlaneValue())
                                    .setTiltValue(item.getTiltValue())
                                    .setEpsg(item.getEpsg())
                    );

                } else if (item.getFileType() == 3) {
                    polygonList.add(
                            new UserCulturalHeritageCreateSHPModel()
                                    .setGeom(WKTUtil.wktToGeom(item.getWkt()))
                                    .setAreaName(item.getAreaName())
                                    .setPlaneValue(item.getPlaneValue())
                                    .setTiltValue(item.getTiltValue())
                                    .setEpsg(item.getEpsg())
                    );
                }
            }

            if(poinList.size()!=0){
                String newName = CommonUtils.getUUID();
                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
                String folder = appConfig.getUavModelPath() + "/" + data.getCreateUser() + "/" + folderName;
                String filePath = folder + "/" + newName + ".shp";
                ShapeUtil.write2Shape(filePath, "EUC_KR", "Point", poinList);
                ShapeUtil.zipShapeFile(filePath);
                String url = "/" + data.getCreateUser() + "/" + folderName + "/" + newName + ".zip";
                pointUrl = url;
            }

            if(lineStringList.size()!=0){
                String newName = CommonUtils.getUUID();
                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
                String folder = appConfig.getUavModelPath() + "/" + data.getCreateUser() + "/" + folderName;
                String filePath = folder + "/" + newName + ".shp";
                ShapeUtil.write2Shape(filePath, "EUC_KR", "LineString", lineStringList);
                ShapeUtil.zipShapeFile(filePath);
                String url = "/" + data.getCreateUser() + "/" + folderName + "/" + newName + ".zip";
                lineStringUrl = url;
            }

            if(polygonList.size()!=0){
                String newName = CommonUtils.getUUID();
                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
                String folder = appConfig.getUavModelPath() + "/" + data.getCreateUser() + "/" + folderName;
                String filePath = folder + "/" + newName + ".shp";
                ShapeUtil.write2Shape(filePath, "EUC_KR", "Polygon", polygonList);
                ShapeUtil.zipShapeFile(filePath);
                String url = "/" + data.getCreateUser() + "/" + folderName + "/" + newName + ".zip";
                polygonUrl = url;
            }

            JSONObject result = new JSONObject();
            result.put("pointUrl", pointUrl);
            result.put("lineStringUrl", lineStringUrl);
            result.put("polygonUrl", polygonUrl);
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }

    public R downloadChildren(KoUserCulturalHeritageShp entity) {
        try {

            KoUserCulturalHeritageShp data = this.getById(entity.getId());
            List<UserCulturalHeritageCreateSHPModel> list = new ArrayList<>();
            UserCulturalHeritageCreateSHPModel item = new UserCulturalHeritageCreateSHPModel();
            item.setGeom(WKTUtil.wktToGeom(data.getWkt()));
            item.setAreaName(data.getAreaName());
            item.setPlaneValue(data.getPlaneValue());
            item.setTiltValue(data.getTiltValue());
            item.setEpsg(data.getEpsg());
            list.add(item);

            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
            String folder = appConfig.getUavModelPath() + "/" + data.getCreateUser() + "/" + folderName;
            String filePath = folder + "/" + newName + ".shp";

            if (data.getFileType() == 1) {
                ShapeUtil.write2Shape(filePath, "EUC_KR", "Point", list);
            } else if (data.getFileType() == 2) {
                ShapeUtil.write2Shape(filePath, "EUC_KR", "LineString", list);
            } else if (data.getFileType() == 3) {
                ShapeUtil.write2Shape(filePath, "EUC_KR", "Polygon", list);
            } else {
                return R.error("Unknown type");
            }
            ShapeUtil.zipShapeFile(filePath);

            String url = "/" + data.getCreateUser() + "/" + folderName + "/" + newName + ".zip";
            JSONObject result = new JSONObject();
            result.put("url", url);
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }

    public R downloadShp(List<KoUserCulturalHeritageShp> list){
        try {

            if(list.size()==0){
                return R.error();
            }

            List<UserCulturalHeritageCreateSHPModel> poinList = new ArrayList<>();
            List<UserCulturalHeritageCreateSHPModel> lineStringList = new ArrayList<>();
            List<UserCulturalHeritageCreateSHPModel> polygonList = new ArrayList<>();

            //List<UserCulturalHeritageCreateSHPModel> multiPolygonList = new ArrayList<>();

            String pointUrl = "";
            String lineStringUrl = "";
            String polygonUrl = "";
            //String multiPolygonList = "";

            for (KoUserCulturalHeritageShp item : list) {
                if (item.getFileType() == 1) {
                    poinList.add(
                            new UserCulturalHeritageCreateSHPModel()
                                    .setGeom(WKTUtil.wktToGeom(item.getWkt()))
                                    .setAreaName(item.getAreaName())
                                    .setPlaneValue(item.getPlaneValue())
                                    .setTiltValue(item.getTiltValue())
                                    .setEpsg(item.getEpsg())
                    );
                } else if (item.getFileType() == 2) {
                    lineStringList.add(
                            new UserCulturalHeritageCreateSHPModel()
                                    .setGeom(WKTUtil.wktToGeom(item.getWkt()))
                                    .setAreaName(item.getAreaName())
                                    .setPlaneValue(item.getPlaneValue())
                                    .setTiltValue(item.getTiltValue())
                                    .setEpsg(item.getEpsg())
                    );

                } else if (item.getFileType() == 3) {
                    polygonList.add(
                            new UserCulturalHeritageCreateSHPModel()
                                    .setGeom(WKTUtil.wktToGeom(item.getWkt()))
                                    .setAreaName(item.getAreaName())
                                    .setPlaneValue(item.getPlaneValue())
                                    .setTiltValue(item.getTiltValue())
                                    .setEpsg(item.getEpsg())
                    );
                }
            }

            if(poinList.size()!=0){
                String newName = CommonUtils.getUUID();
                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
                String folder = appConfig.getUavModelPath() + "/" + list.get(0).getCreateUser() + "/" + folderName;
                String filePath = folder + "/" + newName + ".shp";
                ShapeUtil.write2Shape(filePath, "EUC_KR", "Point", poinList);
                ShapeUtil.zipShapeFile(filePath);
                String url = "/" + list.get(0).getCreateUser() + "/" + folderName + "/" + newName + ".zip";
                pointUrl = url;
            }

            if(lineStringList.size()!=0){
                String newName = CommonUtils.getUUID();
                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
                String folder = appConfig.getUavModelPath() + "/" + list.get(0).getCreateUser() + "/" + folderName;
                String filePath = folder + "/" + newName + ".shp";
                ShapeUtil.write2Shape(filePath, "EUC_KR", "LineString", lineStringList);
                ShapeUtil.zipShapeFile(filePath);
                String url = "/" + list.get(0).getCreateUser() + "/" + folderName + "/" + newName + ".zip";
                lineStringUrl = url;
            }

            if(polygonList.size()!=0){
                String newName = CommonUtils.getUUID();
                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
                String folder = appConfig.getUavModelPath() + "/" + list.get(0).getCreateUser() + "/" + folderName;
                String filePath = folder + "/" + newName + ".shp";
                ShapeUtil.write2Shape(filePath, "EUC_KR", "Polygon", polygonList);
                ShapeUtil.zipShapeFile(filePath);
                String url = "/" + list.get(0).getCreateUser() + "/" + folderName + "/" + newName + ".zip";
                polygonUrl = url;
            }

//            if(polygonList.size()!=0){
//                String newName = CommonUtils.getUUID();
//                String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
//                String folder = appConfig.getUavModelPath() + "/" + list.get(0).getCreateUser() + "/" + folderName;
//                String filePath = folder + "/" + newName + ".shp";
//                ShapeUtil.write2Shape(filePath, "EUC_KR", "Polygon", polygonList);
//                ShapeUtil.zipShapeFile(filePath);
//                String url = "/" + list.get(0).getCreateUser() + "/" + folderName + "/" + newName + ".zip";
//                polygonUrl = url;
//            }

            JSONObject result = new JSONObject();
            result.put("pointUrl", pointUrl);
            result.put("lineStringUrl", lineStringUrl);
            result.put("polygonUrl", polygonUrl);
//            result.put("multiPolygonList", multiPolygonList);

            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }


    public R downloadMultiPolygon(JSONObject data) {
        try {
            List<UserCulturalHeritageCreateSHPModel> geoms = new ArrayList<>();
            JSONArray list = data.getJSONArray("data");

            UserCulturalHeritageCreateSHPModel base = new UserCulturalHeritageCreateSHPModel();
            base.setGeom(WKTUtil.wktToGeom(data.getString("crltsGeom")));
            base.setAreaName(data.getString("userCrltsNm"));
            base.setPlaneValue("");
            base.setTiltValue("");
            base.setEpsg(data.getString("epsgCd"));
            geoms.add(base);

            for (int i = 0; i < list.size(); i++) {
                UserCulturalHeritageCreateSHPModel item = new UserCulturalHeritageCreateSHPModel();
                item.setGeom(WKTUtil.wktToGeom(list.getJSONObject(i).getString("geom")));
                item.setAreaName(list.getJSONObject(i).getString("zoneNum"));
                item.setPlaneValue(list.getJSONObject(i).getString("flatHg"));
                item.setTiltValue(list.getJSONObject(i).getString("sltHg"));
                item.setEpsg(list.getJSONObject(i).getString("epsgCd"));
                geoms.add(item);
            }

            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7) + "/" + CommonUtils.currentDateToStr(8);
            String folder = appConfig.getUavModelPath() + "/" + folderName;
            String filePath = folder + "/" + newName + ".shp";

            ShapeUtil.write2Shape(filePath, "EUC_KR", "MultiPolygon", geoms);
            ShapeUtil.zipShapeFile(filePath);

            String url = "/" + folderName + "/" + newName + ".zip";
            JSONObject result = new JSONObject();
            result.put("url", url);
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }









}
