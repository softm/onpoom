package com.quickex.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java Ping ip
 */
public class PingUtil {

    public static final String LINUX = "Linux";
    public static final String WINDOWS = "Windows";

    public static void main(String[] args) throws Exception {
        String ipAddress = "127.0.0.1";
        System.out.println(ping(ipAddress, 5, 3));
        System.out.println(pingForLinux(ipAddress, 5, 3));

//        String host = "192.168.1.192";
//        int port = 4001;
//
//        System.out.println(connect(host, port, 3000));

        String pingStr = "64 bytes from 127.0.0.1: icmp_seq=1 tttl=64 time=0.015 ms";
        System.out.println(getCheckResultForLinux(pingStr));
    }

    /**
     *  check ip and port
     * @param host
     * @param port
     * @param timeOut ms
     * @return
     */
    public static boolean connect(String host, int port, int timeOut){
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port),timeOut);
            //boolean res = socket.isConnected();//
        } catch (IOException e) {
            //
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(socket!=null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        try {
            Properties props=System.getProperties(); //
            String osName = props.getProperty("os.name"); //
            String osArch = props.getProperty("os.arch"); //
            String osVersion = props.getProperty("os.version"); //
            System.out.println(osName);
            System.out.println(osArch);
            System.out.println(osVersion);
            Boolean result = false;
            if(osName.contains(LINUX)){
                result = pingForLinux(ipAddress, pingTimes, timeOut);
            }else if(osName.contains(WINDOWS)){
                result = pingForWindows(ipAddress, pingTimes, timeOut);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            return false;
        }
    }


    public static boolean pingForWindows(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        InputStreamReader inputStreamReader = null;
        Runtime r = Runtime.getRuntime();
        try {
            String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut*1000;
            //
            //System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            //
            inputStreamReader = new InputStreamReader(p.getInputStream());
            in = new BufferedReader(inputStreamReader);
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResultForWindows(line);
            }
            //
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   //
            return false;
        } finally {
            try {
                if (in!=null){
                    in.close();
                }
                if (inputStreamReader!=null){
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static boolean pingForLinux(String ipAddress, int pingTimes, int timeOut) {
        Process process = null;
        InputStreamReader r = null;
        LineNumberReader returnData = null;
        try {
            //String pingCommandStr = "timeout "+timeOut+"s "+"ping " + ipAddress + " -c " + pingTimes + " -i 0";
            String pingCommandStr = "ping " + ipAddress + " -c " + pingTimes;
            process = Runtime.getRuntime().exec(pingCommandStr);
            r = new InputStreamReader(process.getInputStream());
            returnData = new LineNumberReader(r);
            int connectedCount = 0;
            String line = "";
            while ((line = returnData.readLine()) != null) {
                //log.info("line1===="+line);
                connectedCount += getCheckResultForLinux(line);
            }
            //log.info("connectedCount===="+connectedCount);
            return connectedCount == pingTimes;
        }catch (IOException e) {
            //log.error("System error: ", e);
            return false;
        } finally{
            try {
                if(returnData != null){
                    returnData.close();
                }
                if(r != null){
                    r.close();
                }
            } catch (IOException e) {
                //log.error("System error: ", e);
            }
        }
    }


    private static int getCheckResultForWindows(String line) {
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }


    private static int getCheckResultForLinux(String line) {
        Pattern pattern = Pattern.compile("(ttl=\\d+)(\\s+)(time=+\\w)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

}

