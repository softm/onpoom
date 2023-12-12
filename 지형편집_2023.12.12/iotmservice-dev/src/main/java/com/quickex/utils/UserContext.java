package com.quickex.utils;

import java.util.HashMap;
import java.util.Map;

public class UserContext {

    private static ThreadLocal<Map<String, Object>> threadLocal;

    public static final String CONTEXT_KEY_USER_ACCOUNT = "account";

    static {
        threadLocal = new ThreadLocal<>();
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static void setUserAccount(String account) {
        set(CONTEXT_KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        Object value = get(CONTEXT_KEY_USER_ACCOUNT);
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        return map.get(key);
    }

}
