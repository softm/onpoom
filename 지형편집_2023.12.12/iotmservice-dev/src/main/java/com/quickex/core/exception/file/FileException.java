package com.quickex.core.exception.file;


import com.quickex.core.exception.base.BaseException;

/**
 * File information exception class
 * 
 * @author ffzh
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
