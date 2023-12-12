package com.quickex.service.layer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.core.page.PageDomain;
import com.quickex.domain.TreeSelect;
import com.quickex.domain.layer.KoDictData;
import com.quickex.domain.layer.KoDictType;

import java.util.List;

public interface IKoDictTypeService extends IService<KoDictType> {

    IPage<KoDictType> selectDictTypeList(KoDictType dictType, PageDomain pageDomain);

    List<KoDictType> selectDictTypeList(KoDictType dictType);

    List<TreeSelect> buildDictTreeSelect(List<KoDictType> dictTypes);

    List<KoDictType> buildDictTypeTree(List<KoDictType> dictTypes);

    List<KoDictType> selectDictTypeAll();

    List<KoDictData> selectDictDataByType(String dictType);

    KoDictType selectDictTypeById(String dictId);

    KoDictType selectDictTypeByType(String dictType);

    int deleteDictTypeByIds(String[] dictIds);

    void clearCache();

    int insertDictType(KoDictType dictType);

    int updateDictType(KoDictType dictType);

    String checkDictTypeUnique(KoDictType dictType);
}
