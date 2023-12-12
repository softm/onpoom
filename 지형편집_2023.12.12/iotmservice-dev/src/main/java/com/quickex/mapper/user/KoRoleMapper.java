package com.quickex.mapper.user;

import com.quickex.domain.user.KoRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.user.KoUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoRoleMapper extends BaseMapper<KoRole> {

    List<KoRole> userList(@Param("role") KoRole role);
    List<KoRole> list(@Param("role") KoRole role);

    Integer deleteUserRole();
    Integer deleteRoleMenu();

}
