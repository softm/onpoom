package com.quickex.domain.stage2;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.ibatis.sqlmap.engine.type.StringTypeHandler;
import com.quickex.config.GeometryTypeHandler;
import com.quickex.utils.WKTUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoTerrainEdit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String userAccount;

    private Date createTime;

    private String terrainName;

    private String buildName;

    private String terrainHeight;

    private String testHeight;

    @TableField(value = "geom", el = "geom, typeHandler=com.quickex.config.GeometryTypeHandler")
    private String geom;

    public String getGeom() {
        if (geom == null) {
            this.geom = "";
        }
        return geom;
    }

    private String img;


    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

    @TableField(exist = false)
    private List<String> ids;

}
