package com.quickex.mapper.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.user.KoDeveloper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.user.KoUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoDeveloperMapper extends BaseMapper<KoDeveloper> {

    List<KoDeveloper> developerPage(@Param("page") Page<KoDeveloper> page, @Param("entity") KoDeveloper entity);

    KoDeveloper developerInfo(@Param("id") String id);

}
