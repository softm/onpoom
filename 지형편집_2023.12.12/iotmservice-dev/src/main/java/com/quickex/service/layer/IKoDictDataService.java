package com.quickex.service.layer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quickex.core.page.PageDomain;
import com.quickex.domain.layer.KoDictData;

import java.util.List;

public interface IKoDictDataService extends IService<KoDictData> {

    IPage<KoDictData> selectDictDataList(KoDictData dictData, PageDomain pageDomain);

    String selectDictLabel(String dictType, String dictValue);

    KoDictData selectDictDataById(String dictCode);

    int deleteDictDataByIds(String[] dictCodes);

    int insertDictData(KoDictData dictData);

    int updateDictData(KoDictData dictData);

    List<KoDictData> findByDictCode(List<String> dictCodes);

    List<KoDictData> findByDictType(String dictType);
}
