package com.quickex.service.doc;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.doc.KoUserCulturalHeritageShp;
import com.quickex.mapper.doc.KoUserCulturalHeritageShpMapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface IKoUserCulturalHeritageShpService extends IBaseService<KoUserCulturalHeritageShp> {

    R add(KoUserCulturalHeritageShp entity);

    R delete(KoUserCulturalHeritageShp entity);

    R edit(KoUserCulturalHeritageShp entity);

    R get(KoUserCulturalHeritageShp entity);

    R page(PageDomain pageDomain, KoUserCulturalHeritageShp entity);

    R list(KoUserCulturalHeritageShp entity);

    R downloadBase(KoUserCulturalHeritageShp entity);

    R downloadChildren(KoUserCulturalHeritageShp entity);

    R downloadShp(List<KoUserCulturalHeritageShp> entity);

    R downloadMultiPolygon(JSONObject data);

}
