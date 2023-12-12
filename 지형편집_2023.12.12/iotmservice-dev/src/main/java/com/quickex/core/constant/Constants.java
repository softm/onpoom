package com.quickex.core.constant;

/**
 * General constant information
 *
 * @author ffzh.thero
 */
public class Constants {
    /**
     * UTF-8
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK
     */
    public static final String GBK = "GBK";

    /**
     * http
     */
    public static final String HTTP = "http://";

    /**
     * https
     */
    public static final String HTTPS = "https://";

    /**
     * SUCCESS
     */
    public static final String SUCCESS = "0";

    /**
     * FAIL
     */
    public static final String FAIL = "1";

    /**
     * LOGIN_SUCCESS
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * LOGOUT
     */
    public static final String LOGOUT = "Logout";

    /**
     * LOGIN_FAIL
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * Verification Code redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * log on user redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * Anti duplication submission redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * Validity period of verification code (minutes)
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * token
     */
    public static final String TOKEN = "token";

    /**
     * Token prefix
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Token prefix
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * User ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * User name
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * User Avatar
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * Creation time
     */
    public static final String JWT_CREATED = "created";

    /**
     * User rights
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * Parameter management cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * Dictionary management cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * Resource mapping path prefix
     */
    public static final String RESOURCE_PREFIX = "/profile";

    public static final String SYS_DICT_VERSION = "sys_dict_version";
    public static final String SYS_DEPT_VERSION = "dept_version";


    /**
     * System dictionary version
     */
    public static final String SYS_DICT_VERSION_KEY = SYS_CONFIG_KEY + SYS_DICT_VERSION;

    public static final String SYS_DEPT_VERSION_KEY = SYS_CONFIG_KEY + SYS_DEPT_VERSION;
}
