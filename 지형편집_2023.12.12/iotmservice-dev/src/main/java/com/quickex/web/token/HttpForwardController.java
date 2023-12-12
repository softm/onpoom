package com.quickex.web.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.service.user.IKoUserService;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/**
 * http Request forwarding
 */
@RestController
@RequestMapping("/api/httpForward")
public class HttpForwardController {

    @Autowired
    private AppConfig appConfig;

    //get
    @PostMapping("/lx_uav_api")
    public R forward(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpPost = new HttpGet(object.getString("url"));
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
//        String jsonString =json.toJSONString();
//        StringEntity entity = new StringEntity("", "UTF-8");
//        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        if(object.get("header")!=null){
            JSONArray headers = object.getJSONArray("header");
            for (int i = 0; i < headers.size(); i++) {
                httpPost.setHeader(
                        headers.getJSONObject(i).getString("name"),
                        headers.getJSONObject(i).getString("value")
                );
            }
        }

//        httpPost.setHeader("X-DT-TI", appConfig.getApiServicekey());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return R.success(JSON.parse(EntityUtils.toString(responseEntity,"utf-8")));
            }else{
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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

    //post
    @PostMapping("/lx_uav_api_post")
    public R forward_post(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(object.getString("url"));
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
        if(object.getString("body")!=null){
            //String jsonString =object.toJSONString();
            StringEntity entity = new StringEntity(object.getString("body"), "UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        if(object.get("header")!=null){
            JSONArray headers = object.getJSONArray("header");
            for (int i = 0; i < headers.size(); i++) {
                httpPost.setHeader(
                        headers.getJSONObject(i).getString("name"),
                        headers.getJSONObject(i).getString("value")
                );
            }
        }

//        httpPost.setHeader("X-DT-TI", appConfig.getApiServicekey());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return R.success(JSON.parse(EntityUtils.toString(responseEntity, "utf-8")));
            } else {
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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

    //put
    @PostMapping("/lx_uav_api_put")
    public R lx_uav_api_put(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut httpPost = new HttpPut(object.getString("url"));
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
        if(object.getString("body")!=null && !object.getString("body").equals("")){
            //String jsonString =object.toJSONString();
            StringEntity entity = new StringEntity(object.getString("body"), "UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        if(object.get("header")!=null){
            JSONArray headers = object.getJSONArray("header");
            for (int i = 0; i < headers.size(); i++) {
                httpPost.setHeader(
                        headers.getJSONObject(i).getString("name"),
                        headers.getJSONObject(i).getString("value")
                );
            }
        }

//        httpPost.setHeader("X-DT-TI", appConfig.getApiServicekey());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return R.success(JSON.parse(EntityUtils.toString(responseEntity, "utf-8")));
            } else {
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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


    //post - form_urlencoded
    @PostMapping("/lx_uav_api_form_urlencoded")
    public R lx_uav_api_form_urlencoded(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(object.getString("url"));
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            if(object.get("formData")!=null){
                JSONArray formData = object.getJSONArray("formData");
                for (int i = 0; i < formData.size(); i++) {
                    NameValuePair pair = new BasicNameValuePair(
                            formData.getJSONObject(i).getString("name"),
                            formData.getJSONObject(i).getString("value")
                    );
                    params.add(pair);
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }


        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                JSONObject result = new JSONObject();
                result.put("result",EntityUtils.toString(responseEntity, "utf-8"));
                return R.success(result);
            } else {
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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


    //delete
    @PostMapping("/lx_uav_api_delete")
    public R lx_uav_api_delete(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete httpPost = new HttpDelete(object.getString("url"));
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        if(object.get("header")!=null){
            JSONArray headers = object.getJSONArray("header");
            for (int i = 0; i < headers.size(); i++) {
                httpPost.setHeader(
                        headers.getJSONObject(i).getString("name"),
                        headers.getJSONObject(i).getString("value")
                );
            }
        }

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return R.success(JSON.parse(EntityUtils.toString(responseEntity, "utf-8")));
            } else {
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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






    ///   not use
    @PostMapping("/lx_uav_api1")
    public R lx_uav_api1(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpPost = new HttpGet(object.getString("url"));
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(9000)
                .setConnectionRequestTimeout(9000)
                .setSocketTimeout(9000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
//        String jsonString =json.toJSONString();
//        StringEntity entity = new StringEntity("", "UTF-8");
//        httpPost.setEntity(entity);



        httpPost.addHeader("Content-Type", "application/json;charset=utf8");
        httpPost.addHeader("X-DT-CREDENTIAL", object.getString("certificateInfo"));
        httpPost.addHeader("X-DT-TI", object.getString("transactionID"));



        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return R.success(JSON.parse(EntityUtils.toString(responseEntity,"utf-8")));
            }else{
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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

    ///   not use
    @PostMapping("/lx_hotspot_buffer_api")
    public R lx_hotspot_buffer_api(@RequestBody String request) {

        JSONObject object = JSON.parseObject(request);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(object.getString("url"));   //url
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);

        //body
        String jsonString =object.getString("body");  //body
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        //httpPost.addHeader("","");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return R.success(JSON.parse(EntityUtils.toString(responseEntity,"utf-8")));
            }else{
                return R.error();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }finally {
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


    @PostMapping("/testtest")
    public R testtest(@RequestParam String ppp) {
        return R.success(ppp);
    }

}
