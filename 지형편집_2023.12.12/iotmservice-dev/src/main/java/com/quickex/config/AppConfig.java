package com.quickex.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value={"file:${catalina.home}/appconf/admin-manager/app.properties"},encoding = "utf-8")
public class AppConfig {

    /**
     * File upload path
     */
    @Value("${ko.mapFilePath}")
    private String mapFilePath;

    /**
     * File access address
     */
//    @Value("${ko.mapFileUrl}")
//    private String mapFileUrl;

    /**
     *  Layer file upload address
     */
//    @Value("${ko.layerServiceFilePath}")
//    private String layerServiceFilePath;

    /**
     * Layer file access address
     */
//    @Value("${ko.layerServiceFileUrl}")
//    private String layerServiceFileUrl;


    @Value("${ko.shpTempPath}")
    private String shpTempPath;

    @Value("${ko.uavModelPath}")
    private String uavModelPath;

//    @Value("${ko.apiServicekey}")
//    private String apiServicekey;

    @Value("${ko.wordTemplate}")
    private String wordTemplate;

    @Value("${ko.accessInfoHttpUrl}")
    private String accessInfoHttpUrl;

    @Value("${ko.nsaFilePath}")
    private String nsaFilePath;

    @Value("${ko.lxFilePath}")
    private String lxFilePath;


//    @Value("${ko.test1}")
//    private String test1;
//    @Value("${ko.test2}")
//    private String test2;

    @Value("${ko.fmeConvertUrl}")
    private String fmeConvertUrl;

    @Value("${ko.fmeAuthorization}")
    private String fmeAuthorization;


}
