package com.quickex.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.user.KoMenu;
import com.quickex.domain.user.KoServiceCollectionMenu;
import com.quickex.mapper.user.KoServiceCollectionMenuMapper;
import com.quickex.service.user.IKoMenuService;
import com.quickex.service.user.IKoServiceCollectionMenuService;
import com.quickex.service.user.IKoUserService;
import com.quickex.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class KoServiceCollectionMenuServiceImpl extends BaseServiceImpl<KoServiceCollectionMenuMapper, KoServiceCollectionMenu> implements IKoServiceCollectionMenuService {

    @Autowired
    private IKoUserService userService;

    @Autowired
    private IKoMenuService menuService;

    public R add(KoServiceCollectionMenu entity) {
        for (String item : entity.getIds()) {
            KoMenu menu = menuService.getById(item);
            if(menu==null ||menu.getMenuType()==1){
                return R.error("the selected node is invalid!");
            }
        }
        for (String item : entity.getIds()) {
            KoServiceCollectionMenu data = this.getMenu(entity.getPid(), item);
            if (data != null) {
                this.save(data);
            }
        }
        return R.success();
    }

    private KoServiceCollectionMenu getMenu(String pid, String lastMenuId) {
        KoServiceCollectionMenu data = new KoServiceCollectionMenu();
        data.setPid(pid);
        data.setCreateTime(new Date());
        List<KoMenu> list = menuService.getAllPatentAndThis(lastMenuId);
        String titleId = "";
        for (int i = list.size(); i-- > 0; ) {
            titleId += list.get(i).getMenuDescribe() + "-";
        }
        titleId = titleId.substring(0, titleId.length() - 1);
        data.setMenuName(titleId);
        data.setLastComponentId(list.get(0).getMenuPath());
        data.setLastMenuId(list.get(0).getId());
        data.setRootComponentId(list.get(list.size() - 1).getMenuPath());
        data.setRootMenuId(list.get(list.size() - 1).getId());
        return data;
    }

    public R edit(KoServiceCollectionMenu entity){
        this.updateById(entity);
        return R.success();
    }

    public R delete(KoServiceCollectionMenu entity){
        this.removeById(entity);
        return R.success();
    }

    public R list(KoServiceCollectionMenu entity){
        QueryWrapper<KoServiceCollectionMenu> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("pid",entity.getPid());
        List<KoServiceCollectionMenu> list = this.list(queryWrapper);
        return R.success(list);
    }

    public R checkState(KoServiceCollectionMenu entity) {
        JSONObject result = new JSONObject();
        List<String> ids = userService.getAllMenuIdByAccount(UserContext.getUserAccount());
        for (String item : ids) {
            if (entity.getLastMenuId().equals(item)) {
                KoMenu menu = menuService.getById(entity.getLastMenuId());
                if (menu == null) {
                    result.put("result", false);
                    return R.success(result);
                }
                if (menu.getMenuState() == 1) {
                    result.put("result", false);
                    return R.success(result);
                }
                result.put("result", false);
                return R.success(result);
            }
        }
        result.put("result", false);
        return R.success(result);
    }
}
