package com.quickex.mapper.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.user.KoMenu;
import com.quickex.domain.user.KoUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.user.MapMenuResult;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;


public interface KoUserMapper extends BaseMapper<KoUser> {


     List<KoUser> allUser();

     List<KoUser> userPage(@Param("page") Page<KoUser> page,@Param("user") KoUser user);

     KoUser userInfo(@Param("account") String account);


//     List<KoMenu> userMenu(@Param("ids") List<String> ids,
//                           @Param("sysType") Integer sysType,
//                           @Param("idsStr") String idsStr
//
//     );

     List<KoMenu> selectRootMenuList(@Param("ids") List<String> ids, @Param("sysType") Integer sysType);

     List<KoMenu> selectChildrenMenuList(@Param("ids") List<String> ids, @Param("pid") String pid);



     List<MapMenuResult> selectMapRootMenuList(@Param("ids") List<String> ids, @Param("sysType") Integer sysType);

     List<MapMenuResult> selectMapChildrenMenuList(@Param("ids") List<String> ids, @Param("pid") String pid);

     List<MapMenuResult> allRoute();

     Integer deleteUserRole();
     Integer deleteUserMenu();

     Integer checkUser(@Param("account") String account);

}
