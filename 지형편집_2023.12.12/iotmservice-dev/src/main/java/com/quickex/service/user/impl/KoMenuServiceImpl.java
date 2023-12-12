package com.quickex.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.*;
import com.quickex.mapper.user.KoMenuMapper;
import com.quickex.service.user.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class KoMenuServiceImpl extends BaseServiceImpl<KoMenuMapper, KoMenu> implements IKoMenuService {
    @Autowired
    private IKoUserService userService;

    @Autowired
    private IKoUserMenuService userMenuService;

    @Autowired
    private IKoRoleMenuService roleMenuService;

    @Autowired
    private IKoMenuSortService menuSortService;

    @Autowired
    private IKoRoleService roleService;


    public R add(KoMenu entity){
        this.save(entity);
        return R.success();
    }
    public R delete(KoMenu entity){
        QueryWrapper<KoMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",entity.getId());
        int count = this.count(queryWrapper);
        if(count!=0){
            return R.error("It also contains subclasses, which cannot be deleted !");
        }
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoMenu entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoMenu entity){
        KoMenu data = this.getById(entity.getId());
        return R.success(data);
    }

    public R allMenu(KoMenu entity){
        List<KoMenu> list = this.baseMapper.allMenu(entity);
        return R.success(list);
    }



    public R allAndRoleChecked(KoMenu entity){
        List<KoMenu> list = this.baseMapper.allAndRoleChecked(entity);


        QueryWrapper<KoMenuSort> query = new QueryWrapper<>();
        query.eq("role_id",entity.getRoleId());
        int count = menuSortService.count(query);
        if(count>0){
            for (KoMenu item: list) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",entity.getRoleId());
                sortQueryWrapper.eq("menu_id",item.getId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            //
            list.sort(Comparator.comparing(KoMenu::getSort,Comparator.nullsLast(Integer::compareTo)));
            R.success(list);
        }

        return R.success(list);
    }

    public R allAndUserChecked(KoMenu entity){
        List<KoMenu> list = this.baseMapper.allAndUserChecked(entity);


        QueryWrapper<KoMenuSort> query = new QueryWrapper<>();
        query.eq("user_id",entity.getAccount());
        int count = menuSortService.count(query);
        if(count>0){
            for (KoMenu item: list) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("user_id",entity.getAccount());
                sortQueryWrapper.eq("menu_id",item.getId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            //
            list.sort(Comparator.comparing(KoMenu::getSort,Comparator.nullsLast(Integer::compareTo)));
            R.success(list);
        }

        return R.success(list);
    }




    public R updateRoleMenu(KoMenu entity){
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("role_id", entity.getRoleId());
        deleteMap.put("sys_type", entity.getSysType());
        roleMenuService.removeByMap(deleteMap);

        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("role_id", entity.getRoleId());
        deleteMap1.put("sys_type", null);
        roleMenuService.removeByMap(deleteMap1);

        for (String id: entity.getIds()) {
            KoRoleMenu data = new KoRoleMenu();
            data.setRoleId(entity.getRoleId());
            data.setMenuId(id);
            data.setSysType(entity.getSysType());
            roleMenuService.save(data);
        }
        return R.success();
    }
    public R updateUserMenu(KoMenu entity){
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("user_id", entity.getAccount());
        deleteMap.put("sys_type", entity.getSysType());
        userMenuService.removeByMap(deleteMap);

        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("user_id", entity.getAccount());
        deleteMap1.put("sys_type", null);
        userMenuService.removeByMap(deleteMap1);

        for (String id: entity.getIds()) {
            KoUserMenu data = new KoUserMenu();
            data.setUserId(entity.getAccount());
            data.setMenuId(id);
            data.setSysType(entity.getSysType());
            userMenuService.save(data);
        }
        return R.success();
    }


    @Transactional
    public R updateRoleMenuSort(KoMenuSort entity){

        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("role_id", entity.getRoleId());
        deleteMap.put("sys_type", entity.getSysType());
        menuSortService.removeByMap(deleteMap);

        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("role_id", entity.getRoleId());
        deleteMap1.put("sys_type", null);
        menuSortService.removeByMap(deleteMap1);

        for (KoMenuSort item: entity.getList()) {
            item.setSysType(entity.getSysType());
            menuSortService.save(item);
        }

        UpdateWrapper<KoRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",entity.getRoleId());
        KoRole user = new KoRole();
        user.setSortUpdateTime(new Date());
        roleService.update(user, updateWrapper);

        return R.success();
    }

    @Transactional
    public R updateUserMenuSort(KoMenuSort entity){
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("user_id", entity.getUserId());
        deleteMap.put("sys_type", entity.getSysType());
        menuSortService.removeByMap(deleteMap);

        Map<String, Object> deleteMap1 = new HashMap<>();
        deleteMap1.put("user_id", entity.getUserId());
        deleteMap1.put("sys_type", null);
        menuSortService.removeByMap(deleteMap1);

        for (KoMenuSort item: entity.getList()) {
            item.setSysType(entity.getSysType());
            menuSortService.save(item);
        }
        return R.success();
    }

    public List<KoMenu> getAllPatentAndThis(String menuId){
        return this.baseMapper.getAllPatentAndThis(menuId);
    }


    public R checkRouteName(KoMenu entity) {
        if (entity.getMenuData3() == null || entity.getMenuData3().equals("")) {
            JSONObject result = new JSONObject();
            result.put("check", true);
            return R.success(result);
        }

        Boolean check = true;
        if (entity.getId() == null || entity.getId().equals("")) {
            QueryWrapper<KoMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("menu_data3", entity.getMenuData3());
            queryWrapper.eq("sys_type", 2);
            int count = this.count(queryWrapper);
            if (count > 0) {
                check = false;
            }
        } else {
            QueryWrapper<KoMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("menu_data3", entity.getMenuData3());
            queryWrapper.eq("sys_type", 2);
            queryWrapper.ne("id", entity.getId());
            int count = this.count(queryWrapper);
            if (count > 0) {
                check = false;
            }
        }
        JSONObject result = new JSONObject();
        result.put("check", check);
        return R.success(result);
    }
}
