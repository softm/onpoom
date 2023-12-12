package com.quickex.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *  .zip Utils
 */
public class FSZipUtils {

    private static final Logger logger = LoggerFactory.getLogger(FSZipUtils.class);
    private static final int BUFFER_SIZE = 2 * 1024;

    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("zip error from FSZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.equals(""))
            return true;
        else
            return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * decompression
     * zip @param zipFile zip
     *
     * @param unzipFilePath         The path to save the unzipped file
     * @param includeZipFileName    Whether the saved path of the extracted file contains the file name of the compressed file. True - include; False - does not contain
     */
    @SuppressWarnings("unchecked")
    public static void unzip(File zipFile, String unzipFilePath,
                             boolean includeZipFileName) throws Exception { //File zipFile,
        if (zipFile == null) {
            throw new Exception("PARAMETER_IS_NULL");
        }
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (isNotEmpty(fileName)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        //GBK  GB18030  GB2312
        ZipFile zip = new ZipFile(zipFile, Charset.forName("EUC_KR"));

        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            index = entryFilePath.lastIndexOf(File.separator) > entryFilePath.lastIndexOf("/") ? entryFilePath.lastIndexOf(File.separator) : entryFilePath.lastIndexOf("/");
            if (index != -1) {
                entryDirPath = entryFilePath.substring(0, index);
            } else {
                entryDirPath = "";
            }
            entryDir = new File(entryDirPath);
            if (!entryDir.exists() || !entryDir.isDirectory()) {
                entryDir.mkdirs();
            }


            entryFile = new File(entryFilePath);
            if (entryFile.exists()) {
                entryFile.delete();
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                logger.warn("unzip file " + entry.getName() + " exception :" + e);
            }

        }
        zip.close();
    }
}
