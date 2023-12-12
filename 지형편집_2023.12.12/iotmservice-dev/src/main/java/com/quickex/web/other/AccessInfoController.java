package com.quickex.web.other;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.utils.CommonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/other/accessInfo")
public class AccessInfoController {

    @Autowired
    private AppConfig appConfig;

    @PostMapping("/todayStatistics")
    public R today() {
        try {
            String todayStr = CommonUtils.currentDateToStr(1);
            String kssj = CommonUtils.dateToStamp(todayStr + " 00:00:00");
            String jssj = CommonUtils.dateToStamp(todayStr + " 23:59:59");

            JSONObject orgListPar = new JSONObject();
            orgListPar.put("userId","admin");

            JSONObject logPar = new JSONObject();
            logPar.put("userId", "admin");
            logPar.put("sort", "time,desc");
            logPar.put("page", 1);
            logPar.put("size", 1);
            logPar.put("from", kssj);
            logPar.put("to", jssj);
            logPar.put("placeId", "");
            logPar.put("endpoint", "");
            logPar.put("personId", "");
            logPar.put("personsId", "");
            logPar.put("attend", "");
            logPar.put("fever", null);
            logPar.put("includeSubOrg", true);
            logPar.put("orgId", "");



            JSONArray orgList = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/fltp/orgn/all", orgListPar.toJSONString())
                    .getJSONArray("content");

            int todayAll = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/mntrng/cmg/diary", logPar.toJSONString())
                    .getJSONObject("contents").getInteger("totalElements");

            logPar.put("fever", true);
            int todayHave = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/mntrng/cmg/diary", logPar.toJSONString())
                    .getJSONObject("contents").getInteger("totalElements");

            int todayNo = todayAll - todayHave;

            for (int i = 0; i < orgList.size(); i++) {
                JSONObject item = orgList.getJSONObject(i);

                if(item.getString("orgnId").isEmpty()){
                    item.put("all", 0);
                    item.put("have", 0);
                    item.put("no", 0);
                    continue;
                }

                logPar.put("orgId", item.getString("orgnId"));
                logPar.put("fever", null);
                JSONObject all = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/mntrng/cmg/diary", logPar.toJSONString());

                if(!all.getString("returnCode").equals("200")){
                    item.put("all", 0);
                    item.put("have", 0);
                    item.put("no", 0);
                    continue;
                }


                int orgTodayAll = all.getJSONObject("contents").getInteger("totalElements");

                logPar.put("fever", true);
                int orgTodayHave = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/mntrng/cmg/diary", logPar.toJSONString())
                        .getJSONObject("contents").getInteger("totalElements");

                int orgTodayNo = orgTodayAll - orgTodayHave;

                item.put("all", orgTodayAll);
                item.put("have", orgTodayHave);
                item.put("no", orgTodayNo);
            }

            JSONObject result = new JSONObject();
            result.put("all", todayAll);
            result.put("have", todayHave);
            result.put("no", todayNo);
            result.put("orgList", orgList);

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    @PostMapping("/byDepartmentStatistics")
    public R byDepartment(@RequestBody String request) {
        try {
            JSONObject object = JSON.parseObject(request);

            List<String> dateList = CommonUtils.getDayList(object.getString("startDate"), object.getString("endDate"));

            JSONArray result = new JSONArray();

            JSONObject logPar = new JSONObject();
            logPar.put("userId", "admin");
            logPar.put("sort", "time,desc");
            logPar.put("page", 1);
            logPar.put("size", 1);
            logPar.put("from", "");
            logPar.put("to", "");
            logPar.put("placeId", "");
            logPar.put("endpoint", "");
            logPar.put("personId", "");
            logPar.put("personsId", "");
            logPar.put("attend", "");
            logPar.put("fever", null);
            logPar.put("includeSubOrg", true);
            logPar.put("orgId", "");

            for (String item : dateList) {
                JSONObject value = new JSONObject();
                value.put("date", item);

                String kssj = CommonUtils.dateToStamp(item + " 00:00:00");
                String jssj = CommonUtils.dateToStamp(item + " 23:59:59");

                logPar.put("from", kssj);
                logPar.put("to", jssj);


                logPar.put("orgId", object.getString("orgnId"));
                logPar.put("fever", null);
                JSONObject all = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/mntrng/cmg/diary", logPar.toJSONString());

                if(!all.getString("returnCode").equals("200")){
                    value.put("all", 0);
                    value.put("have", 0);
                    value.put("no", 0);
                    continue;
                }


                int orgTodayAll = all.getJSONObject("contents").getInteger("totalElements");

                logPar.put("fever", true);
                int orgTodayHave = httpPost(appConfig.getAccessInfoHttpUrl() + "/services/bldg/mntrng/cmg/diary", logPar.toJSONString())
                        .getJSONObject("contents").getInteger("totalElements");

                int orgTodayNo = orgTodayAll - orgTodayHave;

                value.put("all", orgTodayAll);
                value.put("have", orgTodayHave);
                value.put("no", orgTodayNo);

                result.add(value);
            }

            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }

    private JSONObject httpPost(String url, String body) {

        JSONObject result = new JSONObject();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        StringEntity entity = new StringEntity(body, "UTF-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                result = JSON.parseObject(EntityUtils.toString(responseEntity, "utf-8"));
            } else {
                result = null;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            result = null;
            return result;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
