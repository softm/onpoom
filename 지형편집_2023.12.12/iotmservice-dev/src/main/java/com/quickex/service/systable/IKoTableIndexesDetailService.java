package com.quickex.service.systable;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.systable.KoTableIndexesDetail;

/**
 * <p>
 *Interface definition
 * </p>
 *
 * @author ffzh
 * @since 2022-01-10
 */
public interface IKoTableIndexesDetailService extends IBaseService<KoTableIndexesDetail> {

    R add(KoTableIndexesDetail entity);

    R delete(KoTableIndexesDetail entity);

    R edit(KoTableIndexesDetail entity);

    R get(KoTableIndexesDetail entity);

    R list(KoTableIndexesDetail entity);

}
