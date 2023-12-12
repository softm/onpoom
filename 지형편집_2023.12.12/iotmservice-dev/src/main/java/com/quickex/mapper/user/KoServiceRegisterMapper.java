package com.quickex.mapper.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.user.KoDeveloper;
import com.quickex.domain.user.KoServiceRegister;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KoServiceRegisterMapper extends BaseMapper<KoServiceRegister> {

    List<KoServiceRegister> page(@Param("page") Page<KoServiceRegister> page, @Param("entity") KoServiceRegister entity);

    KoServiceRegister get(@Param("id") String id);
}
