package com.quickex.service.mapfile;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.mapfile.KoUavModel;
import org.springframework.web.multipart.MultipartFile;


public interface IKoUavModelService extends IBaseService<KoUavModel> {

    R upload(MultipartFile file);

    R add(KoUavModel entity);

    R delete(KoUavModel entity);

    R edit(KoUavModel entity);

    R get(KoUavModel entity);

    R page(PageDomain pageDomain,KoUavModel entity);

    R editPicture(MultipartFile file,Integer width,Integer height);

}
