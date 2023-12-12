package com.quickex.mapper.user;

import com.quickex.domain.user.KoOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoOrganizationMapper extends BaseMapper<KoOrganization> {

    List<KoOrganization> root(@Param("entity") KoOrganization entity);

    List<KoOrganization> children(@Param("entity") KoOrganization entity);

    List<KoOrganization> searchList(@Param("entity") KoOrganization entity);

}
