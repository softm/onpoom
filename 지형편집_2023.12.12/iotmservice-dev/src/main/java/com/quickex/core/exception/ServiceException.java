package com.quickex.core.exception;

/**
 * Business exception
 * 
 * @author ffzh
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    private Integer code;

    /**
     * message
     */
    private String message;

    /**
     * Error details, internal debugging error
     *
     * Design consistent with {@ link commonresult #getdetailmessage()}
     */
    private String detailMessage;

    /**
     * Empty construction method to avoid deserialization problem
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}