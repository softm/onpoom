package com.quickex.service.doc;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.doc.KoUserCulturalHeritagePdf;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;


public interface IKoUserCulturalHeritagePdfService extends IBaseService<KoUserCulturalHeritagePdf> {

    R add(MultipartFile file,String docName,String regionName,String createUser);

    R delete(KoUserCulturalHeritagePdf entity);

    R edit(KoUserCulturalHeritagePdf entity);

    R get(KoUserCulturalHeritagePdf entity);

    R list(KoUserCulturalHeritagePdf entity);

    R docxToPdf(String docxPath,String createUser);

}
