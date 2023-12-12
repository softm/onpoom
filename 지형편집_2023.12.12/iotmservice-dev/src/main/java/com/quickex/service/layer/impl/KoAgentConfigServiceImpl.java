package com.quickex.service.layer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.log.OperLog;
import com.quickex.config.log.OperType;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoAgentConfig;
import com.quickex.domain.layer.KoProvince;
import com.quickex.mapper.layer.KoAgentConfigMapper;
import com.quickex.service.layer.IKoAgentConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoAgentConfigServiceImpl extends BaseServiceImpl<KoAgentConfigMapper, KoAgentConfig> implements IKoAgentConfigService {

    @OperLog(operType = OperType.SELECT, operDesc = "/api/koAgentConfig/list")
    public R getPage(PageDomain pageDomain, KoAgentConfig koAgentConfig){
        Page<KoAgentConfig> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        QueryWrapper<KoAgentConfig> query = new QueryWrapper<>();
        if(koAgentConfig.getProxyType()!=null && !koAgentConfig.getProxyType().isEmpty()){
            query.like("proxy_type", koAgentConfig.getProxyType());
        }
        IPage<KoAgentConfig> list =this.baseMapper.selectPage(page,query);
        return R.success(list);
    }

    @OperLog(operType = OperType.SELECT, operDesc = "/api/koAgentConfig/get")
    public R getById(KoAgentConfig koAgentConfig){
        KoAgentConfig data = getById(koAgentConfig.getId());
        return R.success(data);
    }

    @OperLog(operType = OperType.ADD, operDesc = "/api/koAgentConfig/add")
    public R add(KoAgentConfig koAgentConfig){
        save(koAgentConfig);
        return R.success();
    }

    @OperLog(operType = OperType.EDIT, operDesc = "/api/koAgentConfig/edit")
    public R edit(KoAgentConfig koAgentConfig){
        updateById(koAgentConfig);
        return R.success();
    }

    @OperLog(operType = OperType.DELETE, operDesc = "/api/koAgentConfig/delete")
    public R deleteIds(List<String> ids){
        removeByIds(ids);
        return R.success();
    }

}
