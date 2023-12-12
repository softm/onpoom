package com.quickex.service.user;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoApiRecord;


public interface IKoApiRecordService extends IBaseService<KoApiRecord> {

    R apiRecordPage(PageDomain pageDomain, KoApiRecord entity);

    R userListExcel();

    R apiRecordListExcel(KoApiRecord entity);


}
