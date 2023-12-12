package com.quickex.service.systable;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.systable.KoTableIndexes;


/**
 * <p>
 *Interface definition
 * </p>
 *
 * @author ffzh
 * @since 2022-01-10
 */
public interface IKoTableIndexesService extends IBaseService<KoTableIndexes> {

    R add(KoTableIndexes entity);

    R delete(KoTableIndexes entity);

    R edit(KoTableIndexes entity);

    R get(KoTableIndexes entity);

    R list(KoTableIndexes entity);

}
