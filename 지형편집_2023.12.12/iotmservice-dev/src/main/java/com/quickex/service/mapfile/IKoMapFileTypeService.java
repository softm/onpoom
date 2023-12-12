package com.quickex.service.mapfile;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.mapfile.KoMapFileType;


public interface IKoMapFileTypeService extends IBaseService<KoMapFileType> {

    R add(KoMapFileType entity);

    R delete(KoMapFileType entity);

    R edit(KoMapFileType entity);

    R get(KoMapFileType entity);

    R tree(KoMapFileType entity);

    R list(KoMapFileType entity);

}