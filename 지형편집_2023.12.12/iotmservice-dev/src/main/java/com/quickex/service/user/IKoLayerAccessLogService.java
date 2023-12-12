package com.quickex.service.user;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoLayerAccessLog;


public interface IKoLayerAccessLogService extends IBaseService<KoLayerAccessLog> {

    R addRecord(KoLayerAccessLog entity);

    R RecordPage(PageDomain pageDomain, KoLayerAccessLog entity);

    R serviceCount(String provinceCode);
    R serviceCount1();

    R useCountByType(String provinceCode,String startDate,String endDate);

    R useCountByService(String provinceCode,String startDate,String endDate);

    R allserviceUseCountByToday();
    R allserviceUseCountByThismonth();
    R allserviceUseCountByHistory(String startDate,String endDate);
    R cityserviceUseCountByToday();
    R cityserviceUseCountByThismonth();
    R cityserviceUseCountByHistory(String startDate,String endDate);

}
