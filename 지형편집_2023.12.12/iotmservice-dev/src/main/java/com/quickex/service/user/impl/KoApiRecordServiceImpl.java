package com.quickex.service.user.impl;

import cn.hutool.core.io.file.FileWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.AppConfig;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.layer.KoAgentConfig;
import com.quickex.domain.user.KoApiRecord;
import com.quickex.domain.user.KoUser;
import com.quickex.mapper.user.KoApiRecordMapper;
import com.quickex.service.user.IKoApiRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quickex.service.user.IKoUserService;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.ExcelUtils;
import com.quickex.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.CommandAPDU;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KoApiRecordServiceImpl extends BaseServiceImpl<KoApiRecordMapper, KoApiRecord> implements IKoApiRecordService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private IKoUserService userService;

    public R apiRecordPage(PageDomain pageDomain, KoApiRecord entity){

        try{
            Page<KoApiRecord> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
            QueryWrapper<KoApiRecord> query = new QueryWrapper<>();

            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                entity.setStartDate(entity.getStartDate()+" 00:00:00");
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                entity.setEndDate(entity.getEndDate()+" 23:59:59");
            }

            if(entity.getAccount()!=null && !entity.getAccount().isEmpty()){
                query.eq("account",entity.getAccount());
            }
            if(entity.getApi()!=null && !entity.getApi().isEmpty()){
                query.like("api",entity.getApi());
            }
//        if(entity.getTime()!=null){
//            query.eq("to_char(time, 'yyyy-MM-dd')", CommonUtils.currentDateToStr(1,entity.getTime()));
//        }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                query.ge("time", sdf.parse(entity.getStartDate()));
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                query.le("time", sdf.parse(entity.getEndDate()));
            }

            query.select().orderByDesc("time");
            IPage<KoApiRecord> list =this.baseMapper.selectPage(page,query);
            return R.success(list);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }

    }

    public R userListExcel(){
        File templateFile = new File(appConfig.getWordTemplate() + "/export-user-list.xlsx");
        Map titleMap = new HashMap();

        List<Map<String, Object>> tableList = new ArrayList();
        List<KoUser> users = userService.allUser();
        for (KoUser item : users) {
            Map map = new HashMap();
            map.put("account", item.getAccount());
            map.put("cityname", item.getCityName());
            map.put("areaname", item.getAreaName());
            map.put("organizationname", item.getOrganizationName());
            map.put("department", item.getDepartmentName());
            if(item.getUserState()==null){
                map.put("userstate", "비활성화");
            }else{
                if(item.getUserState()==0){
                    map.put("userstate", "활성화");
                }else{
                    map.put("userstate", "비활성화");
                }
            }
            map.put("createuser", item.getCreateUser());
            tableList.add(map);
        }

        String account = "excel-temp";//UserContext.getUserAccount();
        String FileName =CommonUtils.getUUID();
        String foldeName =CommonUtils.currentDateToStr(7);
        String docSavePath = "/" + account + "/" + foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + "/" + account + "/" + foldeName);
        if (!folderDir.exists() && !folderDir.isDirectory()) {
            folderDir.mkdirs();
        }

        File outFile = new File(appConfig.getUavModelPath() +docSavePath);
        FileWriter writer = new FileWriter(outFile);
        writer.write(bytes, 0, bytes.length);

        JSONObject result = new JSONObject();
        result.put("url",docSavePath);
        return R.success(result);
    }

    public R apiRecordListExcel(KoApiRecord entity){

        try{
            File templateFile = new File(appConfig.getWordTemplate() + "/export-api-record.xlsx");
            Map titleMap = new HashMap();
            titleMap.put("apiname", entity.getApi());
            titleMap.put("ksrq", entity.getStartDate());
            titleMap.put("jsrq", entity.getEndDate());

            List<Map<String, Object>> tableList = new ArrayList();
            QueryWrapper<KoApiRecord> query = new QueryWrapper<>();
            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                entity.setStartDate(entity.getStartDate()+" 00:00:00");
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                entity.setEndDate(entity.getEndDate()+" 23:59:59");
            }
            if(entity.getAccount()!=null && !entity.getAccount().isEmpty()){
                query.eq("account",entity.getAccount());
            }
            if(entity.getApi()!=null && !entity.getApi().isEmpty()){
                query.like("api",entity.getApi());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                query.ge("time", sdf.parse(entity.getStartDate()));
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                query.le("time", sdf.parse(entity.getEndDate()));
            }
            query.select().orderByDesc("time");
            List<KoApiRecord> list =this.list(query);
            for (KoApiRecord item : list) {
                Map map = new HashMap();
                map.put("id", item.getId());
                map.put("account", item.getAccount());
                map.put("api", item.getApi());
                map.put("time", CommonUtils.currentDateToStr(2,item.getTime()));
                map.put("times", item.getTimes());
                tableList.add(map);
            }

            String account = UserContext.getUserAccount();
            String FileName =CommonUtils.getUUID();
            String foldeName =CommonUtils.currentDateToStr(7);
            //String docSavePath = "/" + account + "/" + foldeName + "/" + FileName + ".xlsx";
            String docSavePath = "/excel-temp/" + foldeName + "/" + FileName + ".xlsx";


            byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

            //File folderDir = new File(appConfig.getUavModelPath() + "/" + account + "/" + foldeName);
            File folderDir = new File(appConfig.getUavModelPath() + "/excel-temp/" + foldeName);

            if (!folderDir.exists() && !folderDir.isDirectory()) {
                folderDir.mkdirs();
            }

            File outFile = new File(appConfig.getUavModelPath() +docSavePath);
            FileWriter writer = new FileWriter(outFile);
            writer.write(bytes, 0, bytes.length);

            JSONObject result = new JSONObject();
            result.put("url",docSavePath);
            return R.success(result);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error();
        }


    }

}
