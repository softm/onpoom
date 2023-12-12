package com.quickex.mapper.layer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.layer.KoLayerType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface KoLayerTypeMapper extends BaseMapper<KoLayerType> {

}
