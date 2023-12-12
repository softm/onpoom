package com.quickex.service.layer;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.layer.KoAgentConfig;

import java.util.List;


public interface IKoAgentConfigService extends IBaseService<KoAgentConfig> {

    R getPage(PageDomain pageDomain, KoAgentConfig koAgentConfig);

    R getById(KoAgentConfig koAgentConfig);

    R add(KoAgentConfig koAgentConfig);

    R edit(KoAgentConfig koAgentConfig);

    R deleteIds(List<String> ids);

}
