package com.quickex.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String menuId;

    private Integer sysType;

}
