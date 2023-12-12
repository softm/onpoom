package com.quickex.service.doc;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.doc.KoAnalysisDoc;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoAnalysisDocService extends IBaseService<KoAnalysisDoc> {

    R page(PageDomain pageDomain,KoAnalysisDoc entity);

    R add(KoAnalysisDoc entity);

    R deletes(KoAnalysisDoc entity);

}
