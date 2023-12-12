package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.*;
import com.quickex.domain.user.KoRole;
import com.quickex.domain.user.KoRoleMenu;
import com.quickex.domain.user.KoUserRole;
import com.quickex.mapper.layer.KoLayerCatalogMapper;
import com.quickex.mapper.layer.KoLayerServiceMapper;
import com.quickex.service.layer.IKoLayerCatalogService;
import com.quickex.service.layer.IKoRoleLayerCatalogService;
import com.quickex.service.layer.IKoUserLayerCatalogService;
import com.quickex.service.user.IKoRoleService;
import com.quickex.service.user.IKoUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class KoLayerCatalogServiceImpl extends BaseServiceImpl<KoLayerCatalogMapper, KoLayerCatalog> implements IKoLayerCatalogService {

    @Autowired
    private KoLayerServiceMapper koLayerServiceMapper;

    @Autowired
    private IKoRoleLayerCatalogService roleLayerCatalogService;

    @Autowired
    private IKoUserLayerCatalogService userLayerCatalogService;

    @Autowired
    private IKoUserRoleService userRoleService;

    @Autowired
    private IKoRoleService roleService;


    public R add(KoLayerCatalog entity){
        entity.setCreateTime(new Date());
        this.save(entity);
        return R.success();
    }

    public R delete(KoLayerCatalog entity) {
        //check child node
        QueryWrapper<KoLayerCatalog> query = new QueryWrapper<>();
        query.eq("parent_id", entity.getId());
        int count = this.baseMapper.selectCount(query);
        if (count > 0) {
            return R.error("the data also contains sub data");
        }
        //check KoLayerService
        QueryWrapper<KoLayerService> query1 = new QueryWrapper<>();
        query1.eq("tree_id", entity.getId());
        int count1 = koLayerServiceMapper.selectCount(query1);
        if (count1 > 0) {
            return R.error("the data also contains sub data");
        }
        this.removeById(entity.getId());
        return R.success();
    }

    public R edit(KoLayerCatalog entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoLayerCatalog entity){
        KoLayerCatalog data = this.getById(entity.getId());
        return R.success(data);
    }

    public R list(KoLayerCatalog entity){
        QueryWrapper<KoLayerCatalog> query = new QueryWrapper<>();
        query.eq("parent_id", entity.getParentId());
        query.eq("catalog_type", entity.getCatalogType());
        List<KoLayerCatalog> list = this.list(query);
        return R.success(list);
    }

    public R allTree(KoLayerCatalog entity){
        List<KoLayerCatalog> list = this.baseMapper.allTree(entity.getName());
        return R.success(list);
    }

    public R allAndRoleChecked(KoLayerCatalog entity){
        List<KoLayerCatalog> list = this.baseMapper.allAndRoleChecked(entity.getRoleId());
        return R.success(list);
    }

    public R allAndUserChecked(KoLayerCatalog entity){
        List<KoLayerCatalog> list = this.baseMapper.allAndUserChecked(entity.getCreateUser());
        return R.success(list);
    }

    public R updateRoleCatalog(KoLayerCatalog entity){
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("role_id", entity.getRoleId());
        roleLayerCatalogService.removeByMap(deleteMap);

        for (String id: entity.getIds()) {
            KoRoleLayerCatalog data = new KoRoleLayerCatalog();
            data.setRoleId(entity.getRoleId());
            data.setCatalogId(id);
            roleLayerCatalogService.save(data);
        }
        return R.success();
    }

    public R updateUserCatalog(KoLayerCatalog entity){
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("user_id", entity.getRoleId());
        userLayerCatalogService.removeByMap(deleteMap);

        for (String id: entity.getIds()) {
            KoUserLayerCatalog data = new KoUserLayerCatalog();
            data.setUserId(entity.getCreateUser());
            data.setCatalogId(id);
            userLayerCatalogService.save(data);
        }
        return R.success();
    }




    public R userWebTree(KoLayerCatalog entity){

        //entity.setCreateUser(entity.getUserAccount()); //****

        List<String> ids = this.getAllTypeId(entity.getCreateUser());

        List<KoMenuCatalogUserWebDto> list = this.baseMapper.getTypeList("-1",1, new ArrayList<>());

        this.buildUserWebTree(list,ids,entity.getCreateUser());

        //user type
        KoMenuCatalogUserWebDto collection =new KoMenuCatalogUserWebDto();
        collection.setKey("collection");
        collection.setTitle("사용자");
        collection.setDataType(1);
        collection.setTreeId("collection");
        collection.setChildren(new ArrayList<KoMenuCatalogUserWebDto>());
        collection.setCatalogType(1);
        if (list.size() == 0) {
            list.add(collection);
        } else {
            list.add(1, collection);
        }

        return R.success(list);
    }

    private List<String> getAllTypeId(String account){
        List<String> ids = new ArrayList<>();
//        ids.add("1529760943982919681");
//        ids.add("1529762037517688833");
//        ids.add("23433");

        QueryWrapper<KoUserLayerCatalog> q1 = new QueryWrapper<>();
        q1.eq("user_id",account);
        List<KoUserLayerCatalog> list1 = userLayerCatalogService.list(q1);
        for (KoUserLayerCatalog item: list1) {
            ids.add(item.getCatalogId());
        }

        QueryWrapper<KoUserRole> q2 = new QueryWrapper<>();
        q2.eq("user_id",account);
        List<KoUserRole> list2 = userRoleService.list(q2);
        for (KoUserRole item: list2) {
            QueryWrapper<KoRoleLayerCatalog> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getRoleId());
            List<KoRoleLayerCatalog> list3 = roleLayerCatalogService.list(q3);
            for (KoRoleLayerCatalog it: list3) {
                ids.add(it.getCatalogId());
            }
        }

        //add default role
        QueryWrapper<KoRole> queryWrapperRole = new QueryWrapper<>();
        queryWrapperRole.eq("role_type","Default");
        List<KoRole> defeuleRoles = roleService.list(queryWrapperRole);
        for (KoRole item: defeuleRoles) {
            QueryWrapper<KoRoleLayerCatalog> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getId());
            List<KoRoleLayerCatalog> list3 = roleLayerCatalogService.list(q3);
            for (KoRoleLayerCatalog it: list3) {
                ids.add(it.getCatalogId());
            }
        }

        if(ids.size()==0){
            ids.add("1529760943xxx982919681");
        }
        return ids;
    }

    private void buildUserWebTree(List<KoMenuCatalogUserWebDto> list, List<String> ids, String userId) {
        for (KoMenuCatalogUserWebDto item : list) {
            List<KoMenuCatalogUserWebDto> childrenList = new ArrayList<>();

            if (item.getCatalogType() == 1) {
                childrenList = this.baseMapper.getTypeList(item.getTreeId(),2, new ArrayList<>()); //samll type
            }
            if (item.getCatalogType() == 2) {
                childrenList = this.baseMapper.getTypeList(item.getTreeId(),3, ids);  //layer
            }
            if (item.getCatalogType() == 3) {
                childrenList = baseMapper.getServiceList(item.getKey(), userId);   //layer path
            }

            item.setChildren(childrenList);
            if (item.getChildren().size() > 0) {
                buildUserWebTree(item.getChildren(), ids, userId);
            }
        }
    }

}
