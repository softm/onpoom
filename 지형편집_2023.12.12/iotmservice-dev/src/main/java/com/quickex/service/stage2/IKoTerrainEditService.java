package com.quickex.service.stage2;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.stage2.KoTerrainEdit;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoTerrainEditService extends IBaseService<KoTerrainEdit> {

    R add(KoTerrainEdit entity);

    R delete(KoTerrainEdit entity);

    R edit(KoTerrainEdit entity);

    R page(PageDomain pageDomain,KoTerrainEdit entity);

    R get(KoTerrainEdit entity);

}
