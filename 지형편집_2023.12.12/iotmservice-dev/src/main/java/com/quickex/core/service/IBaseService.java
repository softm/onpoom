package com.quickex.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBaseService<T> {
    boolean save(T var1);

    boolean saveBatch(Collection<T> var1);

    boolean saveBatch(Collection<T> var1, int var2);

    boolean saveOrUpdateBatch(Collection<T> var1);

    boolean saveOrUpdateBatch(Collection<T> var1, int var2);

    boolean removeById(Serializable var1);

    boolean removeByMap(Map<String, Object> var1);

    boolean remove(Wrapper<T> var1);

    boolean removeByIds(Collection<? extends Serializable> var1);

    boolean updateById(T var1);

    boolean update(T var1, Wrapper<T> var2);

    boolean updateBatchById(Collection<T> var1);

    boolean updateBatchById(Collection<T> var1, int var2);

    boolean saveOrUpdate(T var1);

    T getById(Serializable var1);

    Collection<T> listByIds(Collection<? extends Serializable> var1);

    Collection<T> listByMap(Map<String, Object> var1);

    T getOne(Wrapper<T> var1);

    Map<String, Object> getMap(Wrapper<T> var1);

    Object getObj(Wrapper<T> var1);

    int count(Wrapper<T> var1);

    List<T> list(Wrapper<T> var1);

    IPage<T> page(IPage<T> var1, Wrapper<T> var2);

    List<Map<String, Object>> listMaps(Wrapper<T> var1);

    List<Object> listObjs(Wrapper<T> var1);

    IPage<Map<String, Object>> pageMaps(IPage<T> var1, Wrapper<T> var2);
}
