package com.quickex.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * 
 * @author ffzh
 */
//@Component
//@ConfigurationProperties(prefix = "koconfig")
//@Data
//@PropertySource(value={"classpath:app.properties"},encoding = "gb2312")
public class KoConfig
{
    /**  */
    public static String name;

    /**  */
    public static String version;

    /**  */
    public static String copyrightYear;

    /**  */
    public static boolean demoEnabled;

    /**  */
    public static String profile;

    /**  */
    public static boolean addressEnabled;

//    public String getName()
//    {
//        return name;
//    }
//
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//
//    public String getVersion()
//    {
//        return version;
//    }
//
//    public void setVersion(String version)
//    {
//        this.version = version;
//    }
//
//    public String getCopyrightYear()
//    {
//        return copyrightYear;
//    }
//
//    public void setCopyrightYear(String copyrightYear)
//    {
//        this.copyrightYear = copyrightYear;
//    }
//
//    public boolean isDemoEnabled()
//    {
//        return demoEnabled;
//    }
//
//    public void setDemoEnabled(boolean demoEnabled)
//    {
//        this.demoEnabled = demoEnabled;
//    }
//
    public static String getProfile()
    {
        return profile;
    }
//
//    public void setProfile(String profile)
//    {
//        KoConfig.profile = profile;
//    }
//
    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }
//
//    public void setAddressEnabled(boolean addressEnabled)
//    {
//        KoConfig.addressEnabled = addressEnabled;
//    }

    /**
     *
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     *
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     *
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     *
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

    /**
     *
     */
    public static String getUnZipPath()
    {
        return getProfile() + "/unZip";
    }
}
