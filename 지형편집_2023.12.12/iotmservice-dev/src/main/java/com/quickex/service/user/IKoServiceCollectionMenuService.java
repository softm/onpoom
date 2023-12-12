package com.quickex.service.user;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoServiceCollectionMenu;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoServiceCollectionMenuService extends IBaseService<KoServiceCollectionMenu> {

    R add(KoServiceCollectionMenu entity);

    R edit(KoServiceCollectionMenu entity);

    R delete(KoServiceCollectionMenu entity);

    R list(KoServiceCollectionMenu entity);

    R checkState(KoServiceCollectionMenu entity);

}
