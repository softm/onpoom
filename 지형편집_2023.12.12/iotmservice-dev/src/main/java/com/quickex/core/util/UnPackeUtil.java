package com.quickex.core.util;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;

import java.io.File;


/**
 * @Description: Unzip the RAR / zip utility class
 * @Date: 2019/1/22
 * @Auther:
 */

@Slf4j
public class UnPackeUtil {

    /**
     * Zip file decompression
     * @param destPath Unzip file path
     * @param zipFile  Compressed file
     */

    public static void unPackZip(File zipFile, String destPath) {

        try {
            ZipFile zip = new ZipFile(zipFile);
            /*Zip4j uses GBK encoding to decompress by default. Here, the encoding is set to GBK*/
            zip.setFileNameCharset("UTF-8");
            log.info("begin unpack zip file....");
            zip.extractAll(destPath);
            // If a password is required for decompression
            if (zip.isEncrypted()) {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("unPack zip file to " + destPath + " fail ....", e.getMessage(), e);
        }
    }


}
