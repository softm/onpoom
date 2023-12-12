package com.quickex.service.mapfile;

import com.quickex.core.result.R;
import com.quickex.core.service.IBaseService;
import com.quickex.domain.mapfile.Dto.KoMapFileLabelRelationDto;
import com.quickex.domain.mapfile.Dto.KoMapFileLabelRelationUpdateDto;
import com.quickex.domain.mapfile.KoMapFile;
import com.quickex.domain.mapfile.KoMapFileLabelRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IKoMapFileLabelRelationService extends IBaseService<KoMapFileLabelRelation> {

    R list(KoMapFile entity);

    R add(KoMapFileLabelRelation entity);

    R delete(KoMapFileLabelRelation entity);

    R listall(@RequestBody KoMapFile entity);

    R updateall(KoMapFileLabelRelationUpdateDto par);
}
