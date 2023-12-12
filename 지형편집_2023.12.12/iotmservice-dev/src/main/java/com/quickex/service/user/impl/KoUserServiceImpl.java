package com.quickex.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.page.PageDomain;
import com.quickex.core.redis.RedisCache;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoMenuCatalog;
import com.quickex.domain.user.*;
import com.quickex.mapper.user.KoUserMapper;
import com.quickex.service.user.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class KoUserServiceImpl extends BaseServiceImpl<KoUserMapper, KoUser> implements IKoUserService {

    @Autowired
    private IKoUserMenuService userMenuService;

    @Autowired
    private IKoRoleService roleService;

    @Autowired
    private IKoUserRoleService userRoleService;

    @Autowired
    private IKoRoleMenuService roleMenuService;

    @Autowired
    private IKoMenuSortService menuSortService;

    @Autowired
    private IKoMenuService menuService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IKoApiRecordLoginService apiRecordLoginService;

    @Autowired
    private HttpServletRequest request;


    public R editPwd(KoUser entity){
        UpdateWrapper<KoUser> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("account",entity.getAccount());
        KoUser user = new KoUser();
        user.setPassword(MD5Utils.md5(entity.getPassword()));
        this.update(user,userUpdateWrapper);
        return R.success();
    }

    public R add(KoUser entity) {
        KoUser check = this.getById(entity.getAccount());
        if (check != null) {
            JSONObject result = new JSONObject();
            result.put("isExistence", true);
            return R.success(result);
        }

        Integer check1 = this.baseMapper.checkUser(entity.getAccount());
        if (check1 != 0) {
            JSONObject result = new JSONObject();
            result.put("isExistence", true);
            return R.success(result);
        }

        entity.setCreateTime(new Date());
        entity.setPassword(MD5Utils.md5(entity.getPassword()));
        this.save(entity);
        JSONObject result = new JSONObject();
        result.put("isExistence", false);
        return R.success(result);
    }

    public R delete(KoUser entity) {
        if (entity.getAccount().equals("admin") || entity.getAccount().equals("LX_admin")) {
            return R.error("the system administrator account [admin,LX_admin] cannot be deleted !");
        }
        this.removeById(entity.getAccount());
        this.baseMapper.deleteUserRole();
        this.baseMapper.deleteUserMenu();
        return R.success();
    }

    public R edit(KoUser entity){
        this.updateById(entity);
        return R.success();
    }

    public R get(KoUser entity){
        //KoUser data = this.getById(entity.getAccount());
        KoUser data = this.baseMapper.userInfo(entity.getAccount());
        return R.success(data);
    }

    public List<KoUser> allUser(){
        List<KoUser> list =this.baseMapper.allUser();
        return list;
    }

    public R page(PageDomain pageDomain,KoUser entity){

        Page<KoUser> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<KoUser> list =this.baseMapper.userPage(page,entity);

        IPage<KoUser> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }

    @Transactional
    public R mapLogin(KoUser entity){
        KoUser user = this.baseMapper.userInfo(entity.getAccount());
        if (user == null) {
            return R.error("incorrect account or password!");
        }
        if (user.getUserState() == null || user.getUserState() == 1) {
            return R.error("account disabled!");
        }
        boolean login = MD5Utils.verify(entity.getPassword(), user.getPassword());
        if (login == false) {
            return R.error("incorrect account or password!");
        }

        String token = this.saveLoginToken(entity.getAccount());

       // List<String> ids = this.getAllMenuId(user.getAccount());
       // List<MapMenuResult> menuList = this.baseMapper.selectMapRootMenuList(ids, 2);
       // this.buildMapTree(menuList, ids);
        //this.menuMapSort(menuList, user);

        //save login log
        KoApiRecordLogin LOG = new  KoApiRecordLogin();
        LOG.setId(System.currentTimeMillis());
        LOG.setAccount(entity.getAccount());
        LOG.setIp(this.getIpAddr(request));
        LOG.setApi(request.getServletPath());
        LOG.setTime(new Date());
        LOG.setTimes(null);
        apiRecordLoginService.save(LOG);


        JSONObject result = new JSONObject();
        result.put("userInfo", user);
        //result.put("menuInfo", menuList);
        result.put("token", token);

        return R.success(result);
    }

    public R adminLogin(KoUser entity) {
        KoUser user = this.baseMapper.userInfo(entity.getAccount());
        if (user == null) {
            return R.error("incorrect account or password!");
        }
        if (user.getUserState() == null || user.getUserState() == 1) {
            return R.error("account disabled!");
        }
        boolean login = MD5Utils.verify(entity.getPassword(), user.getPassword());
        if (login == false) {
            return R.error("incorrect account or password!");
        }

        String token = this.saveLoginToken(entity.getAccount());

        List<String> ids = this.getAllMenuId(user.getAccount());
        List<KoMenu> menuList = this.baseMapper.selectRootMenuList(ids, 1);
        this.buildTree(menuList, ids);
        this.menuSort(menuList, user);

        JSONObject result = new JSONObject();
        result.put("userInfo", user);
        result.put("menuInfo", menuList);
        result.put("token", token);


        QueryWrapper<KoMenu> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("sys_type",1);
        List<KoMenu> allMenu = menuService.list(queryWrapper);

        result.put("allMenu", allMenu);

        return R.success(result);
    }

    private void menuSort(List<KoMenu> menuList, KoUser user) {

        QueryWrapper<KoMenuSort> query = new QueryWrapper<>();
        query.eq("user_id",user.getAccount());
        int count = menuSortService.count(query);

        if(count>0){
            for (KoMenu item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("user_id",user.getAccount());
                sortQueryWrapper.eq("menu_id",item.getId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(KoMenu::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }
        String roleId = "";

        QueryWrapper<KoRole> query1 = new QueryWrapper<>();
        query1.eq("role_type","Admin");
        query1.orderByDesc("sort_update_time");
        List<KoRole> adminRoleList = roleService.list(query1);
        if(adminRoleList.size()>0){
            roleId = adminRoleList.get(0).getId();
            for (KoMenu item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",roleId);
                sortQueryWrapper.eq("menu_id",item.getId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(KoMenu::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }

        QueryWrapper<KoRole> query2 = new QueryWrapper<>();
        query2.eq("role_type","Manager");
        query2.orderByDesc("sort_update_time");
        List<KoRole> managerRoleList = roleService.list(query2);
        if(managerRoleList.size()>0){
            roleId = managerRoleList.get(0).getId();
            for (KoMenu item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",roleId);
                sortQueryWrapper.eq("menu_id",item.getId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(KoMenu::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }

        QueryWrapper<KoRole> query3 = new QueryWrapper<>();
        query3.eq("role_type","User");
        query3.orderByDesc("sort_update_time");
        List<KoRole> userRoleList = roleService.list(query3);
        if(userRoleList.size()>0){
            roleId = userRoleList.get(0).getId();
            for (KoMenu item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",roleId);
                sortQueryWrapper.eq("menu_id",item.getId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(KoMenu::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }

    }

    private List<String> getAllMenuId(String account){
        List<String> ids = new ArrayList<>();
        ids.add("roles");
//        ids.add("1529760943982919681");
//        ids.add("1529762037517688833");
//        ids.add("23433");



        QueryWrapper<KoUserMenu> q1 = new QueryWrapper<>();
        q1.eq("user_id",account);
        List<KoUserMenu> list1 = userMenuService.list(q1);
        for (KoUserMenu item: list1) {
            ids.add(item.getMenuId());
        }

        QueryWrapper<KoUserRole> q2 = new QueryWrapper<>();
        q2.eq("user_id",account);
        List<KoUserRole> list2 = userRoleService.list(q2);
        for (KoUserRole item: list2) {

            QueryWrapper<KoRoleMenu> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getRoleId());
            List<KoRoleMenu> list3 = roleMenuService.list(q3);
            for (KoRoleMenu it: list3) {
                ids.add(it.getMenuId());
            }

        }

        //add default role
        QueryWrapper<KoRole> queryWrapperRole = new QueryWrapper<>();
        queryWrapperRole.eq("role_type","Default");
        List<KoRole> defeuleRoles = roleService.list(queryWrapperRole);
        for (KoRole item: defeuleRoles) {
            QueryWrapper<KoRoleMenu> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getId());
            List<KoRoleMenu> list3 = roleMenuService.list(q3);
            for (KoRoleMenu it: list3) {
                ids.add(it.getMenuId());
            }
        }
        return ids;
    }

    private void buildTree(List<KoMenu> list, List<String> ids) {
        for (KoMenu item : list) {
            List<KoMenu> childrenList = this.baseMapper.selectChildrenMenuList(ids, item.getId());
            item.setChildren(childrenList);
            if (item.getChildren().size() > 0) {
                buildTree(item.getChildren(), ids);
            }
        }
    }

    private void buildMapTree(List<MapMenuResult> list, List<String> ids) {
        for (MapMenuResult item : list) {
            List<MapMenuResult> childrenList = this.baseMapper.selectMapChildrenMenuList(ids, item.getMenuId());
            item.setChildren(childrenList);
            if (item.getChildren().size() > 0) {
                buildMapTree(item.getChildren(), ids);
            }
        }
    }

    private void menuMapSort(List<MapMenuResult> menuList, KoUser user) {

        QueryWrapper<KoMenuSort> query = new QueryWrapper<>();
        query.eq("user_id",user.getAccount());
        int count = menuSortService.count(query);

        if(count>0){
            for (MapMenuResult item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("user_id",user.getAccount());
                sortQueryWrapper.eq("menu_id",item.getMenuId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(MapMenuResult::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }
        String roleId = "";

        QueryWrapper<KoRole> query1 = new QueryWrapper<>();
        query1.eq("role_type","Admin");
        query1.orderByDesc("sort_update_time");
        List<KoRole> adminRoleList = roleService.list(query1);
        if(adminRoleList.size()>0){
            roleId = adminRoleList.get(0).getId();
            for (MapMenuResult item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",roleId);
                sortQueryWrapper.eq("menu_id",item.getMenuId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(MapMenuResult::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }

        QueryWrapper<KoRole> query2 = new QueryWrapper<>();
        query2.eq("role_type","Manager");
        query2.orderByDesc("sort_update_time");
        List<KoRole> managerRoleList = roleService.list(query2);
        if(managerRoleList.size()>0){
            roleId = managerRoleList.get(0).getId();
            for (MapMenuResult item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",roleId);
                sortQueryWrapper.eq("menu_id",item.getMenuId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(MapMenuResult::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }


        QueryWrapper<KoRole> query3 = new QueryWrapper<>();
        query3.eq("role_type","User");
        query3.orderByDesc("sort_update_time");
        List<KoRole> userRoleList = roleService.list(query3);
        if(userRoleList.size()>0){
            roleId = userRoleList.get(0).getId();
            for (MapMenuResult item: menuList) {
                QueryWrapper<KoMenuSort> sortQueryWrapper = new QueryWrapper<>();
                sortQueryWrapper.eq("role_id",roleId);
                sortQueryWrapper.eq("menu_id",item.getMenuId());
                KoMenuSort koMenuSort = menuSortService.getOne(sortQueryWrapper);
                if(koMenuSort!=null && koMenuSort.getOrderNum()!=null){
                    item.setSort(koMenuSort.getOrderNum());
                }
            }
            menuList.sort(Comparator.comparing(MapMenuResult::getSort,Comparator.nullsLast(Integer::compareTo)));
            return;
        }
    }


    private String saveLoginToken(String account) {
        String token = "token-" + CommonUtils.getUUID1()+"-"+CommonUtils.currentDateToStr(6);
        redisCache.setCacheObject(token, account);
        redisCache.expire(token, 60 * 60 * 10);
        return token;
    }


    public R getMenuByAccount(KoUser entity) {

        KoUser user = this.getById(entity.getAccount());

        if (user == null) {
            return R.error();
        }

        if (entity.getSysType().equals(2)) {
            //List<String> ids = this.getAllMenuId(user.getAccount());
            List<String> ids = this.getAllMenuId1(user.getAccount(),2);
            //ids = ids.stream().distinct().collect(Collectors.toList());
            List<MapMenuResult> menuList = this.baseMapper.selectMapRootMenuList(ids, 2);
            this.buildMapTree(menuList, ids);
            return R.success(menuList);
        }

        if (entity.getSysType().equals(1)) {
            List<String> ids = this.getAllMenuId(user.getAccount());
            ids = ids.stream().distinct().collect(Collectors.toList());
            List<KoMenu> menuList = this.baseMapper.selectRootMenuList(ids, 1);
            this.buildTree(menuList, ids);
            this.menuSort(menuList, user);
            return R.success(menuList);
        }

        return R.success();
    }


    public List<String> getAllMenuIdByAccount(String account){
        List<String> ids = new ArrayList<>();
        ids.add("roles");
//        ids.add("1529760943982919681");
//        ids.add("1529762037517688833");
//        ids.add("23433");

        QueryWrapper<KoUserMenu> q1 = new QueryWrapper<>();
        q1.eq("user_id",account);
        List<KoUserMenu> list1 = userMenuService.list(q1);
        for (KoUserMenu item: list1) {
            ids.add(item.getMenuId());
        }

        QueryWrapper<KoUserRole> q2 = new QueryWrapper<>();
        q2.eq("user_id",account);
        List<KoUserRole> list2 = userRoleService.list(q2);
        for (KoUserRole item: list2) {

            QueryWrapper<KoRoleMenu> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getRoleId());
            List<KoRoleMenu> list3 = roleMenuService.list(q3);
            for (KoRoleMenu it: list3) {
                ids.add(it.getMenuId());
            }

        }

        //add default role
        QueryWrapper<KoRole> queryWrapperRole = new QueryWrapper<>();
        queryWrapperRole.eq("role_type","Default");
        List<KoRole> defeuleRoles = roleService.list(queryWrapperRole);
        for (KoRole item: defeuleRoles) {
            QueryWrapper<KoRoleMenu> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getId());
            List<KoRoleMenu> list3 = roleMenuService.list(q3);
            for (KoRoleMenu it: list3) {
                ids.add(it.getMenuId());
            }
        }
        return ids;
    }




    private List<String> getAllMenuId1(String account,Integer sysType){
        List<String> ids = new ArrayList<>();
        ids.add("default");

        //user menu
        QueryWrapper<KoUserMenu> q1 = new QueryWrapper<>();
        q1.eq("user_id",account);
        q1.eq("sys_type",sysType);
        List<KoUserMenu> list1 = userMenuService.list(q1);
        for (KoUserMenu item: list1) {
            ids.add(item.getMenuId());
        }

        //role menu
        QueryWrapper<KoUserRole> q2 = new QueryWrapper<>();
        q2.eq("user_id",account);
        List<KoUserRole> list2 = userRoleService.list(q2);
        for (KoUserRole item: list2) {
            QueryWrapper<KoRoleMenu> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getRoleId());
            q3.eq("sys_type",sysType);
            List<KoRoleMenu> list3 = roleMenuService.list(q3);
            for (KoRoleMenu it: list3) {
                ids.add(it.getMenuId());
            }
        }

        //default role menu
        QueryWrapper<KoRole> queryWrapperRole = new QueryWrapper<>();
        queryWrapperRole.eq("role_type","Default");
        List<KoRole> defeuleRoles = roleService.list(queryWrapperRole);
        for (KoRole item: defeuleRoles) {
            QueryWrapper<KoRoleMenu> q3 = new QueryWrapper<>();
            q3.eq("role_id",item.getId());
            q3.eq("sys_type",sysType);
            List<KoRoleMenu> list3 = roleMenuService.list(q3);
            for (KoRoleMenu it: list3) {
                ids.add(it.getMenuId());
            }
        }

        //duplicate removal
        ids = ids.stream().distinct().collect(Collectors.toList());

        return ids;
    }


    //Lazy loading
    public R MapGetMenuByAccount(JSONObject par){

        String userAccount = par.getString("userAccount");
        Boolean isRoot = par.getBoolean("isRoot");
        String parentId = par.getString("parentId");

        List<String> ids = this.getAllMenuId1(userAccount,2);

        if(isRoot){
            List<MapMenuResult> menuList = this.baseMapper.selectMapRootMenuList(ids, 2);
            return R.success(menuList);
        }

        List<MapMenuResult> childrenList = this.baseMapper.selectMapChildrenMenuList(ids, parentId);
        return R.success(childrenList);

    }


    public R getAllRoute() {
        List<MapMenuResult> list = this.baseMapper.allRoute();
        return R.success(list);
    }


    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            String localIp = "127.0.0.1";
            String localIpv6 = "0:0:0:0:0:0:0:1";
            if (ipAddress.equals(localIp) || ipAddress.equals(localIpv6)) {
                // Take the configured IP of the machine according to the network card
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // In the case of multiple agents, the first IP is the real IP of the client, and multiple IPS are divided according to ','
        String ipSeparate = ",";
        int ipLength = 15;
        if (ipAddress != null && ipAddress.length() > ipLength) {
            if (ipAddress.indexOf(ipSeparate) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(ipSeparate));
            }
        }
        return ipAddress;
    }


}
