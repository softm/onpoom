package com.quickex.service.mapfile;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoAgentConfig;
import com.quickex.domain.mapfile.KoMapFileLabel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IKoMapFileLabelService extends IBaseService<KoMapFileLabel> {

    R getPage(PageDomain pageDomain, KoMapFileLabel entity);

    R getById(KoMapFileLabel entity);

    R add(KoMapFileLabel entity);

    R deleteIds(KoMapFileLabel entity);

}
