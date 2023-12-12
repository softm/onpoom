package com.quickex.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *  MD5
 */
public class MD5Utils {

    private static  final String Key="1234567890";

    /**
     * MD5 funciton
     *
     * @param text Plaintext
     * @return ciphertext
     * @throws Exception
     */
    public static String md5(String text)  {
        //Encrypted string
        try{
            String encodeStr= DigestUtils.md5Hex(text + Key);
            System.out.println("MD5 encrypted string is:encodeStr="+encodeStr);
            return encodeStr;
        }catch (Exception ex){
            return "";
        }
    }

    /**
     * MD5 verification method
     *
     * @param text Plaintext
     * @param md5 ciphertext
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String md5) {
        //Validate against the incoming key
        try{
            String md5Text = md5(text);
            if(md5Text.equalsIgnoreCase(md5))
            {
                System.out.println("MD5 validation passed");
                return true;
            }
            return false;
        }catch (Exception ex){
            return false;
        }
    }
}
