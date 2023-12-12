package com.quickex.service.user;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoServiceCollectionType;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IKoServiceCollectionTypeService extends IBaseService<KoServiceCollectionType> {

    R add(KoServiceCollectionType entity);

    R edit(KoServiceCollectionType entity);

    R delete(KoServiceCollectionType entity);

    R list(KoServiceCollectionType entity);

}
