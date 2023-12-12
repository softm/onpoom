package com.quickex.mapper.user;

import com.quickex.domain.user.KoMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.user.KoRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoMenuMapper extends BaseMapper<KoMenu> {

    List<KoMenu> allMenu(@Param("menu") KoMenu menu);

    List<KoMenu> allAndRoleChecked(@Param("menu") KoMenu menu);

    List<KoMenu> allAndUserChecked(@Param("menu") KoMenu menu);


    List<KoMenu> getAllPatentAndThis(@Param("id") String id);

}

