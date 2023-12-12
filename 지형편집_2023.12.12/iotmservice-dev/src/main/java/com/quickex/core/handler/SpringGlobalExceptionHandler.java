package com.quickex.core.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;


import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author chengtianping
 * @description Spring MVC exception handling
 * Two processing mechanisms
 * 1. Directly implement your own handlerexceptionresolver
 * Of course, this also includes using the simplemappingexceptionresolver and defaulthandlerexceptionresolver that spring has provided for us
 * 2. Use annotation to implement a special controller for exception handling -- exceptionhandler
 * @date 2018/5/21
 */
@Slf4j
public class SpringGlobalExceptionHandler extends SimpleMappingExceptionResolver {
    public static final String UTF_8 = "utf-8";
    public static final String ACCEPT = "accept";
    public static final String APPLICATION_JSON = "application/json";
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    @Override
    public ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                           Object o, Exception ex) {
        ex.printStackTrace();
        logger.error("[Exception]:{}==================================",ex.getCause());
        ModelAndView modelAndView;
        httpServletResponse.setCharacterEncoding(UTF_8);
        String viewName = determineViewName(ex,httpServletRequest);
        String accept = httpServletRequest.getHeader(ACCEPT);
        boolean isJsonXMLRequest = accept != null && !(accept.indexOf(APPLICATION_JSON) != -1
                || (httpServletRequest.getHeader(X_REQUESTED_WITH) != null
                && httpServletRequest.getHeader(X_REQUESTED_WITH).indexOf(XML_HTTP_REQUEST) != -1));
        if (!isJsonXMLRequest) {
            //Non JSON XML Ajax requests return the normal view
            Integer statusCode = determineStatusCode(httpServletRequest, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(httpServletRequest, httpServletResponse, statusCode);
            }
            modelAndView = getModelAndView(viewName, ex, httpServletRequest);
        } else {
            PrintWriter writer = null;
            try {
                //JSON XML request Ajax request returns error information in JSON format
                // JSON request return
                httpServletResponse.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
                writer = httpServletResponse.getWriter();
                writer.write(JSONObject.toJSONString("error"));
                writer.flush();
            } catch (IOException e) {
                if (log.isInfoEnabled()) {
                    logger.error("SpringGlobalExceptionHandler exception  ",e);
                }
            }finally{
                if(null != writer){
                    writer.flush();
                    writer.close();
                }
            }
            return null;
        }
        return modelAndView;
    }
}