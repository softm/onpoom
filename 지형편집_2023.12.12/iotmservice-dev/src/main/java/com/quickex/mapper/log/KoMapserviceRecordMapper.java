package com.quickex.mapper.log;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.domain.log.KoMapserviceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface KoMapserviceRecordMapper extends BaseMapper<KoMapserviceRecord> {

    List<LinkedHashMap<String, Object>> apiPag(
            @Param("page") Page<LinkedHashMap<String, Object>> page,
            @Param("accessAccount") String accessAccount,
            @Param("apiName") String apiName
    );

    List<LinkedHashMap<String, Object>> menuPag(
            @Param("page") Page<LinkedHashMap<String, Object>> page,
            @Param("accessAccount") String accessAccount,
            @Param("menuName") String menuName
    );

    List<LinkedHashMap<String, Object>> accountPage(
            @Param("page") Page<LinkedHashMap<String, Object>> page
    );

    List<LinkedHashMap<String, Object>> menuPage(
            @Param("page") Page<LinkedHashMap<String, Object>> page
    );


    Integer getApiCountYear(
            @Param("account") String account,
            @Param("time") String time
    );

    Integer getApiCountMonth(
            @Param("account") String account,
            @Param("time") String time
    );

    Integer getApiCountDay(
            @Param("account") String account,
            @Param("time") String time
    );


    Integer getMenuCountYear(
            @Param("menuId") String menuId,
            @Param("time") String time
    );

    Integer getMenuCountMonth(
            @Param("menuId") String menuId,
            @Param("time") String time
    );

    Integer getMenuCountDay(
            @Param("menuId") String menuId,
            @Param("time") String time
    );

    Integer deleteApiLog();
    Integer deleteLoginLog();

}
