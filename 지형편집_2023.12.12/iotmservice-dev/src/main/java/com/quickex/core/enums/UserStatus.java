package com.quickex.core.enums;

/**
 * user state
 * 
 * @author ffzh
 */
public enum UserStatus
{
    OK("0", "normal"), DISABLE("1", "Deactivate"), DELETED("2", "delete");

    private final String code;
    private final String info;

    UserStatus(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
