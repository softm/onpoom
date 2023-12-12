package com.quickex.service.systable.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.systable.KoTableIndexes;
import com.quickex.mapper.systable.KoTableIndexesMapper;
import com.quickex.service.systable.IKoTableIndexesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-01-10
 */
@Service
public class KoTableIndexesServiceImpl extends BaseServiceImpl<KoTableIndexesMapper, KoTableIndexes> implements IKoTableIndexesService {

    @OperLog(operType = OperType.ADD, operDesc = "/api/ko-table-indexes/add")
    public R add(KoTableIndexes entity){
        this.save(entity);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/ko-table-indexes/delete")
    public R delete(KoTableIndexes entity){
        this.removeById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/ko-table-indexes/edit")
    public R edit(KoTableIndexes entity){
        this.updateById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-indexes/get")
    public R get(KoTableIndexes entity){
        KoTableIndexes data = this.getById(entity.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-indexes/list")
    public R list(KoTableIndexes entity){
        QueryWrapper<KoTableIndexes> query = new QueryWrapper<>();
        query.eq("table_id",entity.getTableId());
        List<KoTableIndexes> list = this.list(query);
        return R.success(list);
    }
}
