package com.quickex.service.stage2;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoTextureLibraryData;
import com.quickex.domain.stage2.KoTextureLibraryType;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IKoTextureLibraryTypeService extends IBaseService<KoTextureLibraryType> {

    R publicType();
    R userType(KoTextureLibraryType entity);

    R publiclist(PageDomain pageDomain, JSONObject par);
    R userlist(PageDomain pageDomain, JSONObject par);

    R addType(KoTextureLibraryType entity);
    R deleteType(KoTextureLibraryType entity);
    R editType(KoTextureLibraryType entity);

    R deleteTexture(KoTextureLibraryData entity);
    R editTexture(KoTextureLibraryData entity);
    R addTexture(KoTextureLibraryData entity);



}
