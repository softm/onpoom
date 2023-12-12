package com.quickex.domain.mapfile.Dto;

import lombok.Data;

import java.util.List;

@Data
public class KoMapFileLabelRelationUpdateDto {
    public String fileId;
    public String createUser;
    public List<KoMapFileLabelRelationDto> list;

}
