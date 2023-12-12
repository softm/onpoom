package com.quickex.service.systable;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.systable.KoTableField;

/**
 * <p>
 *Interface definition
 * </p>
 *
 * @author ffzh
 * @since 2022-01-10
 */
public interface IKoTableFieldService extends IBaseService<KoTableField> {

    R add(KoTableField entity);

    R delete(KoTableField entity);

    R edit(KoTableField entity);

    R get(KoTableField entity);

    R list(KoTableField entity);

}
