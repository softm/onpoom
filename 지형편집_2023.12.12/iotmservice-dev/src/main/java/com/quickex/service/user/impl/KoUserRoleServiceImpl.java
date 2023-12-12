package com.quickex.service.user.impl;

import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoUserRole;
import com.quickex.mapper.user.KoUserRoleMapper;
import com.quickex.service.user.IKoUserRoleService;
import org.springframework.stereotype.Service;


@Service
public class KoUserRoleServiceImpl extends BaseServiceImpl<KoUserRoleMapper, KoUserRole> implements IKoUserRoleService {

}
