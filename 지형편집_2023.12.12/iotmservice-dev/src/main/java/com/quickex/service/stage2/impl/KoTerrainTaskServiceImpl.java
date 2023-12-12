package com.quickex.service.stage2.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.AppConfig;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.stage2.KoTerrainModel;
import com.quickex.domain.stage2.KoTerrainPoint;
import com.quickex.domain.stage2.KoTerrainPolygon;
import com.quickex.domain.stage2.KoTerrainTask;
import com.quickex.mapper.stage2.KoTerrainTaskMapper;
import com.quickex.service.stage2.*;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.WKTUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.vividsolutions.jts.geom.Geometry;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.GeoJSON;
import org.geotools.geojson.GeoJSONUtil;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class KoTerrainTaskServiceImpl extends BaseServiceImpl<KoTerrainTaskMapper, KoTerrainTask> implements IKoTerrainTaskService {

    @Autowired
    private IKoTerrainTidService tidService;

    @Autowired
    private IKoTerrainBidService bidService;

    @Autowired
    private IKoTerrainPolygonService polygonService;

    @Autowired
    private IKoTerrainPointService pointService;

    @Autowired
    private IKoTerrainModelService modelService;

    @Autowired
    private AppConfig appConfig;

    public R taskList(PageDomain pageDomain, JSONObject par) {
        try {
            String startDate = par.getString("startDate");
            String endDate = par.getString("endDate");
            String taskName = par.getString("taskName");

            Page<KoTerrainTask> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
            QueryWrapper<KoTerrainTask> query = new QueryWrapper<>();

            if (taskName != null && !taskName.equals("")) {
                query.like("name", taskName);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (startDate != null && !startDate.equals("")) {
                startDate = startDate + " 00:00:00";
                query.ge("regist_time", sdf.parse(startDate));
            }
            if (endDate != null && !endDate.equals("")) {
                endDate = endDate + " 23:59:59";
                query.le("regist_time", sdf.parse(endDate));
            }

            query.orderByDesc("regist_time");
            IPage<KoTerrainTask> list = this.baseMapper.selectPage(page, query);
            return R.success(list);
        } catch (Exception ex) {
            log.error("ex,", ex);
            return R.error();
        }
    }

    public R addTask(JSONObject par){

        //task
        KoTerrainTask task = par.getJSONObject("task").toJavaObject(KoTerrainTask.class);
        task.setNumber(tidService.getNumber());
        task.setRegistTime(new Date());
        this.save(task);

        //polygon
        KoTerrainPolygon polygon = par.getJSONObject("polygon").toJavaObject(KoTerrainPolygon.class);
        polygon.setNumber(bidService.getNumber(task.getNumber()));
        polygon.setRegistTime(new Date());
        polygon.setTaskId(task.getId());
        polygonService.save(polygon);

        //point
        List<KoTerrainPoint> points = par.getJSONArray("points").toJavaList(KoTerrainPoint.class);
        for (KoTerrainPoint item : points) {
            item.setPolygonId(polygon.getId());
            item.setTaskId(task.getId());
            pointService.save(item);
        }

        //model
        List<KoTerrainModel> models = par.getJSONArray("models").toJavaList(KoTerrainModel.class);
        for (int i = 0; i < models.size(); i++) {
            models.get(i).setPolygonId(polygon.getId());
            models.get(i).setTaskId(task.getId());
            models.get(i).setSort(i);
            modelService.save(models.get(i));
        }

        return  R.success();
    }

    public R deleteTask(JSONObject par){
        String taskId = par.getString("taskId");
        this.removeById(taskId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("task_id", taskId);
        polygonService.removeByMap(map);
        pointService.removeByMap(map);
        modelService.removeByMap(map);
        return  R.success();
    }

    public R terrainList(PageDomain pageDomain,JSONObject par){
        String taskId = par.getString("taskId");

        Page<KoTerrainPolygon> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<KoTerrainPolygon> query = new QueryWrapper<>();
        query.eq("task_id", taskId);

        query.orderByDesc("regist_time");
        IPage<KoTerrainPolygon> list = polygonService.page(page, query);

        for (KoTerrainPolygon item:list.getRecords()) {
            //String polygonId = par.getString("polygonId");
            QueryWrapper<KoTerrainModel> query1 = new QueryWrapper<>();
            query1.eq("polygon_id",item.getId());
            query1.orderByAsc("sort");
            List<KoTerrainModel> list1 = modelService.list(query1);
            item.setModels(list1);
        }

        return R.success(list);
    }

    public R addTerrain(JSONObject par){

        //task
        String taskId = par.getString("taskId");
        String taskNumber = par.getString("taskNumber");

        //polygon
        KoTerrainPolygon polygon = par.getJSONObject("polygon").toJavaObject(KoTerrainPolygon.class);
        polygon.setNumber(bidService.getNumber(taskNumber));
        polygon.setRegistTime(new Date());
        polygon.setTaskId(taskId);
        polygonService.save(polygon);

        //point
        List<KoTerrainPoint> points = par.getJSONArray("points").toJavaList(KoTerrainPoint.class);
        for (KoTerrainPoint item : points) {
            item.setPolygonId(polygon.getId());
            item.setTaskId(taskId);
            pointService.save(item);
        }

        //model
        List<KoTerrainModel> models = par.getJSONArray("models").toJavaList(KoTerrainModel.class);
        for (int i = 0; i < models.size(); i++) {
            models.get(i).setPolygonId(polygon.getId());
            models.get(i).setTaskId(taskId);
            models.get(i).setSort(i);
            modelService.save(models.get(i));
        }
        return  R.success();
    }

    public R editTerrain(JSONObject par){

        //polygon
        KoTerrainPolygon polygon = par.getJSONObject("polygon").toJavaObject(KoTerrainPolygon.class);
        polygonService.updateById(polygon);

        //delete point and model
        HashMap<String, Object> map = new HashMap<>();
        map.put("polygon_id", polygon.getId());
        pointService.removeByMap(map);
        modelService.removeByMap(map);

        //point
        List<KoTerrainPoint> points = par.getJSONArray("points").toJavaList(KoTerrainPoint.class);
        for (KoTerrainPoint item : points) {
            item.setPolygonId(polygon.getId());
            item.setTaskId(polygon.getTaskId());
            pointService.save(item);
        }

        //model
        List<KoTerrainModel> models = par.getJSONArray("models").toJavaList(KoTerrainModel.class);
        for (int i = 0; i < models.size(); i++) {
            models.get(i).setPolygonId(polygon.getId());
            models.get(i).setTaskId(polygon.getTaskId());
            models.get(i).setSort(i);
            modelService.save(models.get(i));
        }

        return  R.success();
    }

    public R deleteTerrain(JSONObject par){
        String polygonId = par.getString("polygonId");
        polygonService.removeById(polygonId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("polygon_id", polygonId);
        pointService.removeByMap(map);
        modelService.removeByMap(map);
        return  R.success();
    }

    public R terrainPoints(JSONObject par){
        String polygonId = par.getString("polygonId");
        QueryWrapper<KoTerrainPoint> query = new QueryWrapper<>();
        query.eq("polygon_id",polygonId);
        query.orderByAsc("number");
        List<KoTerrainPoint> list = pointService.list(query);
        return  R.success(list);
    }

    public R terrainModels(JSONObject par){
        String polygonId = par.getString("polygonId");
        QueryWrapper<KoTerrainModel> query = new QueryWrapper<>();
        query.eq("polygon_id",polygonId);
        query.orderByAsc("sort");
        List<KoTerrainModel> list = modelService.list(query);
        return  R.success(list);
    }

    public R downloadTaskGeojson(JSONObject par){
        try{

            String taskId = par.getString("taskId");
            KoTerrainTask task = this.getById(taskId);

            QueryWrapper<KoTerrainPolygon> query = new QueryWrapper<>();
            query.eq("task_id",taskId);
            query.orderByDesc("regist_time");
            List<KoTerrainPolygon> polygonList = polygonService.list(query);

            SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
            typeBuilder.setName("MyFeatureType");
            typeBuilder.add("geometry", Geometry.class);
            typeBuilder.add("taskNumber", String.class);
            typeBuilder.add("boundaryNumber", String.class);
            typeBuilder.add("textureId", String.class);
            typeBuilder.add("textureUrl", String.class);
            typeBuilder.add("shpPath", String.class);
            typeBuilder.add("landNo", String.class);
            typeBuilder.add("planeHeight", String.class);
            typeBuilder.add("tunnelHeight", String.class);
            typeBuilder.add("terrainSelect", String.class);
            typeBuilder.add("pointInfo", String.class);
            typeBuilder.add("modelInfo", String.class);

            SimpleFeatureType featureType = typeBuilder.buildFeatureType();
            List<SimpleFeature> features = new ArrayList<>();

            for (KoTerrainPolygon item : polygonList) {
                Geometry geometry = WKTUtil.wktToGeom(item.getGeom());
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
                featureBuilder.set("geometry", geometry);
                featureBuilder.set("taskNumber", task.getNumber());
                featureBuilder.set("boundaryNumber", item.getNumber());
                featureBuilder.set("textureId", geoJsonNll(item.getTextureId()));
                featureBuilder.set("textureUrl", geoJsonNll(item.getTextureUrl()));
                featureBuilder.set("shpPath", geoJsonNll(item.getShpPath()));
                featureBuilder.set("landNo", geoJsonNll(item.getLandNo()));
                featureBuilder.set("planeHeight", geoJsonNll(item.getPlaneHeight()));
                featureBuilder.set("tunnelHeight", geoJsonNll(item.getTunnelHeight()));
                featureBuilder.set("terrainSelect", item.getTerrainSelect());

                QueryWrapper<KoTerrainPoint> query1 = new QueryWrapper<>();
                query1.eq("polygon_id", item.getId());
                query1.orderByAsc("number");
                List<KoTerrainPoint> list1 = pointService.list(query1);

                QueryWrapper<KoTerrainModel> query2 = new QueryWrapper<>();
                query2.eq("polygon_id", item.getId());
                query2.orderByAsc("sort");
                List<KoTerrainModel> list2 = modelService.list(query2);

                featureBuilder.set("pointInfo", JSON.toJSONString(list1));
                featureBuilder.set("modelInfo", JSON.toJSONString(list2));

                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }

            SimpleFeatureCollection featureCollection = new ListFeatureCollection(featureType, features);
            FeatureJSON featureJSON = new FeatureJSON(new GeometryJSON(14));
            String geoJSONString = featureJSON.toString(featureCollection);

            String fileName = "terrain_edit_" + System.currentTimeMillis() + ".geojson";
            String folderPath = "/temp/geojson/" + CommonUtils.currentDateToStr(7);

            File fileDir = new File(appConfig.getUavModelPath() + folderPath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }

            File file = new File(appConfig.getUavModelPath() + folderPath + "/" +fileName);
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileOutputStream fw = new FileOutputStream (appConfig.getUavModelPath() + folderPath + "/" +fileName);
            fw.write(geoJSONString.getBytes("utf-8"));
            fw.close();

            String url = folderPath + "/" + fileName;
            return  R.success(url);
        }catch (Exception ex){
            log.error("ex",ex);
            return R.error();
        }
    }

    private String geoJsonNll(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }



}
