package com.quickex.service.systable;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.systable.KoTableForeignkey;

/**
 * <p>
 *Interface definition
 * </p>
 *
 * @author ffzh
 * @since 2022-01-10
 */
public interface IKoTableForeignkeyService extends IBaseService<KoTableForeignkey> {

    R add(KoTableForeignkey entity);

    R delete(KoTableForeignkey entity);

    R edit(KoTableForeignkey entity);

    R get(KoTableForeignkey entity);

    R list(KoTableForeignkey entity);

}
