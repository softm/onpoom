package com.quickex.service.user;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoRole;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoRoleService extends IBaseService<KoRole> {

    R add(KoRole entity);
    R delete(KoRole entity);
    R edit(KoRole entity);
    R get(KoRole entity);
    R list(KoRole entity);

    R userList(KoRole entity);
    R updateUserRole(KoRole entity);

}
