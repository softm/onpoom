package com.quickex.mapper.doc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.doc.KoAnalysisDoc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoAnalysisDocMapper extends BaseMapper<KoAnalysisDoc> {

    List<KoAnalysisDoc> page(@Param("page") Page<KoAnalysisDoc> page, @Param("entity") KoAnalysisDoc entity);

}
