package com.quickex.service.user.impl;

import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoUserMenu;
import com.quickex.mapper.user.KoUserMenuMapper;
import com.quickex.service.user.IKoUserMenuService;
import org.springframework.stereotype.Service;


@Service
public class KoUserMenuServiceImpl extends BaseServiceImpl<KoUserMenuMapper, KoUserMenu> implements IKoUserMenuService {

}
