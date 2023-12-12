package com.quickex.domain.doc;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoAnalysisDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private Integer docType;

    private String analysisName;

    private String docName;

    private String userAccount;

    private Date createTime;

    private String docData;

    private String docPath;

    private String subType;


    //query p   --- start
    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

    @TableField(exist = false)
    private List<String> ids;

    @TableField(exist = false)
    private String createDate;
    //query p   --- end

}
