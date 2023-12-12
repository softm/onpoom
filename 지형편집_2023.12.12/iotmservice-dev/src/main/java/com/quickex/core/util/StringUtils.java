package com.quickex.core.util;


import com.quickex.core.text.StrFormatter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String utility class
 *
 * @author ffzh.thero
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static Pattern AZ_PATTERN = Pattern.compile("[A-Z]");
    private static Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");


    private static final String NULLSTR = "";

    private static final char SEPARATOR = '_';


    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }


    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }


    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }


    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }


    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }


    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }


    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }


    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }


    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    public static boolean isNull(Object object) {
        return object == null;
    }


    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }


    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }


    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }


    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }


    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }


    public static final Set<String> str2Set(String str, String sep) {
        return new HashSet<String>(str2List(str, sep, true, false));
    }


    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        //
        if (filterBlank && StringUtils.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && StringUtils.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }


    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        //
        boolean preCharIsUpperCase = true;
        //
        boolean curreCharIsUpperCase = true;
        //
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }


    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();

        if (name == null || name.isEmpty()) {

            return "";
        } else if (!name.contains("_")) {

            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        String[] camels = name.split("_");
        for (String camel : camels) {

            if (camel.isEmpty()) {
                continue;
            }

            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }


    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static List<String> getSubUtil(String soap, String rgex) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);//
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }


    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);//
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String underLineLetter(String str, String result) {

        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();

            result += matcher.group(1).toLowerCase(Locale.ROOT);
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());

            matcher.appendTail(sb);
        } else {
            return str.charAt(0)+"" + result;
           // return sb.toString();
        }
        return underLineLetter(sb.toString(),result);
    }

    public static String unicodeToCn(String unicode) {

        String[] strs = unicode.split("\\\\u");
        String returnStr = "";

        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }


    private static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}