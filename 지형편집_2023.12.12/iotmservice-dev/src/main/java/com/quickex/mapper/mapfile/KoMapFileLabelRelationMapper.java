package com.quickex.mapper.mapfile;

import com.quickex.domain.mapfile.Dto.KoMapFileLabelRelationDto;
import com.quickex.domain.mapfile.KoMapFileLabelRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoMapFileLabelRelationMapper extends BaseMapper<KoMapFileLabelRelation> {
    List<KoMapFileLabelRelationDto> getLables(@Param("id") String id);
}
