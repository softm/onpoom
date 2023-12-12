package com.quickex.web.user;


import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoLayerAccessLog;
import com.quickex.service.user.IKoLayerAccessLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-layer-access-log")
public class KoLayerAccessLogController extends BaseController {

    @Resource
    private IKoLayerAccessLogService service;

    @PostMapping("/addRecord")
    public R addRecord(@RequestBody KoLayerAccessLog entity) {
        return service.addRecord(entity);
    }

    @PostMapping("/RecordPage")
    public R RecordPage(@RequestBody KoLayerAccessLog entity) {
        return service.RecordPage(startPage(),entity);
    }

    @PostMapping("/serviceCount")
    public R serviceCount(String provinceCode) {
        return service.serviceCount(provinceCode);
    }

    @PostMapping("/serviceCount1")
    public R serviceCount1() {
        return service.serviceCount1();
    }

    @PostMapping("/useCountByType")
    public R useCountByType(String provinceCode,String startDate,String endDate) {
        return service.useCountByType(provinceCode,startDate,endDate);
    }

    @PostMapping("/useCountByService")
    public R useCountByService(String provinceCode,String startDate,String endDate) {
        return service.useCountByService(provinceCode,startDate,endDate);
    }

    @PostMapping("/allserviceUseCountByToday")
    public R allserviceUseCountByToday(){return service.allserviceUseCountByToday();}

    @PostMapping("/allserviceUseCountByThismonth")
    public R allserviceUseCountByThismonth(){return service.allserviceUseCountByThismonth();}

    @PostMapping("/allserviceUseCountByHistory")
    public R allserviceUseCountByHistory(String startDate,String endDate){return service.allserviceUseCountByHistory(startDate,endDate);}

    @PostMapping("/cityserviceUseCountByToday")
    public R cityserviceUseCountByToday(){return service.cityserviceUseCountByToday();}

    @PostMapping("/cityserviceUseCountByThismonth")
    public R cityserviceUseCountByThismonth(){return service.cityserviceUseCountByThismonth();}

    @PostMapping("/cityserviceUseCountByHistory")
    public R cityserviceUseCountByHistory(String startDate,String endDate){return service.cityserviceUseCountByHistory(startDate,endDate);}


}

