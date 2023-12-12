package com.quickex.web.other;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.service.other.IKoReadGeoJson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/other/fileInfo")
public class OtherFileInfoController {

    @Resource
    private IKoReadGeoJson service;

    @PostMapping("/readTxt")
    public R readTxt(MultipartFile file, String userAccount){
         return service.readTxt(file,userAccount);
    }

    @PostMapping("/readGeoJson")
    public R readGeoJson(MultipartFile file,String userAccount){
        return service.readGeoJson(file,userAccount);
    }

    @PostMapping("/readSHP")
    public R readSHP(MultipartFile file,String userAccount){
        return service.readSHP(file,userAccount);
    }

    @PostMapping("/objConvertToGltf")
    public R objConvertToGltf(@RequestBody String json){
        return service.objConvertToGltf(JSON.parseObject(json));
    }

    @PostMapping("/readSHPandConvertEPSG")
    public R readSHPandConvertEPSG(MultipartFile file,String userAccount,String sourceEpsg,String targetEpsg){
        return service.readSHPandConvertEPSG(file,userAccount,sourceEpsg,targetEpsg);
    }




    @PostMapping("/gridTool")
    public R gridTool(@RequestBody String json){
        return service.gridTool(JSON.parseObject(json));
    }


    @PostMapping("/getShpEPSG")
    public R getShpEPSG(MultipartFile file,String userAccount){
        return service.getShpEPSG(file,userAccount);
    }


    @PostMapping("/hcmn-read-csv")
    public R hcmnReadCsv(MultipartFile file,String userAccount){
        return service.hcmnReadCsv(file,userAccount);
    }



    @PostMapping("/readSHP1")
    public R readSHP1(MultipartFile file,String userAccount){
        return service.readSHP1(file,userAccount);
    }



}
