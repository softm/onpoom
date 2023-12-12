package com.quickex.service.mapfile.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.mapfile.Dto.KoMapFileLabelRelationDto;
import com.quickex.domain.mapfile.Dto.KoMapFileLabelRelationUpdateDto;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.mapfile.KoMapFileLabelRelation;
import com.quickex.mapper.mapfile.KoMapFileLabelRelationMapper;
import com.quickex.service.mapfile.IKoMapFileLabelRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class KoMapFileLabelRelationServiceImpl extends BaseServiceImpl<KoMapFileLabelRelationMapper, KoMapFileLabelRelation> implements IKoMapFileLabelRelationService {

    public R list(KoMapFile entity){
        QueryWrapper<KoMapFileLabelRelation> query = new QueryWrapper<>();
        query.eq("file_id",entity.getId());
        List<KoMapFileLabelRelation> list = this.baseMapper.selectList(query);
        return R.success(list);
    }

    public R add(KoMapFileLabelRelation entity){
        save(entity);
        return R.success();
    }

    public R delete(KoMapFileLabelRelation entity){
        entity.setCreateTime(new Date());
        removeById(entity.getId());
        return R.success();
    }

    public R get(KoMapFileLabelRelation entity){
        entity = getById(entity.getId());
        return R.success(entity);
    }

    //----  Not even up there


    /**
     * All labels for file binding
     * @param entity
     * @return
     */
    @OperLog(operType = OperType.SELECT, operDesc = "/api/ko-map-file-label-relation/listall")
    public R listall(KoMapFile entity) {
        List<KoMapFileLabelRelationDto> list = this.baseMapper.getLables(entity.getId());
        return R.success(list);
    }

    /**
     * update All labels for file binding
     * @param entity
     * @return
     */
    @OperLog(operType = OperType.EDIT, operDesc = "/api/ko-map-file-label-relation/updateall")
    public R updateall(KoMapFileLabelRelationUpdateDto par){


        Map<String,Object> map =new HashMap<>();
        map.put("file_id",par.getFileId());
        removeByMap(map);

        if(par.getList().size()==0){
            return R.success();
        }

        for (KoMapFileLabelRelationDto item: par.getList()) {

            KoMapFileLabelRelation data =new KoMapFileLabelRelation();
            data.setFileId(par.getFileId());
            data.setCreateTime(new Date());
            data.setCreateUser(par.getCreateUser());
            data.setLabel(item.getId());
            save(data);
        }
        return R.success();
    }
}
