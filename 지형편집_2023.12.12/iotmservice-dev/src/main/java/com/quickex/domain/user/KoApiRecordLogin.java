package com.quickex.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoApiRecordLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String account;

    private String ip;

    private String api;

    private Integer times;

    private Date time;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;


}
