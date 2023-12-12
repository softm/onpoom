package com.quickex.service.layer.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.util.StringUtils;
import com.quickex.domain.entity.LayerAndTypeSelect;
import com.quickex.domain.layer.*;
import com.quickex.domain.user.KoRole;
import com.quickex.domain.user.KoUserRole;
import com.quickex.mapper.layer.KoLayerServiceMapper;
import com.quickex.mapper.layer.KoMenuCatalogMapper;
import com.quickex.service.layer.*;
import com.quickex.service.user.IKoRoleService;
import com.quickex.service.user.IKoUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KoMenuCatalogServiceImpl extends ServiceImpl<KoMenuCatalogMapper, KoMenuCatalog> implements IKoMenuCatalogService {

    @Autowired
    private KoMenuCatalogMapper koMenuCatalogMapper;

    @Autowired
    private KoLayerServiceMapper koLayerServiceMapper;

    @OperLog(operType = OperType.SELECT, operDesc = "/koMenuCatalog/MenuCatalog/list")
    public R getPage(PageDomain pageDomain,KoMenuCatalog koMenuCatalog){
        Page<KoMenuCatalog> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoMenuCatalog> query = new QueryWrapper<>();
        if(koMenuCatalog.getName()!=null && !koMenuCatalog.getName().isEmpty()){
            query.like("name", koMenuCatalog.getName());
        }
        IPage<KoMenuCatalog> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/koMenuCatalog/MenuCatalog/get")
    public R getById(KoMenuCatalog koMenuCatalog){
        KoMenuCatalog data = getById(koMenuCatalog.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.ADD, operDesc = "/koMenuCatalog/MenuCatalog/add")
    public R add(KoMenuCatalog koMenuCatalog){

        QueryWrapper<KoLayerService> query = new QueryWrapper<>();
        query.eq("tree_id",koMenuCatalog.getParentId());
        List<KoLayerService> check = koLayerServiceMapper.selectList(query);
        if(check.size()>0){
            //There are already layer services under the node. Cannot create child nodes!
            return R.error();
        }
        save(koMenuCatalog);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/koMenuCatalog/MenuCatalog/edit")
    public R edit(KoMenuCatalog koMenuCatalog){
        updateById(koMenuCatalog);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/koMenuCatalog/MenuCatalog/delete")
    public R deleteIds(List<String> ids){

        //check Child node
        for (int i = 0; i < ids.size(); i++) {
            QueryWrapper<KoMenuCatalog> query = new QueryWrapper<>();
            query.eq("parent_id",ids.get(i));
            int count = this.baseMapper.selectCount(query);
            if(count>0){
                return R.error("the data also contains sub data");
            }
        }

        //check KoLayerService
        for (int i = 0; i < ids.size(); i++) {
            QueryWrapper<KoLayerService> query = new QueryWrapper<>();
            query.eq("tree_id",ids.get(i));
            int count = koLayerServiceMapper.selectCount(query);
            if(count>0){
                return R.error("the data also contains sub data");
            }
        }

        removeByIds(ids);
        return R.success();
    }


    /**
     * Tree root node list (non paged)
     */
    @OperLog(operType = OperType.SELECT, operDesc = "/koMenuCatalog/MenuCatalog/treeRootList")
    public R treeRootList(){
        QueryWrapper<KoMenuCatalog> query = new QueryWrapper<>();
        query.eq("parent_id", "-1");
        query.orderByAsc("sort");
        List<KoMenuCatalog> list = this.baseMapper.selectList(query);
        return R.success(list);
    }

    /**
     * Build tree structure
     * @param koMenuCatalog
     * @return
     */
    @OperLog(operType = OperType.SELECT, operDesc = "/koMenuCatalog/MenuCatalog/treeList")
    @Override
    public R treeList(KoMenuCatalog koMenuCatalog) {

        List<KoMenuCatalog> result = koMenuCatalogMapper.getTreeByParentId(koMenuCatalog.getId(),koMenuCatalog.getName());
        return R.success(result);
//        QueryWrapper<KoMenuCatalog> query = new QueryWrapper<>();
//        if(koMenuCatalog.getId()!=null && !koMenuCatalog.getId().isEmpty()){
//            query.eq("parent_id",koMenuCatalog.getId());
//        }
//        List<KoMenuCatalog> koMenuCatalogs = this.koMenuCatalogMapper.selectList(query);
//        List<KoMenuCatalog> result = buildMenuCatalogTree(koMenuCatalogs);
//        return R.success(result);
    }

    /**
     * Tree structure front end user
     * @param koMenuCatalog
     * @return
     */
    @OperLog(operType = OperType.SELECT, operDesc = "/koMenuCatalog/MenuCatalog/userWebTree")
    public R userWebTree(KoMenuCatalog koMenuCatalog){
//        List<KoMenuCatalog> result = koMenuCatalogMapper.getTreeByParentIdForUserWeb(koMenuCatalog.getId());
        List<KoMenuCatalogUserWebDto> result = koMenuCatalogMapper.getTreeByParentIdForUserWebDto(koMenuCatalog.getId());
        return R.success(result);
    }

    //Manual subset   ----- use this
    public R userWebTree1(KoMenuCatalog koMenuCatalog){
        List<KoMenuCatalogUserWebDto> list = koMenuCatalogMapper.getTreeByParentIdForUserWebDto1(koMenuCatalog.getId());

        List<String> layerIds = getUserLayerIds(koMenuCatalog.getCreateUser());
        buildTree(list,koMenuCatalog.getCreateUser(),layerIds);

        //user type
        KoMenuCatalogUserWebDto collection =new KoMenuCatalogUserWebDto();
        collection.setKey("collection");
        collection.setTitle("사용자");
        collection.setDataType(1);
        collection.setTreeId("collection");
        collection.setChildren(new ArrayList<KoMenuCatalogUserWebDto>());
        if (list.size() == 0) {
            list.add(collection);
        } else {
            list.add(1, collection);
        }

        return R.success(list);
    }

    private void buildTree(List<KoMenuCatalogUserWebDto> list,String createUser,List<String> layerIds){
        for (KoMenuCatalogUserWebDto item:list) {
            if(item.getChildren().size()>0){
                buildTree(item.getChildren(),createUser,layerIds);
            }else{
                if(item.getDataType()==1){
                    List<KoMenuCatalogUserWebDto> childList = koMenuCatalogMapper.getServiceList(item.getKey(),createUser,layerIds);
                    if(childList.size()>0){
                        for (KoMenuCatalogUserWebDto c:childList) {
                            c.setChildren(new ArrayList<>());
                            item.getChildren().add(c);
                        }
                        //item.setChildren(childList);
                    }
                }
            }
        }
    }





    /**
     * Build tree structure
     * @param koMenuCatalogs
     * @return
     */
    private List<KoMenuCatalog> buildMenuCatalogTree(List<KoMenuCatalog> koMenuCatalogs) {
        List<KoMenuCatalog> returnList = new ArrayList<KoMenuCatalog>();
        List<String> tempList = new ArrayList<String>();
        for (KoMenuCatalog dept : koMenuCatalogs) {
            tempList.add(dept.getId());
        }
        for (Iterator<KoMenuCatalog> iterator = koMenuCatalogs.iterator(); iterator.hasNext(); ) {
            KoMenuCatalog dept = iterator.next();
            // If it is a top-level node, traverse all child nodes of the parent node
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(koMenuCatalogs, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = koMenuCatalogs;
        }
        return returnList;
    }

    /**
     * Recursive list
     */
    private void recursionFn(List<KoMenuCatalog> list, KoMenuCatalog t) {
        // Get the list of child nodes
        List<KoMenuCatalog> childList = getChildList(list, t);
        t.setChildren(childList);
        for (KoMenuCatalog tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * Get the list of child nodes
     */
    private List<KoMenuCatalog> getChildList(List<KoMenuCatalog> list, KoMenuCatalog t) {
        List<KoMenuCatalog> tlist = new ArrayList<KoMenuCatalog>();
        Iterator<KoMenuCatalog> it = list.iterator();
        while (it.hasNext()) {
            KoMenuCatalog n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * Determine whether there are child nodes
     */
    private boolean hasChild(List<KoMenuCatalog> list, KoMenuCatalog t) {
        return getChildList(list, t).size() > 0;
    }





    @Autowired
    private IKoUserLayerCollectionService userLayerCollectionService;

    @Autowired
    private IKoUserLayerTypeService userLayerTypeService;


    public R addCollection(KoUserLayerCollection entity) {
        entity.setCreateTime(new Date());
        userLayerCollectionService.save(entity);
        return R.success();
    }

    public R deleteCollection(KoUserLayerCollection entity) {
        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("layer_id",entity.getLayerId());
        map1.put("create_user",entity.getCreateUser());
        userLayerCollectionService.removeByMap(map1);
        return R.success();
    }

    public R listCollection(KoUserLayerCollection entity) {
        List<KoMenuCatalogUserWebDto> childList = koMenuCatalogMapper.getCollectionList(entity.getCreateUser());
        return R.success(childList);
    }

    public R addUserType(KoUserLayerType entity) {
        QueryWrapper<KoUserLayerType> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("type_name",entity.getTypeName());
        queryWrapper.eq("create_user",entity.getCreateUser());
        int count = userLayerTypeService.count(queryWrapper);
        if(count!=0){
            return R.error("name already exists!");
        }
        entity.setCreateTime(new Date());
        userLayerTypeService.save(entity);
        return R.success();
    }

    public R deleteUserType(KoUserLayerType entity) {
        userLayerTypeService.removeById(entity.getId());
        return R.success();
    }

    public R deleteUserType1(KoUserLayerType entity) {
        userLayerTypeService.removeByIds(entity.getIds());
        return R.success();
    }

    @Transactional
    public R rNameUserType(KoUserLayerType entity) {

        KoUserLayerType data =  userLayerTypeService.getById(entity.getId());
        if(data==null){
            R.error("data is null!");
        }
        QueryWrapper<KoUserLayerType> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("type_name",entity.getTypeName());
        queryWrapper.eq("create_user",entity.getCreateUser());
        queryWrapper.ne("id",entity.getId());
        int count = userLayerTypeService.count(queryWrapper);
        if(count!=0){
            return R.error("name already exists!");
        }
        data.setTypeName(entity.getTypeName());
        userLayerTypeService.updateById(data);
        return R.success();
    }

    public R listUserType(KoUserLayerType entity) {
        QueryWrapper<KoUserLayerType> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("create_user",entity.getCreateUser());
        queryWrapper.orderByAsc("create_time");
        List<KoUserLayerType> list= userLayerTypeService.list(queryWrapper);
        return R.success(list);
    }



    public R userCsTree(KoMenuCatalog koMenuCatalog){
        //OLD
        List<KoMenuCatalogUserWebDto> list = koMenuCatalogMapper.getTreeByParentIdForUserWebDto1(koMenuCatalog.getId());
        //NEW
        //List<KoMenuCatalogUserWebDto> list = koMenuCatalogMapper.getTreeByParentIdForUserWebDto11();
        buildCsTree(list);
        return R.success(list);
    }

    private void buildCsTree(List<KoMenuCatalogUserWebDto> list){
        for (KoMenuCatalogUserWebDto item:list) {
            if(item.getChildren().size()>0){
                buildCsTree(item.getChildren());
            }else{
                if(item.getDataType()==1){
                    List<KoMenuCatalogUserWebDto> childList = koMenuCatalogMapper.getCsTreeServiceList(item.getKey());
                    if(childList.size()>0){
                        for (KoMenuCatalogUserWebDto c:childList) {
                            c.setChildren(new ArrayList<>());

                            if(c.getCenterX()==null || c.getCenterX().equals("")){
                                c.setCenterX("127.14847");
                            }
                            if(c.getCenterY()==null || c.getCenterY().equals("")){
                                c.setCenterY("33.3350");
                            }

                            item.getChildren().add(c);
                        }
                        //item.setChildren(childList);
                    }
                }
            }
        }
    }


    public R userWebTreeType(KoMenuCatalog entity){
        List<KoMenuCatalogUserWebDto> list = koMenuCatalogMapper.getTreeByParentIdForUserWebDto1(entity.getId());

        //user type
        KoMenuCatalogUserWebDto node =new KoMenuCatalogUserWebDto();
        node.setKey("collection");
        node.setTitle("사용자");
        node.setDataType(1);
        node.setTreeId("collection");
        node.setChildren(new ArrayList<KoMenuCatalogUserWebDto>());
        if (list.size() == 0) {
            list.add(node);
        } else {
            list.add(1, node);
        }

        return R.success(list);
    }

    public R userWebTreeLayer(KoMenuCatalog entity){
        List<String> layerIds = new ArrayList<>();
        List<KoMenuCatalogUserWebDto> childList = koMenuCatalogMapper.getServiceList(entity.getId(),entity.getCreateUser(),layerIds);
        return R.success(childList);
    }



    @Autowired
    private IKoRoleLayerCatalogService roleLayerCatalogService;

    @Autowired
    private IKoUserRoleService userRoleService;

    @Autowired
    private IKoLayerServiceService layerServiceService;

    @Autowired
    private IKoRoleService roleService;

    public R allAndRoleCheckedLayer(JSONObject par){
        String roleId = par.getString("roleId");
        List<LayerAndTypeSelect> list = koMenuCatalogMapper.getTreeLayerAndTypeSelectRoot();
        this.build_LayerAndTypeSelect_type(list);
        this.build_LayerAndTypeSelect_service(list,roleId);
        return R.success(list);
    }

    private void build_LayerAndTypeSelect_type(List<LayerAndTypeSelect> list){
        for (LayerAndTypeSelect item:list) {
            item.setChildren(koMenuCatalogMapper.getTreeLayerAndTypeSelectChildren(item.getId()));
            if(item.getChildren().size()>0){
                build_LayerAndTypeSelect_type(item.getChildren());
            }
        }
    }

    private void build_LayerAndTypeSelect_service(List<LayerAndTypeSelect> list,String roleId){
        for (LayerAndTypeSelect item:list) {
            if(item.getChildren().size()>0){
                build_LayerAndTypeSelect_service(item.getChildren(),roleId);
            }else{
                if(item.getType()==0){
                    List<LayerAndTypeSelect> childList = koMenuCatalogMapper.getTreeLayerAndTypeSelectService(roleId,item.getId());
                    if(childList.size()>0){
                        for (LayerAndTypeSelect c:childList) {
                            c.setChildren(new ArrayList<>());
                            item.getChildren().add(c);
                        }
                        //item.setChildren(childList);
                    }
                }
            }
        }
    }

    @Transactional
    public R updateRoleLayer(JSONObject par){
        String roleId = par.getString("roleId");
        List<String> layerIds = par.getJSONArray("ids").toJavaList(String.class);

        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("role_id", roleId);
        roleLayerCatalogService.removeByMap(deleteMap);

        for (String id: layerIds) {
            KoRoleLayerCatalog data = new KoRoleLayerCatalog();
            data.setRoleId(roleId);
            data.setCatalogId(id);
            roleLayerCatalogService.save(data);
        }

        return R.success();
    }

    private List<String> getUserLayerIds(String userAccount){
        List<String> ids = new ArrayList<>();
        ids.add("root");
        //user layer
        QueryWrapper<KoLayerService> q1 = new QueryWrapper<>();
        q1.eq("create_user",userAccount);
        List<KoLayerService> list1 = layerServiceService.list(q1);
        for (KoLayerService item: list1) {
            ids.add(item.getId());
        }

        //role layer
        QueryWrapper<KoUserRole> q2 = new QueryWrapper<>();
        q2.eq("user_id",userAccount);
        List<KoUserRole> list2 = userRoleService.list(q2);
        for (KoUserRole item: list2) {
            QueryWrapper<KoRoleLayerCatalog> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getRoleId());
            List<KoRoleLayerCatalog> list3 = roleLayerCatalogService.list(q3);
            for (KoRoleLayerCatalog it: list3) {
                ids.add(it.getCatalogId());
            }
        }

        //default role layer
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

        //duplicate removal
        ids = ids.stream().distinct().collect(Collectors.toList());

        //if superadmin
        QueryWrapper<KoRole> queryWrapperSuperRole = new QueryWrapper<>();
        queryWrapperSuperRole.eq("role_type","SuperAdmin");
        List<KoRole> superRoles = roleService.list(queryWrapperSuperRole);

        List<String> haveSuperIds = new ArrayList<>();
        for (KoRole item:superRoles) {
            for (KoUserRole item1:list2) {
                if(item.getId().equals(item1.getRoleId())){
                    haveSuperIds.add(item.getId());
                }
            }
        }

        if(haveSuperIds.size()>0){
            ids.clear();
        }

        return ids;
    }

}
