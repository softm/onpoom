package com.quickex.service.systable.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.systable.KoTableForeignkey;
import com.quickex.mapper.systable.KoTableForeignkeyMapper;
import com.quickex.service.systable.IKoTableForeignkeyService;
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
public class KoTableForeignkeyServiceImpl extends BaseServiceImpl<KoTableForeignkeyMapper, KoTableForeignkey> implements IKoTableForeignkeyService {

    @OperLog(operType = OperType.ADD, operDesc = "/api/ko-table-foreignkey/add")
    public R add(KoTableForeignkey entity){
        this.save(entity);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/ko-table-foreignkey/delete")
    public R delete(KoTableForeignkey entity){
        this.removeById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/ko-table-foreignkey/edit")
    public R edit(KoTableForeignkey entity){
        this.updateById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-foreignkey/get")
    public R get(KoTableForeignkey entity){
        KoTableForeignkey data = this.getById(entity.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-foreignkey/list")
    public R list(KoTableForeignkey entity){
        QueryWrapper<KoTableForeignkey> query = new QueryWrapper<>();
        query.eq("table_id",entity.getTableId());
        List<KoTableForeignkey> list = this.list(query);
        return R.success(list);
    }
}
