package com.quickex.mapper.mapfile;

import com.quickex.domain.mapfile.KoMapFileType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface KoMapFileTypeMapper extends BaseMapper<KoMapFileType> {

    List<KoMapFileType> getTreeByParentId(@Param("id") String id);

    //not user
    List<KoMapFileType> getAllChildren(@Param("id") String id);

    List<KoMapFileType> getUserList(@Param("createUser") String createUser);


}
