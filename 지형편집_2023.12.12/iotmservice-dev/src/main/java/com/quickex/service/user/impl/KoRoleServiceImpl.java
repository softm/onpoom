package com.quickex.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoOrganization;
import com.quickex.domain.user.KoRole;
import com.quickex.domain.user.KoUser;
import com.quickex.domain.user.KoUserRole;
import com.quickex.mapper.layer.KoProvinceMapper;
import com.quickex.mapper.user.KoRoleMapper;
import com.quickex.service.user.IKoRoleMenuService;
import com.quickex.service.user.IKoRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.service.user.IKoUserRoleService;
import com.quickex.service.user.IKoUserService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class KoRoleServiceImpl extends BaseServiceImpl<KoRoleMapper, KoRole> implements IKoRoleService {

    @Autowired
    private IKoUserService userService;

    @Autowired
    private IKoUserRoleService userRoleService;

    @Autowired
    private IKoRoleMenuService roleMenuService;


    public R add(KoRole entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R delete(KoRole entity){
//        QueryWrapper<KoUserRole> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id",entity.getId());
//        queryWrapper.eq("user_id",entity.getId());
//        int count = userRoleService.count(queryWrapper);
//        if(count!=0){
//            return R.error("users using this role cannot be deleted");
//        }
//        this.removeById(entity.getId());
        this.removeById(entity.getId());
        this.baseMapper.deleteUserRole();
        this.baseMapper.deleteRoleMenu();
        return R.success();
    }

    public R edit(KoRole entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoRole entity){
        KoRole data = this.getById(entity.getId());
        return R.success(data);
    }

    public R list(KoRole entity){
//        QueryWrapper<KoRole> queryWrapper = new QueryWrapper<>();
//        if(entity.getRoleType()!=null && !entity.getRoleType().equals("")){
//            queryWrapper.eq("role_type",entity.getRoleType());
//        }
//        if(entity.getRoleName()!=null && !entity.getRoleName().equals("")){
//            queryWrapper.like("role_name",entity.getRoleName());
//        }
//        List<KoRole> list = this.list(queryWrapper);
        List<KoRole> list = this.baseMapper.list(entity);
        return R.success(list);
    }

    public R userList(KoRole entity){
        List<KoRole> list = this.baseMapper.userList(entity);
        return R.success(list);
    }

    @Transactional
    public R updateUserRole(KoRole entity){

        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("user_id", entity.getAccount());
        userRoleService.removeByMap(deleteMap);

        for (String id: entity.getIds()) {
            KoUserRole data = new KoUserRole();
            data.setUserId(entity.getAccount());
            data.setRoleId(id);
            userRoleService.save(data);
        }
        return R.success();
    }
}
