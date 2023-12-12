package com.quickex.mapper.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quickex.core.result.R;
import com.quickex.domain.user.KoLayerAccessLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface KoLayerAccessLogMapper extends BaseMapper<KoLayerAccessLog> {

    List<LinkedHashMap<String,Object>> RecordPage(
            @Param("page") Page<LinkedHashMap<String,Object>> page,
            @Param("layerProvinceCode")String layerProvinceCode,
            @Param("origin")String origin,
            @Param("startDate")String startDate,
            @Param("endDate")String endDate
    );


    List<LinkedHashMap<String,Object>> serviceCount1();
    Integer serviceCount2();

    LinkedHashMap<String,Object> serviceCount(@Param("provinceCode")String provinceCode);

    List<LinkedHashMap<String,Object>> useCountByType(@Param("provinceCode")String provinceCode,
                                                      @Param("startDate")String startDate,
                                                      @Param("endDate")String endDate);

    List<LinkedHashMap<String,Object>> useCountByService(@Param("provinceCode")String provinceCode,
                                                         @Param("startDate")String startDate,
                                                         @Param("endDate") String endDate);


    List<LinkedHashMap<String,Object>> allserviceUseCountByToday();
    List<LinkedHashMap<String,Object>> allserviceUseCountByThismonth(@Param("dateList")List<String> dateList);
    List<LinkedHashMap<String,Object>> allserviceUseCountByHistory(@Param("dateList")List<String> dateList);
    List<LinkedHashMap<String,Object>> cityserviceUseCountByToday(@Param("provinceCode")String provinceCode);
    List<LinkedHashMap<String,Object>> cityserviceUseCountByThismonth(@Param("dateList")List<String> dateList,@Param("provinceCode")String provinceCode);
    List<LinkedHashMap<String,Object>> cityserviceUseCountByHistory(@Param("dateList")List<String> dateList,@Param("provinceCode")String provinceCode);

}
