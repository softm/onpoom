package com.quickex.service.systable.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.systable.KoTableIndexesDetail;
import com.quickex.mapper.systable.KoTableIndexesDetailMapper;
import com.quickex.service.systable.IKoTableIndexesDetailService;
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
public class KoTableIndexesDetailServiceImpl extends BaseServiceImpl<KoTableIndexesDetailMapper, KoTableIndexesDetail> implements IKoTableIndexesDetailService {

    @OperLog(operType = OperType.ADD, operDesc = "/api/ko-table-indexes-detail/add")
    public R add(KoTableIndexesDetail entity){
        this.save(entity);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/ko-table-indexes-detail/delete")
    public R delete(KoTableIndexesDetail entity){
        this.removeById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/ko-table-indexes-detail/edit")
    public R edit(KoTableIndexesDetail entity){
        this.updateById(entity);
        return R.success();
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-indexes-detail/get")
    public R get(KoTableIndexesDetail entity){
        KoTableIndexesDetail data = this.getById(entity.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-table-indexes-detail/list")
    public R list(KoTableIndexesDetail entity){
        QueryWrapper<KoTableIndexesDetail> query = new QueryWrapper<>();
        query.eq("table_id",entity.getTableId());
        query.eq("indexes_id",entity.getIndexesId());
        List<KoTableIndexesDetail> list = this.list(query);
        return R.success(list);
    }
}
