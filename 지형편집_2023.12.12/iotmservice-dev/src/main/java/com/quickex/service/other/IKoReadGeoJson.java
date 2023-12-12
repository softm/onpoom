package com.quickex.service.other;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import org.springframework.web.multipart.MultipartFile;

public interface IKoReadGeoJson {

    R readTxt(MultipartFile file,String userAccount);

    R readGeoJson(MultipartFile file,String userAccount);

    R readSHP(MultipartFile file,String userAccount);


    R objConvertToGltf(JSONObject par);

    R readSHPandConvertEPSG(MultipartFile file,String userAccount,String sourceEpsg,String targetEpsg);

    R gridTool(JSONObject par);

    R getShpEPSG(MultipartFile file,String userAccount);

    R hcmnReadCsv(MultipartFile file,String userAccount);


    R readSHP1(MultipartFile file,String userAccount);

}
