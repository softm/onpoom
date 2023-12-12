package com.quickex.domain.log;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoMapserviceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String menuId;

    private String menuPath;

    private String userAccount;

    private Date accessTime;

    private String ip;


    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

    @TableField(exist = false)
    private String [] ids;

}
