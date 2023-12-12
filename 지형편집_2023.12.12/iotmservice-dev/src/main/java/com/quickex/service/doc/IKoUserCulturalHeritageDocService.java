package com.quickex.service.doc;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.doc.KoUserCulturalHeritageDoc;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoUserCulturalHeritageDocService extends IBaseService<KoUserCulturalHeritageDoc> {

    R add(KoUserCulturalHeritageDoc entity);

    R deletes(KoUserCulturalHeritageDoc entity);

    R page(PageDomain pageDomain,KoUserCulturalHeritageDoc entity);

}
