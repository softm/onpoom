package com.quickex.service.user;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoOrganization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.domain.user.KoRole;


public interface IKoOrganizationService extends IBaseService<KoOrganization> {
    R add(KoOrganization entity);
    R delete(KoOrganization entity);
    R edit(KoOrganization entity);
    R get(KoOrganization entity);
    R list(KoOrganization entity);

    R tree(KoOrganization entity);


}
