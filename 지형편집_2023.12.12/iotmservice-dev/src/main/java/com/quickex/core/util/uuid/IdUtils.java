package com.quickex.core.util.uuid;

/**
 * ID generator tool class
 * 
 * @author ffzh
 */
public class IdUtils
{
    /**
     * Get random UUID
     * 
     * @return
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * Simplified UUID with horizontal lines removed
     * 
     * @return
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * Obtain the random UUID and generate the UUID using threadlocalrandom with better performance
     * 
     * @return
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * The simplified UUID removes horizontal lines and uses threadlocalrandom with better performance to generate UUIDs
     * 
     * @return
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
