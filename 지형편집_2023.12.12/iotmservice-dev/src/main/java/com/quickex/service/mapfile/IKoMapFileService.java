package com.quickex.service.mapfile;

import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.mapfile.Dto.KoMapFileDto;
import com.quickex.domain.mapfile.KoMapFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.domain.mapfile.KoMapFileCollection;
import com.quickex.domain.mapfile.KoMapFileLabel;
import org.springframework.web.multipart.MultipartFile;


public interface IKoMapFileService extends IBaseService<KoMapFile> {

    R uploadZip(MultipartFile file, String treeId, String createUser);

    R uploadList(MultipartFile[] files, String treeId, String createUser);

    R getPageUser(PageDomain pageDomain, KoMapFileDto entity);

    R getPageAdmin(PageDomain pageDomain, KoMapFileDto entity);

    R edit(KoMapFile entity);

    R delete(KoMapFile entity);

    R get(KoMapFile entity);

    R getFileBinary(KoMapFile entity);

    R upload3Dtiles(MultipartFile file, String createUser,String serviceId);



    R uploadZip1(MultipartFile file, String treeId, String createUser);
    R uploadList1(MultipartFile[] files, String treeId, String createUser);

    //--------------------------------------

    R upload(MultipartFile image,MultipartFile model, String treeId, String createUser,String isAdmin);

    R upload1(MultipartFile zipFile, String treeId, String createUser,String isAdmin);

    R uploadImg(MultipartFile image,String createUser);

    R getPageUser1(PageDomain pageDomain, KoMapFile entity);

    R getPageAdmin1(PageDomain pageDomain, KoMapFile entity);

    R addCollection(KoMapFileCollection entity);

    R deleteCollection(KoMapFileCollection entity);

    R getPageCollection(PageDomain pageDomain, KoMapFile entity);

    R getAdminRootTypeFiles(KoMapFile entity);

    R uploadObj(MultipartFile file, String treeId, String createUser, String isAdmin);

    R uploadFbx(MultipartFile file, String treeId, String createUser, String isAdmin);

}
