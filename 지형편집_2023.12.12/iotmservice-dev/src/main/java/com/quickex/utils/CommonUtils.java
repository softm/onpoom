package com.quickex.utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Common function tool classes
 */
public class CommonUtils {

    /**
     * get UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getUUID1(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * Calculate the number of seconds between two dates
     *
     * @param startDate  start
     * @param endDate    end
     * @return
     */
    public static int calLastedTime(Date startDate, Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }

    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * Gets the string of the current date
     * @param type 1:yyyy-MM-dd
     *             2:yyyy-MM-dd HH:mm:ss
     *             3：yyyy-MM-dd HH:mm:ss.SSS
     *             4：HH:mm:ss
     *             5：HH:mm:ss.SSS
     *             6:yyyyMMddHHmmssSSS
     *             7:yyyyMMdd
     *             8:HHmmssSSS
     * @return
     */
    public static String currentDateToStr(int type) {
        Date dNow = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat format4 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format5 = new SimpleDateFormat("HH:mm:ss.SSS");
        SimpleDateFormat format6 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleDateFormat format7 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format8 = new SimpleDateFormat("HHmmssSSS");
        String str = "";
        switch (type){
            case 1:
                str = format1.format(dNow);
                break;
            case 2:
                str = format2.format(dNow);
                break;
            case 3:
                str = format3.format(dNow);
                break;
            case 4:
                str = format4.format(dNow);
                break;
            case 5:
                str = format5.format(dNow);
                break;
            case 6:
                str = format6.format(dNow);
                break;
            case 7:
                str = format7.format(dNow);
                break;
            case 8:
                str = format8.format(dNow);
                break;
        }
        return str;
    }

    public static String currentDateToStr(int type,Date dNow) {

        if (dNow == null) {
            return "";
        }

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat format4 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format5 = new SimpleDateFormat("HH:mm:ss.SSS");
        String str = "";
        switch (type){
            case 1:
                str = format1.format(dNow);
                break;
            case 2:
                str = format2.format(dNow);
                break;
            case 3:
                str = format3.format(dNow);
                break;
            case 4:
                str = format4.format(dNow);
                break;
            case 5:
                str = format5.format(dNow);
                break;
        }
        return str;
    }


    /**
     * Print hexadecimal string
     * @param data byte[]
     * @return
     */
    public static String printHexBinary(byte[] data) {
        char[] hexCode = "0123456789ABCDEF".toCharArray();
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
            r.append(" ");
        }
        return r.toString().trim();
    }

    /**
     * padLeft  Left complement of string
     * @param src str
     * @param len Specify length
     * @param ch  Complement character
     * @return
     */
    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    /**
     * Right padding of string
     * @param src str
     * @param len Specify length
     * @param ch  Complement character
     * @return
     */
    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    /**
     * Serialize an object object and return a byte []
     * @param obj object
     * @return
     */
    public static byte[] objectToBytes(Object obj){
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.flush();
            bytes= out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Restore a serialized byte [] array
     * @param bytes byte[]
     * @return
     */
    public static Object bytesToObject(byte[] bytes) {
        Object t = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn;
        try {
            sIn = new ObjectInputStream(in);
            t = (Object)sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Gets a list of dates within a time period
     * @param beginDate yyyy-mm-dd
     * @param endDate yyyy-mm-dd
     * @return yyyy-mm-dd
     */
    public static List<String> getDayList(String beginDate, String endDate){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> lDate = new ArrayList<>();
        try {
            Date can1 = df.parse(beginDate);
            Date can2 = df.parse(endDate);
            lDate.add(can1);
            Calendar cal = Calendar.getInstance();
            cal.setTime(can1);
            boolean bContinue = true;
            while (bContinue) {

                cal.add(Calendar.DAY_OF_MONTH, 1);

                if (can2.after(cal.getTime())) {
                    lDate.add(cal.getTime());
                } else {
                    break;
                }
            }
            if (!sameDate(can1, can2)) {
                lDate.add(can2);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> date = new ArrayList<>();
        for (int i = 0; i < lDate.size(); i++) {
            date.add(df.format(lDate.get(i)));
        }
        return date;
    }

    private static boolean sameDate(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        //fmt.setTimeZone(new TimeZone());
        return fmt.format(d1).equals(fmt.format(d2));
    }


    /**
     * Get a list of months in a time period
     * @param sratr yyyy-mm
     * @param end  yyyy-mm
     * @return yyyy-mm
     */
    public static List<String> getMonthList(String sratr, String end) {

        try {
            Date startDate = new SimpleDateFormat("yyyy-MM").parse(sratr);
            Date endDate = new SimpleDateFormat("yyyy-MM").parse(end);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // get start
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            // getend
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            //
            List<String> list = new ArrayList<String>();
            for (int i = startYear; i <= endYear; i++) {
                String date = "";
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "-0" + (j + 1);
                        } else {
                            date = i + "-" + (j + 1);
                        }
                        list.add(date);
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    }

                }

            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get the first day of the month
     * @return
     */
    public static  Date getmindate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    /**
     * Get the last day of the month
     * @return
     */
    public static  Date getmaxdate(){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar2.getTime();
    }

    public static List<String> getDatesInMonth(String targetMonth) {
        List<String> dateList = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(targetMonth + "-01");
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!startDate.isAfter(endDate)) {
            dateList.add(startDate.format(formatter));
            startDate = startDate.plusDays(1);
        }

        return dateList;
    }

    public static List<String> getDatesInYear() {
        List<String> times = new ArrayList<>();
        times.add("01");
        times.add("02");
        times.add("03");
        times.add("04");
        times.add("05");
        times.add("06");
        times.add("07");
        times.add("08");
        times.add("09");
        times.add("10");
        times.add("11");
        times.add("12");
        return times;
    }

    public static List<String> getDatesInDay() {
        List<String> times = new ArrayList<>();
        times.add("00");
        times.add("01");
        times.add("02");
        times.add("03");
        times.add("04");
        times.add("05");
        times.add("06");
        times.add("07");
        times.add("08");
        times.add("09");
        times.add("10");
        times.add("11");
        times.add("12");
        times.add("13");
        times.add("14");
        times.add("15");
        times.add("16");
        times.add("17");
        times.add("18");
        times.add("19");
        times.add("20");
        times.add("21");
        times.add("22");
        times.add("23");
        return times;
    }


}
