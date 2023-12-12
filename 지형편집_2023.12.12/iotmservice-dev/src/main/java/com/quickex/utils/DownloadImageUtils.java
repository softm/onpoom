package com.quickex.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageUtils {

    public static void main(String[] args){
//        writeImageToDisk(
//                "http://220.120.28.20:8087/share-files/files/ScrnCaptImgs/20221026095355/20221026/result.png"
//                ,"D:/A_TEST/uav-model/admin/20221026/xxx/123.png"
//        );

        writeImageToDisk(
                "http://1.212.36.102:8087/share-files/permit/1674006543730/file_1674006546187.xlsx"
                ,"D:/A_TEST/A_TEST/name.xlsx"
        );
    }

    public static boolean writeImageToDisk(String url, String fileName) {
        try {
            byte[] data = getImageFromNetByUrl(url);
            File file = new File(fileName);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
                file.createNewFile();
            }
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(data);
            fops.flush();
            fops.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] btData = readInputStream(inStream);
            return btData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}
