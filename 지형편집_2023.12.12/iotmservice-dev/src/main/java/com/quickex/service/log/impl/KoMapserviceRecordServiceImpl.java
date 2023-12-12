package com.quickex.service.log.impl;

import cn.hutool.core.io.file.FileWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.config.AppConfig;
import com.quickex.core.page.PageDomain;
import com.quickex.core.result.R;
import com.quickex.core.service.impl.BaseServiceImpl;
import com.quickex.domain.log.KoMapserviceRecord;
import com.quickex.domain.user.KoMenu;
import com.quickex.mapper.log.KoMapserviceRecordMapper;
import com.quickex.service.log.IKoMapserviceRecordService;
import com.quickex.service.user.IKoMenuService;
import com.quickex.utils.CommonUtils;
import com.quickex.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KoMapserviceRecordServiceImpl extends BaseServiceImpl<KoMapserviceRecordMapper, KoMapserviceRecord> implements IKoMapserviceRecordService {

    @Autowired
    private IKoMenuService menuService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private HttpServletRequest request;

    public R add(KoMapserviceRecord entity) {
        try {
            List<String> names = new ArrayList<>();
            for (String item : entity.getIds()) {
                KoMenu menu = menuService.getById(item);
                names.add(menu.getMenuName());
            }
            entity.setMenuId(String.join(">", entity.getIds()));
            entity.setMenuPath(names.stream().collect(Collectors.joining(">")));
            entity.setAccessTime(new Date());
            entity.setIp(this.getIpAddr(request));
            this.save(entity);
            return R.success();
        } catch (Exception ex) {
            log.error("ex:", ex);
            return R.error();
        }
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

    public R page(PageDomain pageDomain, KoMapserviceRecord entity){
        try {
            Page<KoMapserviceRecord> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
            QueryWrapper<KoMapserviceRecord> query = new QueryWrapper<>();

            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                entity.setStartDate(entity.getStartDate()+" 00:00:00");
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                entity.setEndDate(entity.getEndDate()+" 23:59:59");
            }
            if(entity.getUserAccount()!=null && !entity.getUserAccount().isEmpty()){
                query.like("user_account",entity.getUserAccount());
            }
            if(entity.getMenuPath()!=null && !entity.getMenuPath().isEmpty()){
                query.like("menu_path",entity.getMenuPath());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                query.ge("access_time", sdf.parse(entity.getStartDate()));
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                query.le("access_time", sdf.parse(entity.getEndDate()));
            }

            query.select().orderByDesc("access_time");
            IPage<KoMapserviceRecord> list =this.baseMapper.selectPage(page,query);
            return R.success(list);
        } catch (Exception ex) {
            log.error("ex:", ex);
            return R.error();
        }
    }

    public R toExcel(KoMapserviceRecord entity){

        try{
            File templateFile = new File(appConfig.getWordTemplate() + "/export-mapservice-record.xlsx");
            Map titleMap = new HashMap();
            titleMap.put("account", entity.getUserAccount());
            titleMap.put("menuPath", entity.getMenuPath());
            titleMap.put("ksrq", entity.getStartDate());
            titleMap.put("jsrq", entity.getEndDate());

            List<Map<String, Object>> tableList = new ArrayList();
            QueryWrapper<KoMapserviceRecord> query = new QueryWrapper<>();
            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                entity.setStartDate(entity.getStartDate()+" 00:00:00");
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                entity.setEndDate(entity.getEndDate()+" 23:59:59");
            }
            if(entity.getUserAccount()!=null && !entity.getUserAccount().isEmpty()){
                query.like("user_account",entity.getUserAccount());
            }
            if(entity.getMenuPath()!=null && !entity.getMenuPath().isEmpty()){
                query.like("menu_path",entity.getMenuPath());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(entity.getStartDate()!=null && !entity.getStartDate().equals("")){
                query.ge("access_time", sdf.parse(entity.getStartDate()));
            }
            if(entity.getEndDate()!=null && !entity.getEndDate().equals("")){
                query.le("access_time", sdf.parse(entity.getEndDate()));
            }
            query.select().orderByDesc("access_time");
            List<KoMapserviceRecord> list =this.list(query);
            for (KoMapserviceRecord item : list) {
                Map map = new HashMap();
                map.put("id", item.getId());
                map.put("userAccount", item.getUserAccount());
                map.put("menuPath", item.getMenuPath());
                map.put("accessTime", CommonUtils.currentDateToStr(2,item.getAccessTime()));
                tableList.add(map);
            }

            String account = "excel-temp";//entity.getUserAccount();
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
        }catch (Exception ex){
            log.error("ex:",ex);
            return R.error();
        }
    }


    //region  api statistics
    public R apiPag(PageDomain pageDomain, JSONObject par){

        String accessAccount = par.getString("accessAccount");
        String apiName = par.getString("apiName");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.apiPag(page,accessAccount,apiName);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }
    public R apiYear(PageDomain pageDomain, JSONObject par){
        String yyyy = par.getString("yyyy");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.accountPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInYear();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("account").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getApiCountYear(account, yyyy + "-" + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
        }

        return R.success(data);
    }
    public R apiMonth(PageDomain pageDomain, JSONObject par){
        String yyyymm = par.getString("yyyymm");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.accountPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInMonth(yyyymm);

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("account").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getApiCountMonth(account, item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
        }

        return R.success(data);
    }
    public R apiDay(PageDomain pageDomain, JSONObject par){
        String yyyymmdd = par.getString("yyyymmdd");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.accountPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInDay();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("account").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getApiCountDay(account, yyyymmdd + " " + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
        }
        return R.success(data);
    }


    public R menuPag(PageDomain pageDomain, JSONObject par){

        String accessAccount = par.getString("accessAccount");
        String menuName = par.getString("menuName");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPag(page,accessAccount,menuName);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        return R.success(data);
    }
    public R menuYear(PageDomain pageDomain, JSONObject par){
        String yyyy = par.getString("yyyy");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInYear();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("id").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getMenuCountYear(account, yyyy + "-" + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
            map.remove("id");
        }

        return R.success(data);
    }
    public R menuMonth(PageDomain pageDomain, JSONObject par){
        String yyyymm = par.getString("yyyymm");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInMonth(yyyymm);

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("id").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getMenuCountMonth(account, item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
            map.remove("id");
        }

        return R.success(data);
    }
    public R menuDay(PageDomain pageDomain, JSONObject par){
        String yyyymmdd = par.getString("yyyymmdd");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInDay();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("id").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getMenuCountDay(account, yyyymmdd + " " + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
            map.remove("id");
        }
        return R.success(data);
    }
    //endregion


    //region  api excel
    public R apiPagExcel(PageDomain pageDomain, JSONObject par){

        String accessAccount = par.getString("accessAccount");
        String apiName = par.getString("apiName");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.apiPag(page,accessAccount,apiName);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());


        File templateFile = new File(appConfig.getWordTemplate() + "/export-01.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("account", accessAccount);
        titleMap.put("api", apiName);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("id", map.get("id"));
            item.put("account", map.get("account"));
            item.put("cityName", map.get("cityName"));
            item.put("areaName", map.get("areaName"));
            item.put("organizationName", map.get("organizationName"));
            item.put("departmentName", map.get("departmentName"));
            item.put("api", map.get("api"));
            item.put("ip", map.get("ip"));
            item.put("time", map.get("time"));
            tableList.add(item);
        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    public R apiYearExcel(PageDomain pageDomain, JSONObject par){
        String yyyy = par.getString("yyyy");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.accountPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInYear();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("account").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getApiCountYear(account, yyyy + "-" + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
        }

        File templateFile = new File(appConfig.getWordTemplate() + "/export-02.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("year", yyyy);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("account", map.get("account"));
            item.put("sum", map.get("sum"));
            item.put("one", map.get("01"));
            item.put("two", map.get("02"));
            item.put("three", map.get("03"));
            item.put("four", map.get("04"));
            item.put("five", map.get("05"));
            item.put("six", map.get("06"));
            item.put("seven", map.get("07"));
            item.put("eight", map.get("08"));
            item.put("nine", map.get("09"));
            item.put("ten", map.get("10"));
            item.put("eleven", map.get("11"));
            item.put("twelve", map.get("12"));
            tableList.add(item);

        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    public R apiMonthExcel(PageDomain pageDomain, JSONObject par){
        String yyyymm = par.getString("yyyymm");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.accountPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInMonth(yyyymm);

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("account").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getApiCountMonth(account, item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
        }

        File templateFile = new File(appConfig.getWordTemplate() + "/export-03.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("month", yyyymm);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("account", map.get("account"));
            item.put("sum", map.get("sum"));

            int i = 1;
            for (String time:times) {
                item.put("count"+i , map.get(time));
                ++i;
            }
            tableList.add(item);

        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    public R apiDayExcel(PageDomain pageDomain, JSONObject par){
        String yyyymmdd = par.getString("yyyymmdd");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.accountPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInDay();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("account").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getApiCountDay(account, yyyymmdd + " " + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
        }
        File templateFile = new File(appConfig.getWordTemplate() + "/export-04.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("day", yyyymmdd);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("account", map.get("account"));
            item.put("sum", map.get("sum"));

            int i = 1;
            for (String time:times) {
                item.put("count"+i , map.get(time));
                ++i;
            }
            tableList.add(item);

        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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


    public R menuPagExcel(PageDomain pageDomain, JSONObject par){

        String accessAccount = par.getString("accessAccount");
        String menuName = par.getString("menuName");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPag(page,accessAccount,menuName);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        File templateFile = new File(appConfig.getWordTemplate() + "/export-05.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("account", accessAccount);
        titleMap.put("app", menuName);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("id", map.get("id"));
            item.put("account", map.get("account"));
            item.put("cityName", map.get("cityName"));
            item.put("areaName", map.get("areaName"));
            item.put("organizationName", map.get("organizationName"));
            item.put("departmentName", map.get("departmentName"));
            item.put("app", map.get("menuPath"));
            item.put("ip", map.get("ip"));
            item.put("time", map.get("time"));
            tableList.add(item);
        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    public R menuYearExcel(PageDomain pageDomain, JSONObject par){
        String yyyy = par.getString("yyyy");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInYear();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("id").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getMenuCountYear(account, yyyy + "-" + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
            map.remove("id");
        }


        File templateFile = new File(appConfig.getWordTemplate() + "/export-06.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("year", yyyy);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("app", map.get("app"));
            item.put("sum", map.get("sum"));
            item.put("one", map.get("01"));
            item.put("two", map.get("02"));
            item.put("three", map.get("03"));
            item.put("four", map.get("04"));
            item.put("five", map.get("05"));
            item.put("six", map.get("06"));
            item.put("seven", map.get("07"));
            item.put("eight", map.get("08"));
            item.put("nine", map.get("09"));
            item.put("ten", map.get("10"));
            item.put("eleven", map.get("11"));
            item.put("twelve", map.get("12"));
            tableList.add(item);

        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    public R menuMonthExcel(PageDomain pageDomain, JSONObject par){
        String yyyymm = par.getString("yyyymm");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInMonth(yyyymm);

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("id").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getMenuCountMonth(account, item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
            map.remove("id");
        }

        File templateFile = new File(appConfig.getWordTemplate() + "/export-07.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("month", yyyymm);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("app", map.get("app"));
            item.put("sum", map.get("sum"));

            int i = 1;
            for (String time:times) {
                item.put("count"+i , map.get(time));
                ++i;
            }
            tableList.add(item);

        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    public R menuDayExcel(PageDomain pageDomain, JSONObject par){
        String yyyymmdd = par.getString("yyyymmdd");

        Page<LinkedHashMap<String, Object>> page = new Page<>(pageDomain.getPageNum(),pageDomain.getPageSize());
        List<LinkedHashMap<String, Object>> list =this.baseMapper.menuPage(page);

        IPage<LinkedHashMap<String, Object>> data =new Page<>();
        data.setRecords(page.setRecords(list).getRecords());
        data.setPages(page.getPages());
        data.setTotal(page.getTotal());
        data.setCurrent(pageDomain.getPageNum());
        data.setSize(pageDomain.getPageSize());

        List<String> times = CommonUtils.getDatesInDay();

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            String account = map.get("id").toString();
            for (String item : times) {
                Integer value = this.baseMapper.getMenuCountDay(account, yyyymmdd + " " + item);
                map.put(item, value);
            }
        }

        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Integer sum = 0;
            for (String item : times) {
                Integer value = Integer.valueOf(map.get(item).toString());
                sum = sum + value;
            }
            map.put("sum",sum);
            map.remove("id");
        }
        File templateFile = new File(appConfig.getWordTemplate() + "/export-08.xlsx");
        Map titleMap = new HashMap();
        titleMap.put("day", yyyymmdd);

        List<Map<String, Object>> tableList = new ArrayList();
        for (LinkedHashMap<String, Object> map : data.getRecords()) {
            Map item = new HashMap();
            item.put("app", map.get("app"));
            item.put("sum", map.get("sum"));

            int i = 1;
            for (String time:times) {
                item.put("count"+i , map.get(time));
                ++i;
            }
            tableList.add(item);
        }

        String FileName =CommonUtils.getUUID();
        String foldeName = "/temp/document/" + CommonUtils.currentDateToStr(7);
        String docSavePath =  foldeName + "/" + FileName + ".xlsx";

        byte[] bytes = ExcelUtils.writeExcel(templateFile, titleMap, tableList);

        File folderDir = new File(appConfig.getUavModelPath() + foldeName);
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
    //endregion



    public void deleteApiLog(){
        this.baseMapper.deleteApiLog();
    }

    public void deleteLoginLog(){
        this.baseMapper.deleteLoginLog();
    }

//    @PostConstruct
//    public void run() {
//        log.info("=========================== init ===========================");
//    }


//    @Transactional
//    @Scheduled(fixedRate = 60000 * 60 * 24)
//    public void startTask() {
//        log.info("=========================== start clear logs ===========================");
//        this.deleteApiLog();
//        this.deleteLoginLog();
//        log.info("=========================== end clear logs ===========================");
//    }

}
