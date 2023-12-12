package com.quickex.service.user;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoServiceRegister;


public interface IKoServiceRegisterService extends IBaseService<KoServiceRegister> {
    R add(KoServiceRegister entity);
    R delete(KoServiceRegister entity);
    R edit(KoServiceRegister entity);
    R get(KoServiceRegister entity);
    R page(PageDomain pageDomain, KoServiceRegister entity);
    R update(KoServiceRegister entity);
}
