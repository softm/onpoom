package com.quickex.service.user;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.domain.user.KoMenuSort;
import com.quickex.domain.user.KoOrganization;

import java.util.List;


public interface IKoMenuService extends IBaseService<KoMenu> {
    R add(KoMenu entity);
    R delete(KoMenu entity);
    R edit(KoMenu entity);
    R get(KoMenu entity);
    R allMenu(KoMenu entity);

    R allAndRoleChecked(KoMenu entity);
    R allAndUserChecked(KoMenu entity);
    R updateRoleMenu(KoMenu entity);
    R updateUserMenu(KoMenu entity);

    R updateRoleMenuSort(KoMenuSort entity);
    R updateUserMenuSort(KoMenuSort entity);

    List<KoMenu> getAllPatentAndThis(String menuId);


    R checkRouteName(KoMenu entity);
}
