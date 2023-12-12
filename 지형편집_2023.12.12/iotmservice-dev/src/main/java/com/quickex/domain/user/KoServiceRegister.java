package com.quickex.domain.user;

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
public class KoServiceRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String serviceName;

    private String serviceDescribe;

    private String serviceVersion;

    private String createUser;

    private Date createTime;

    private String organizationId;

    private String approvalUser;

    private Date approvalTime;

    private Integer approvalState;

    private Integer registerType;

    private String file1;

    private String file2;

    private String file3;

    @TableField(exist = false)
    private String organizationName;


}
