package com.quickex.service.layer.impl;

import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoUserLayerType;
import com.quickex.mapper.layer.KoUserLayerTypeMapper;
import com.quickex.service.layer.IKoUserLayerTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class KoUserLayerTypeServiceImpl extends BaseServiceImpl<KoUserLayerTypeMapper, KoUserLayerType> implements IKoUserLayerTypeService {

}
