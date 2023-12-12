package com.quickex.mapper.layer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.layer.KoDictType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface KoDictTypeMapper extends BaseMapper<KoDictType> {
    /**

     *Query dictionary types by criteria

     *

     *@ param dicttype dictionary type information

     *@ return dictionary type collection information

     */
    List<KoDictType> selectDictTypeList(KoDictType dictType);

    /**

     *Based on all dictionary types

     *

     *@ return dictionary type collection information

     */
    List<KoDictType> selectDictTypeAll();

    /**

     *Query information according to dictionary type ID

     *

     *@ param dictid dictionary type ID

     *@ return dictionary type

     */
    KoDictType selectDictTypeById(String dictId);

    /**

     *Query information according to dictionary type

     *

     *@ param dicttype dictionary type

     *@ return dictionary type

     */
    KoDictType selectDictTypeByType(String dictType);

    /**

     *Delete dictionary information by dictionary ID

     *

     *@ param dictid dictionary ID

     *@ return result

     */
    int deleteDictTypeById(String dictId);

    /**

     *Delete dictionary type information in batch

     *

     *@ param dictids dictionary ID to be deleted

     *@ return result

     */
    int deleteDictTypeByIds(String[] dictIds);

    /**

     *Add dictionary type information

     *

     *@ param dicttype dictionary type information

     *@ return result

     */
    int insertDictType(KoDictType dictType);

    /**
     *Modify dictionary type information
     *
     *@ param dicttype dictionary type information
     *@ return result
     */
    int updateDictType(KoDictType dictType);

    /**
     *Verify whether the dictionary type name is unique
     *
     *@ param dicttype dictionary type
     *@ return result
     */
    KoDictType checkDictTypeUnique(String dictType);
}
