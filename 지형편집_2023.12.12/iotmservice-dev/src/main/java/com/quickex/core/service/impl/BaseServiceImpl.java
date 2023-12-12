package com.quickex.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import com.quickex.core.service.IBaseService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public  class BaseServiceImpl<M extends BaseMapper<T>,T> extends EgovAbstractServiceImpl implements IBaseService<T> {
    @Autowired
    protected M baseMapper;

    public BaseServiceImpl() {
    }

    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(this.currentModelClass());
    }

    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(this.currentModelClass()));
    }

    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(this.currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    public boolean save(T entity) {
        return retBool(this.baseMapper.insert(entity));
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean saveBatch(Collection<T> entityList) {
        return this.saveBatch(entityList, 30);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = this.sqlSessionBatch();
        int i = 0;
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE);

        try {
            for(Iterator var6 = entityList.iterator(); var6.hasNext(); ++i) {
                T anEntityList = (T) var6.next();
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }

            batchSqlSession.flushStatements();
        } finally {
            this.closeSqlSession(batchSqlSession);
        }

        return true;
    }

    public boolean saveOrUpdate(T entity) {
        if (null == entity) {
            return false;
        } else {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    return this.save(entity);
                } else {
                    return this.updateById(entity) || this.save(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return this.saveOrUpdateBatch(entityList, 30);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        } else {
            SqlSession batchSqlSession = this.sqlSessionBatch();

            try {
                Iterator var4 = entityList.iterator();

                while(var4.hasNext()) {
                    T anEntityList = (T) var4.next();
                    this.saveOrUpdate(anEntityList);
                }

                batchSqlSession.flushStatements();
                return true;
            } finally {
                this.closeSqlSession(batchSqlSession);
            }
        }
    }

    public boolean removeById(Serializable id) {
        return SqlHelper.delBool(this.baseMapper.deleteById(id));
    }

    public boolean removeByMap(Map<String, Object> columnMap) {
        if (ObjectUtils.isEmpty(columnMap)) {
            throw ExceptionUtils.mpe("removeByMap columnMap is empty.");
        } else {
            return SqlHelper.delBool(this.baseMapper.deleteByMap(columnMap));
        }
    }

    public boolean remove(Wrapper<T> wrapper) {
        return SqlHelper.delBool(this.baseMapper.delete(wrapper));
    }

    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return SqlHelper.delBool(this.baseMapper.deleteBatchIds(idList));
    }

    public boolean updateById(T entity) {
        return retBool(this.baseMapper.updateById(entity));
    }

    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return retBool(this.baseMapper.update(entity, updateWrapper));
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, 30);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        } else {
            SqlSession batchSqlSession = this.sqlSessionBatch();
            int i = 0;
            String sqlStatement = this.sqlStatement(SqlMethod.UPDATE_BY_ID);

            try {
                for(Iterator var6 = entityList.iterator(); var6.hasNext(); ++i) {
                    T anEntityList = (T) var6.next();
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
                    param.put("et", anEntityList);
                    batchSqlSession.update(sqlStatement, param);
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                }

                batchSqlSession.flushStatements();
                return true;
            } finally {
                this.closeSqlSession(batchSqlSession);
            }
        }
    }

    public T getById(Serializable id) {
        return this.baseMapper.selectById(id);
    }

    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return this.baseMapper.selectBatchIds(idList);
    }

    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return this.baseMapper.selectByMap(columnMap);
    }

    public T getOne(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(this.baseMapper.selectList(queryWrapper));
    }

    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return (Map)SqlHelper.getObject(this.baseMapper.selectMaps(queryWrapper));
    }

    public Object getObj(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(this.baseMapper.selectObjs(queryWrapper));
    }

    public int count(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(this.baseMapper.selectCount(queryWrapper));
    }

    public List<T> list(Wrapper<T> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        queryWrapper = (Wrapper<T>) SqlHelper.fillWrapper(page, queryWrapper);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return this.baseMapper.selectMaps(queryWrapper);
    }

    public List<Object> listObjs(Wrapper<T> queryWrapper) {
        return (List)this.baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        queryWrapper = (Wrapper<T>) SqlHelper.fillWrapper(page, queryWrapper);
        return this.baseMapper.selectMapsPage(page, queryWrapper);
    }
}
