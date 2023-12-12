package com.quickex.service.systable.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.systable.KoTableField;
import com.quickex.mapper.systable.KoTableFieldMapper;
import com.quickex.service.systable.IKoTableFieldService;
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
public class KoTableFieldServiceImpl extends BaseServiceImpl<KoTableFieldMapper, KoTableField> implements IKoTableFieldService {

    @OperLog(operType = OperType.ADD, operDesc = "/api/ko-table-field/add")
    public R add(KoTableField entity){
        this.save(entity);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/ko-table-field/delete")
    public R delete(KoTableField entity){
        this.removeById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/ko-table-field/edit")
    public R edit(KoTableField entity){
        this.updateById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-field/get")
    public R get(KoTableField entity){
        KoTableField data = this.getById(entity.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-field/list")
    public R list(KoTableField entity){
        QueryWrapper<KoTableField> query = new QueryWrapper<>();
        query.eq("table_id",entity.getTableId());
        List<KoTableField> list = this.list(query);
        return R.success(list);
    }



}
