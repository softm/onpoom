package com.quickex.core.result;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * Operation message reminder
 *
 * @author ffzh.thero
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * Status code
     */
    public static final String CODE_TAG = "code";

    /**
     * Return content
     */
    public static final String MSG_TAG = "msg";

    /**
     * data object
     */
    public static final String DATA_TAG = "data";

    /**
     * Initialize a newly created Ajax result object to represent an empty message.
     */
    public R() {
    }

    /**
     * Initialize a newly created Ajax result object
     *
     * @param code
     * @param msg
     */
    public R(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * Initialize a newly created Ajax result object
     *
     * @param code
     * @param msg
     * @param data
     */
    public R(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
//        if (ObjectUtil.isNotNull(data)) {
////            super.put(DATA_TAG, data);
////        }
        super.put(DATA_TAG, data);
    }

    /**
     * Return success message
     *
     * @return
     */
    public static R success() {
        return R.success("성공적인 작업");
    }

    /**
     * Return success message
     *
     * @return
     */
    public static R success(Object data) {
        return R.success("성공적인 작업", data);
    }

    /**
     * Return success message
     *
     * @param msg
     * @return
     */
    public static R success(String msg) {
        return R.success(msg, null);
    }

    /**
     * Return success message
     *
     * @param msg
     * @param data
     * @return
     */
    public static R success(String msg, Object data) {
        return new R(HttpStatus.HTTP_OK, msg, data);
    }

    /**
     * Return error message
     *
     * @return
     */
    public static R error() {
        return R.error("작업 실패");
    }

    /**
     * Return error message
     *
     * @param msg
     * @return
     */
    public static R error(String msg) {
        return R.error(msg, null);
    }

    /**
     * Return error message
     *
     * @param msg
     * @param data
     * @return
     */
    public static R error(String msg, Object data) {
        return new R(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }

    /**
     * Return error message
     *
     * @param code
     * @param msg
     * @return
     */
    public static R error(int code, String msg) {
        return new R(code, msg, null);
    }



}
