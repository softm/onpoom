package com.quickex.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Base64FileUtils {

//    private static String targetFilePath = "E:\\base2Img\\target\\test.txt";
//
//    public static void main(String[] args) throws Exception {
//        String fileStr = getFileStr("E:\\base2Img\\big test.txt");
//        System.out.println("fileStr ===" + fileStr);
//        System.out.println(generateFile(fileStr, targetFilePath));
//        System.out.println("end");
//    }


    /**
     *      * Convert file to Base64 string
     *      * Convert the file into byte array string and encode it with Base64
     *      
     */
    public static String getFileStr(String filePath) {
        InputStream in = null;
        byte[] data = null;
        // Read file byte array
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Encode byte array Base64
        BASE64Encoder encoder = new BASE64Encoder();
        // Returns a Base64 encoded byte array string
        return encoder.encode(data);
    }

    /**
     *      * Base64 string is converted into a file, which can be JPEG, PNG, TXT, avi, etc
     *      *
     *      * @param base64FileStr
     *      * @param filePath
     *      * @return
     *      * @throws Exception
     *      
     */
    public static boolean generateFile(String base64FileStr, String filePath) throws Exception {
        // data is null
        if (base64FileStr == null) {
            System.out.println(" not，oops！ ");
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();


        // Base64 decoding: Base64 decodes the byte array string and generates a file
        byte[] byt = decoder.decodeBuffer(base64FileStr);
        for (int i = 0, len = byt.length; i < len; ++i) {
            // Adjust exception data
            if (byt[i] < 0) {
                byt[i] += 256;
            }
        }
        OutputStream out = null;
        InputStream input = new ByteArrayInputStream(byt);
        try {
            // Generates a file in the specified format
            out = new FileOutputStream(filePath);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = input.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
        return true;
    }

}
