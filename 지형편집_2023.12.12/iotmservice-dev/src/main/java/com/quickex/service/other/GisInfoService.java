package com.quickex.service.other;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;

public interface GisInfoService {

    R pointBuffer(JSONObject par);
    R lineBuffer(JSONObject par);
    R polygonBuffer(JSONObject par);

    R contain1(JSONObject par);

    R contain2(JSONObject par);

    R getArea(JSONObject par);



    R dissolver(JSONObject par);

}
