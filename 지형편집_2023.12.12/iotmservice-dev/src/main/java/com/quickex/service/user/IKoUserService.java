package com.quickex.service.user;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.user.KoRole;
import com.quickex.domain.user.KoUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IKoUserService extends IBaseService<KoUser> {
    R add(KoUser entity);
    R delete(KoUser entity);
    R edit(KoUser entity);
    R get(KoUser entity);
    R page(PageDomain pageDomain, KoUser entity);

    R mapLogin(KoUser entity);
    R adminLogin(KoUser entity);
    R editPwd(KoUser entity);

    R getMenuByAccount(KoUser entity);

    List<KoUser> allUser();

    List<String> getAllMenuIdByAccount(String account);

    ////Lazy loading
    R MapGetMenuByAccount(JSONObject par);

    R getAllRoute();
}
