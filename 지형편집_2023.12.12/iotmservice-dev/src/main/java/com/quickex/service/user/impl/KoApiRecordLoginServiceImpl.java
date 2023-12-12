package com.quickex.service.user.impl;

import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoApiRecordLogin;
import com.quickex.mapper.user.KoApiRecordLoginMapper;
import com.quickex.service.user.IKoApiRecordLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class KoApiRecordLoginServiceImpl extends BaseServiceImpl<KoApiRecordLoginMapper, KoApiRecordLogin> implements IKoApiRecordLoginService {

}
