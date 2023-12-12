package com.quickex.service.log;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.log.KoMapserviceRecord;

public interface IKoMapserviceRecordService extends IBaseService<KoMapserviceRecord> {

    R add(KoMapserviceRecord entity);

    R page(PageDomain pageDomain,KoMapserviceRecord entity);

    R toExcel(KoMapserviceRecord entity);


    R apiPag(PageDomain pageDomain, JSONObject par);
    R apiYear(PageDomain pageDomain, JSONObject par);
    R apiMonth(PageDomain pageDomain, JSONObject par);
    R apiDay(PageDomain pageDomain, JSONObject par);


    R menuPag(PageDomain pageDomain, JSONObject par);
    R menuYear(PageDomain pageDomain, JSONObject par);
    R menuMonth(PageDomain pageDomain, JSONObject par);
    R menuDay(PageDomain pageDomain, JSONObject par);


    R apiPagExcel(PageDomain pageDomain, JSONObject par);
    R apiYearExcel(PageDomain pageDomain, JSONObject par);
    R apiMonthExcel(PageDomain pageDomain, JSONObject par);
    R apiDayExcel(PageDomain pageDomain, JSONObject par);


    R menuPagExcel(PageDomain pageDomain, JSONObject par);
    R menuYearExcel(PageDomain pageDomain, JSONObject par);
    R menuMonthExcel(PageDomain pageDomain, JSONObject par);
    R menuDayExcel(PageDomain pageDomain, JSONObject par);

    void deleteApiLog();
    void deleteLoginLog();

}
