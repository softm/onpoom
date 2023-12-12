package com.quickex.service.user;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoDeveloper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.domain.user.KoUser;


public interface IKoDeveloperService extends IBaseService<KoDeveloper> {
    R add(KoDeveloper entity);
    R delete(KoDeveloper entity);
    R edit(KoDeveloper entity);
    R get(KoDeveloper entity);
    R page(PageDomain pageDomain, KoDeveloper entity);
}
