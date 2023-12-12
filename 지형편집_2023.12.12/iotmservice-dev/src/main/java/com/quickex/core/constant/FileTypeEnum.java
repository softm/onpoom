package com.quickex.core.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Description: file type
 */
@AllArgsConstructor
@NoArgsConstructor
public enum FileTypeEnum {
    FILE_TYPE_ZIP("application/zip", ".zip"),
    FILE_TYPE_GLTF("application/shp", ".gltf"),
    FILE_TYPE_GLB("application/shp", ".glb"),
    FILE_TYPE_3DS("application/shp", ".3ds"),
    FILE_TYPE_SHP("application/shp", ".shp");
    public String type;
    public String fileStufix;

    public static String getFileStufix(String type) {
        for (FileTypeEnum orderTypeEnum : FileTypeEnum.values()) {
            if (orderTypeEnum.type.equals(type)) {
                return orderTypeEnum.fileStufix;
            }
        }
        return null;
    }
}