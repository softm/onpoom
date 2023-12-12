package com.quickex.api.verification;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    private static final String LOGIN_URI = "/login";

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        String requestPath = request.getURI().getPath();
        //Get request through requestcontextholder
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("body", body);

        return body;

//        //Gets the return value of the specified provider
//        if(requestPath.equals(LOGIN_URI)){
//            //Get request through requestcontextholder
//            HttpServletRequest httpServletRequest =
//                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//
//            HttpSession httpSession = httpServletRequest.getSession(true);
//            httpSession.setAttribute("body", body);
//
//            return body;
//        }
//        return body;
    }
}
