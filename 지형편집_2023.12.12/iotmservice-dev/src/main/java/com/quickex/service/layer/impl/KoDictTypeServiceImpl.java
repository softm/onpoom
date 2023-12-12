package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.core.constant.UserConstant;
import com.quickex.core.exception.CustomException;
import com.quickex.core.page.PageDomain;
import com.quickex.core.util.DictUtils;
import com.quickex.core.util.StringUtils;
import com.quickex.domain.TreeSelect;
import com.quickex.domain.layer.KoDictData;
import com.quickex.domain.layer.KoDictType;
import com.quickex.mapper.layer.KoDictDataMapper;
import com.quickex.mapper.layer.KoDictTypeMapper;
import com.quickex.service.layer.IKoDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KoDictTypeServiceImpl extends ServiceImpl<KoDictTypeMapper, KoDictType> implements IKoDictTypeService {
    @Autowired
    private KoDictTypeMapper dictTypeMapper;

    @Autowired
    private KoDictDataMapper dictDataMapper;

    /**
     * When the project starts, initialize the dictionary to the cache
     */
    @PostConstruct
    public void init() {
        List<KoDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
        for (KoDictType dictType : dictTypeList) {
            List<KoDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
    }


    @Override
    public IPage<KoDictType> selectDictTypeList(KoDictType dictType, PageDomain pageDomain) {
        Page<KoDictType> KoDictTypePage = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoDictType> wrapper = new QueryWrapper<>();
        return dictTypeMapper.selectPage(KoDictTypePage,wrapper);
    }

    @Override
    public List<KoDictType> selectDictTypeList(KoDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }


    @Override
    public List<KoDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }


    @Override
    public List<KoDictData> selectDictDataByType(String dictType) {
        List<KoDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (StringUtils.isNotNull(dictDatas)) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }


    @Override
    public KoDictType selectDictTypeById(String dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * Query information according to dictionary type
     *
     * @param dictType Dictionary type
     * @return Dictionary type
     */
    @Override
    public KoDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * Delete dictionary type information in batch
     *
     * @param dictIds Dictionary ID to delete
     * @return
     */
    @Override
    public int deleteDictTypeByIds(String[] dictIds) {
        for (String dictId : dictIds) {
            KoDictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new CustomException(String.format("%1$sAssigned, cannot delete", dictType.getDictName()));
            }
        }
        int count = dictTypeMapper.deleteDictTypeByIds(dictIds);
        if (count > 0) {
            DictUtils.clearDictCache();
        }
        return count;
    }

    /**
     * Empty cache data
     */
    @Override
    public void clearCache() {
        DictUtils.clearDictCache();
    }

    /**
     * Add and save dictionary type information
     *
     * @param dictType Dictionary type information
     * @return
     */
    @Override
    public int insertDictType(KoDictType dictType) {
        dictType.setDictId(IdWorker.getIdStr());
        int row = dictTypeMapper.insert(dictType);
        if (row > 0) {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * Modify and save dictionary type information
     *
     * @param dictType Dictionary type information
     * @return
     */
    @Override
    @Transactional
    public int updateDictType(KoDictType dictType) {
        KoDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        int row = dictTypeMapper.updateDictType(dictType);
        if (row > 0) {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * Verify whether the dictionary type name is unique
     *
     * @param dict Dictionary type
     * @return result
     */
    @Override
    public String checkDictTypeUnique(KoDictType dict) {
        String dictId = StringUtils.isNull(dict.getDictId()) ? "-1" : dict.getDictId();
        KoDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().equals(dictId)) {
            return UserConstant.NOT_UNIQUE;
        }
        return UserConstant.UNIQUE;
    }

    @Override
    public List<TreeSelect> buildDictTreeSelect(List<KoDictType> dictTypes) {
        List<KoDictType> dictTypeTrees = buildDictTypeTree(dictTypes);
        return dictTypeTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    /**
     * Build the tree structure required by the front end
     *
     * @param dictTypes Dictionary type list
     * @return Tree structure list
     */
    @Override
    public List<KoDictType> buildDictTypeTree(List<KoDictType> dictTypes) {
        List<KoDictType> returnList = new ArrayList<KoDictType>();
        List<String> tempList = new ArrayList<>();
        for (KoDictType dictType : dictTypes) {
            tempList.add(dictType.getDictId());
        }
        for (Iterator<KoDictType> iterator = dictTypes.iterator(); iterator.hasNext(); ) {
            KoDictType dictType = iterator.next();
            // If it is a top-level node, traverse all child nodes of the parent node
            if (!tempList.contains(dictType.getParentId())) {
                recursionFn(dictTypes, dictType);
                returnList.add(dictType);
            }
        }
        if (returnList.isEmpty()) {
            returnList = dictTypes;
        }
        return returnList;
    }


    /**
     * Recursive list
     */
    private void recursionFn(List<KoDictType> list, KoDictType t) {
        //Get the list of child nodes
        List<KoDictType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (KoDictType tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * Get the list of child nodes
     */
    private List<KoDictType> getChildList(List<KoDictType> list, KoDictType t) {
        List<KoDictType> tlist = new ArrayList<KoDictType>();
        Iterator<KoDictType> it = list.iterator();
        while (it.hasNext()) {
            KoDictType n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getDictId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }


    /**
     * Determine whether there are child nodes
     */
    private boolean hasChild(List<KoDictType> list, KoDictType t) {
        return getChildList(list, t).size() > 0;
    }


}