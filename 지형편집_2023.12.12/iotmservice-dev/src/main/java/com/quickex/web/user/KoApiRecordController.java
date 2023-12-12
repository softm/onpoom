package com.quickex.web.user;

import com.quickex.core.controller.BaseController;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoApiRecord;
import com.quickex.service.user.IKoApiRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/ko-api-record")
public class KoApiRecordController extends BaseController {

    @Resource
    private IKoApiRecordService service;

    @PostMapping("/apiRecordPage")
    public R apiRecordPage(@RequestBody KoApiRecord entity) {
        return service.apiRecordPage(startPage(),entity);
    }


    @PostMapping("/userListExcel")
    public R userListExcel() {
        return service.userListExcel();
    }

    @PostMapping("/apiRecordListExcel")
    public R apiRecordListExcel(@RequestBody KoApiRecord entity) {
        return service.apiRecordListExcel(entity);
    }


}

