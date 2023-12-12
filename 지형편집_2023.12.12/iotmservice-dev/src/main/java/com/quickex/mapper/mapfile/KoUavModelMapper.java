package com.quickex.mapper.mapfile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.mapfile.KoUavModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KoUavModelMapper extends BaseMapper<KoUavModel> {

    List<KoUavModel> page(@Param("page") Page<KoUavModel> page, @Param("entity") KoUavModel entity);

}
