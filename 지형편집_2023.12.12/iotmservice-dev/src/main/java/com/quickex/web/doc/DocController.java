package com.quickex.web.doc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoApiRecord;
import com.quickex.service.doc.IDocService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/doc")
public class DocController {

    @Resource
    private IDocService service;

    @PostMapping("/download1")
    public R download1(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.download1(object);
    }

    @PostMapping("/download2")
    public R download2(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.download2(object);
    }

    @PostMapping("/download3")
    public R download2(
            @RequestParam(value = "file11",required = false) MultipartFile file11,
            @RequestParam(value = "file12",required = false) MultipartFile file12,
            @RequestParam(value = "file21",required = false) MultipartFile file21,
            @RequestParam(value = "file22",required = false) MultipartFile file22,
            @RequestParam(value = "file31",required = false) MultipartFile file31,
            @RequestParam(value = "file32",required = false) MultipartFile file32,
            @RequestParam(value = "file41",required = false) MultipartFile file41,
            @RequestParam(value = "file42",required = false) MultipartFile file42
    ) {
        return service.download3(file11,file12,file21,file22,file31,file32,file41,file42);
    }

    @PostMapping("/download4")
    public R download4(
            @RequestParam(value = "file11",required = false) MultipartFile file11,
            @RequestParam(value = "file12",required = false) MultipartFile file12,
            @RequestParam(value = "file21",required = false) MultipartFile file21,
            @RequestParam(value = "file22",required = false) MultipartFile file22,
            @RequestParam(value = "file31",required = false) MultipartFile file31,
            @RequestParam(value = "file32",required = false) MultipartFile file32,
            @RequestParam(value = "file41",required = false) MultipartFile file41,
            @RequestParam(value = "file42",required = false) MultipartFile file42
    ) {
        return service.download4(file11,file12,file21,file22,file31,file32,file41,file42);
    }

    @PostMapping("/download5")
    public R download5(
            @RequestParam(value = "file11",required = false) MultipartFile file11,
            @RequestParam(value = "file12",required = false) MultipartFile file12,
            @RequestParam(value = "file21",required = false) MultipartFile file21,
            @RequestParam(value = "file22",required = false) MultipartFile file22,
            @RequestParam(value = "file31",required = false) MultipartFile file31,
            @RequestParam(value = "file32",required = false) MultipartFile file32,
            @RequestParam(value = "file41",required = false) MultipartFile file41,
            @RequestParam(value = "file42",required = false) MultipartFile file42
    ) {
        return service.download5(file11,file12,file21,file22,file31,file32,file41,file42);
    }

    @PostMapping("/download6")
    public R download6(
            @RequestParam(value = "file11",required = false) MultipartFile file11,
            @RequestParam(value = "file12",required = false) MultipartFile file12,
            @RequestParam(value = "file21",required = false) MultipartFile file21,
            @RequestParam(value = "file22",required = false) MultipartFile file22,
            @RequestParam(value = "file31",required = false) MultipartFile file31,
            @RequestParam(value = "file32",required = false) MultipartFile file32,
            @RequestParam(value = "file41",required = false) MultipartFile file41,
            @RequestParam(value = "file42",required = false) MultipartFile file42,

            @RequestParam(value = "file51",required = false) MultipartFile file51,
            @RequestParam(value = "file52",required = false) MultipartFile file52,
            @RequestParam(value = "file61",required = false) MultipartFile file61,
            @RequestParam(value = "file62",required = false) MultipartFile file62,
            @RequestParam(value = "file71",required = false) MultipartFile file71,
            @RequestParam(value = "file72",required = false) MultipartFile file72,
            @RequestParam(value = "file81",required = false) MultipartFile file81,
            @RequestParam(value = "file82",required = false) MultipartFile file82
    ) {
        return service.download6(file11,file12,file21,file22,file31,file32,file41,file42
        ,file51,file52,file61,file62,file71,file72,file81,file82);
    }


    @PostMapping("/download7")
    public R download7(MultipartFile file,String position,String height,String distance,String createUser) {
        return service.download7(file,position,height,distance,createUser);
    }


    @PostMapping("/epsgConvertUpload")
    public R epsgConvertUpload(MultipartFile file) {
        return service.epsgConvertUpload(file);
    }

    @PostMapping("/epsgConvertDownload")
    public R epsgConvertDownload(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.epsgConvertDownload(object);
    }


    @PostMapping("/download8")
    public R download8(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.download8(object);
    }


    @PostMapping("/download9")
    public R download8(String files,String data) {
        JSONObject object = JSON.parseObject(data);
        return service.download9(files,object);
    }


    @PostMapping("/download10")
    public R download10(
            @RequestParam(value = "file11",required = false) MultipartFile file11,
            @RequestParam(value = "file12",required = false) MultipartFile file12,
            @RequestParam(value = "file21",required = false) MultipartFile file21,
            @RequestParam(value = "file22",required = false) MultipartFile file22,
            @RequestParam(value = "file31",required = false) MultipartFile file31,
            @RequestParam(value = "file32",required = false) MultipartFile file32,
            @RequestParam(value = "file41",required = false) MultipartFile file41,
            @RequestParam(value = "file42",required = false) MultipartFile file42,
            String startPosition,
            String visualHeight,
            String visibleDistance,
            String horizontalViewingAngle,
            String verticalViewingAngle,
            String createUser
    ) {
        return service.download10(file11,file12,file21,file22,file31,file32,file41,file42
        ,startPosition,visualHeight,visibleDistance,horizontalViewingAngle,verticalViewingAngle,createUser);
    }


    //download8 add filed
    @PostMapping("/download11")
    public R download11(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.download11(object);
    }


    @PostMapping("/downloadExampleOfOccupationLicenseLedger")
    public R downloadExampleOfOccupationLicenseLedger(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.downloadExampleOfOccupationLicenseLedger(object);
    }

    @PostMapping("/downloadExampleOfOccupationLicenseLedger1")
    public R downloadExampleOfOccupationLicenseLedger1(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.downloadExampleOfOccupationLicenseLedger1(object);
    }


    @PostMapping("/readRoadOccupationPermitAccountTemplate")
    public R readRoadOccupationPermitAccountTemplate(MultipartFile file) {
        return service.readRoadOccupationPermitAccountTemplate(file);
    }

    @PostMapping("/downloadRoadOccupationPermitAccount")
    public R downloadRoadOccupationPermitAccount(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.downloadRoadOccupationPermitAccount(object);
    }

    @PostMapping("/downloadRoadOccupationPermitAccountTemplate")
    public R downloadRoadOccupationPermitAccountTemplate() {
        return service.downloadRoadOccupationPermitAccountTemplate();
    }



    @PostMapping("/download12")
    public R download12(@RequestBody String json) {
        JSONObject object = JSON.parseObject(json);
        return service.download12(object);
    }






}
