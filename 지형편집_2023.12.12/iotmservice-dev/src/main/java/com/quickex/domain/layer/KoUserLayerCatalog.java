package com.quickex.domain.layer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KoUserLayerCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String catalogId;


}
