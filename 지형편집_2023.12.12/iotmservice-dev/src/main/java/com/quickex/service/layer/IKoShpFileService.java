package com.quickex.service.layer;

import com.quickex.domain.layer.KoShpFile;

import java.util.List;

public interface IKoShpFileService {

    KoShpFile selectEgovFileByFileId(String fileId);

    List<KoShpFile> selectEgovFileList(KoShpFile egovFile);

    int insertEgovFile(KoShpFile egovFile);

    int updateEgovFile(KoShpFile egovFile);

    int deleteEgovFileByFileIds(String[] fileIds);

    int deleteEgovFileByFileId(String fileId);
}
