package com.quickex.service.layer.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.core.page.PageDomain;
import com.quickex.core.util.DictUtils;
import com.quickex.domain.layer.KoDictData;
import com.quickex.mapper.layer.KoDictDataMapper;
import com.quickex.service.layer.IKoDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class KoDictDataServiceImpl extends ServiceImpl<KoDictDataMapper, KoDictData> implements IKoDictDataService {
    @Resource
    private KoDictDataMapper dictDataMapper;


    @Override
    public IPage<KoDictData> selectDictDataList(KoDictData dictData, PageDomain pageDomain) {
        Page<KoDictData> KoDictDataPage = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoDictData> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(dictData.getDictCode())){
            wrapper.lambda().eq(KoDictData::getDictCode,dictData.getDictCode());
        }
        if(StrUtil.isNotBlank(dictData.getDictType())){
            wrapper.lambda().eq(KoDictData::getDictType,dictData.getDictType());
        }
        wrapper.orderByAsc("dict_sort");
        return dictDataMapper.selectPage(KoDictDataPage,wrapper);
    }


    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }


    @Override
    public KoDictData selectDictDataById(String dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }


    @Override
    public int deleteDictDataByIds(String[] dictCodes) {
        KoDictData KoDictData = dictDataMapper.selectDictDataById(dictCodes[0]);
        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        if (row > 0) {
            DictUtils.clearOneDictCache(KoDictData.getDictType());
        }
        return row;
    }


    @Override
    public int insertDictData(KoDictData dictData) {
        int row = dictDataMapper.insert(dictData);
        if (row > 0) {
//            DictUtils.clearOneDictCache(dictData.getDictType());
        }
        return row;
    }


    @Override
    public int updateDictData(KoDictData dictData) {
        int row = dictDataMapper.updateDictData(dictData);
        if (row > 0) {
//            DictUtils.clearOneDictCache(dictData.getDictType());
        }
        return row;
    }
    @Override
    public List<KoDictData> findByDictCode(List<String> dictCodes) {
        QueryWrapper<KoDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("dict_code",dictCodes);
        return dictDataMapper.selectList(queryWrapper);
    }

    @Override
    public List<KoDictData> findByDictType(String dictType) {
        QueryWrapper<KoDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(KoDictData::getDictType,dictType);
        return dictDataMapper.selectList(queryWrapper);
    }
}