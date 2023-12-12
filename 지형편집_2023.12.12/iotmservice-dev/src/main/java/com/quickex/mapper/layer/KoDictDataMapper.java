package com.quickex.mapper.layer;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quickex.domain.layer.KoDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface KoDictDataMapper extends BaseMapper<KoDictData> {
    /**

     *Query dictionary data in pages according to conditions

     *

     *@ param dictdata dictionary data information

     *@ return dictionary data collection information

     */
    List<KoDictData> selectDictDataList(KoDictData dictData);

    /**

     *Query dictionary data according to dictionary type

     *

     *@ param dicttype dictionary type

     *@ return dictionary data collection information

     */
    List<KoDictData> selectDictDataByType(String dictType);

    /**

     *Query dictionary data information according to dictionary type and dictionary key value

     *

     *@ param dicttype dictionary type

     *@ param dictvalue dictionary key value

     *@ return dictionary label

     */
    String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * selectDictDataById
     *
     * @param dictCode
     * @return
     */
    KoDictData selectDictDataById(String dictCode);

    /**
     * Query dictionary data
     *
     * @param dictType
     * @return
     */
    int countDictDataByType(String dictType);

    /**

     *Delete dictionary data information through dictionary ID

     *

     *@ param dictcode dictionary data ID

     *@ return result

     */
    int deleteDictDataById(String dictCode);

    /**

     *Batch delete dictionary data information

     *

     *@ param dictcodes dictionary data ID to be deleted

     *@ return result

     */
    int deleteDictDataByIds(String[] dictCodes);

    /**

     *Add dictionary data information

     *

     *@ param dictdata dictionary data information

     *@ return result

     */
    int insertDictData(KoDictData dictData);

    /**

     *Modify dictionary data information

     *

     *@ param dictdata dictionary data information

     *@ return result

     */
    int updateDictData(KoDictData dictData);

    /**

     *Synchronously modify dictionary type

     *

     *@ param olddicttype old dictionary type

     *@ param newdicttype old and new dictionary types

     *@ return result

     */
    int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
