package com.quickex.service.layer.impl;

import com.quickex.core.util.DateUtils;
import com.quickex.domain.layer.KoShpFile;
import com.quickex.mapper.layer.KoShpFileMapper;
import com.quickex.service.layer.IKoShpFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoShpFileServiceImpl implements IKoShpFileService
{
    @Autowired
    private KoShpFileMapper egovFileMapper;

    @Override
    public KoShpFile selectEgovFileByFileId(String fileId)
    {
        return egovFileMapper.selectEgovFileByFileId(fileId);
    }

    @Override
    public List<KoShpFile> selectEgovFileList(KoShpFile egovFile)
    {
        return egovFileMapper.selectEgovFileList(egovFile);
    }

    @Override
    public int insertEgovFile(KoShpFile egovFile)
    {
        egovFile.setCreateTime(DateUtils.getNowDate());
        return egovFileMapper.insertEgovFile(egovFile);
    }

    @Override
    public int updateEgovFile(KoShpFile egovFile)
    {
        egovFile.setUpdateTime(DateUtils.getNowDate());
        return egovFileMapper.updateEgovFile(egovFile);
    }

    @Override
    public int deleteEgovFileByFileIds(String[] fileIds)
    {
        return egovFileMapper.deleteEgovFileByFileIds(fileIds);
    }

    @Override
    public int deleteEgovFileByFileId(String fileId)
    {
        return egovFileMapper.deleteEgovFileByFileId(fileId);
    }
}
