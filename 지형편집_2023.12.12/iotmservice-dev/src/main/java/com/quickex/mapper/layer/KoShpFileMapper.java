package com.quickex.mapper.layer;

import com.quickex.domain.layer.KoShpFile;

import java.util.List;


public interface KoShpFileMapper
{
    /**
     * get
     * 
     * @param fileId
     * @return upFile
     */
    KoShpFile selectEgovFileByFileId(String fileId);

    /**
     * select list
     * 
     * @param egovFile upFile
     * @return upFile
     */
    List<KoShpFile> selectEgovFileList(KoShpFile egovFile);

    /**
     * add
     * 
     * @param egovFile upFile
     * @return
     */
    int insertEgovFile(KoShpFile egovFile);

    /**
     * update
     * 
     * @param egovFile upFile
     * @return
     */
    int updateEgovFile(KoShpFile egovFile);

    /**
     * delete by id
     * 
     * @param fileId
     * @return
     */
    int deleteEgovFileByFileId(String fileId);

    /**
     * delete by ids
     * 
     * @param fileIds
     * @return
     */
    int deleteEgovFileByFileIds(String[] fileIds);
}
