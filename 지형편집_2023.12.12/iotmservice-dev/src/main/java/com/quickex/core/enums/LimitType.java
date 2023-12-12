package com.quickex.core.enums;

/**
 * Current limiting type
 *
 * @author ffzh
 */

public enum LimitType
{
    /**
     * Default policy global current limit
     */
    DEFAULT,

    /**
     * Current limiting according to requester IP
     */
    IP
}
