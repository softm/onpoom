package com.quickex.service.stage2;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoTerrainTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IKoTerrainTaskService extends IBaseService<KoTerrainTask> {

    R taskList(PageDomain pageDomain, JSONObject par);

    R addTask(JSONObject par);

    R deleteTask(JSONObject par);

    R terrainList(PageDomain pageDomain,JSONObject par);

    R addTerrain(JSONObject par);

    R editTerrain(JSONObject par);

    R deleteTerrain(JSONObject par);

    R terrainPoints(JSONObject par);

    R terrainModels(JSONObject par);

    R downloadTaskGeojson(JSONObject par);

}
