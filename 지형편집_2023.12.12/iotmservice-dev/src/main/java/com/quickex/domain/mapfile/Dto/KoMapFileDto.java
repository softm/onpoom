package com.quickex.domain.mapfile.Dto;

import com.quickex.domain.mapfile.KoMapFile;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class KoMapFileDto {


    private Boolean isAdmin;


    private KoMapFile file;


    private List<Integer> types;

    private List<String> lables;

}
