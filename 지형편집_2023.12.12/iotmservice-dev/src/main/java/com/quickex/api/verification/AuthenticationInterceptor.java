package com.quickex.api.verification;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.redis.RedisCache;
import com.quickex.domain.user.KoApiRecord;
import com.quickex.domain.user.KoApiRecordLogin;
import com.quickex.service.user.IKoApiRecordLoginService;
import com.quickex.service.user.IKoApiRecordService;
import com.quickex.utils.UserContext;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

@Log4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private IKoApiRecordService apiRecordService;

    @Resource
    private IKoApiRecordLoginService apiRecordLoginService;

    @Resource
    private RedisCache redisCache;

    private ThreadLocal<KoApiRecord> record = new ThreadLocal<>();

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    //begin
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        // If not mapped to a method, directly through
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        String token = httpServletRequest.getHeader("token");
        String account = httpServletRequest.getHeader("account");

        UserContext.setUserAccount(account);

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

//        if (!method.isAnnotationPresent(PassToken.class)) {
//
//            Boolean tokenResult = true;
//
//            if (token == null || token.equals("") || account == null || account.equals("")) {
//                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpServletResponse.setCharacterEncoding("UTF-8");
//                httpServletResponse.setContentType("application/json; charset=utf-8");
//                JSONObject res = new JSONObject();
//                res.put("code", 401);
//                res.put("data", null);
//                res.put("msg", "token validation failed");
//                PrintWriter out = null;
//                out = httpServletResponse.getWriter();
//                out.write(res.toString());
//                out.flush();
//                out.close();
//            }
//
//            Object userAccount = redisCache.getCacheObject(token);
//
//            if (userAccount == null || !userAccount.toString().equals(account)) {
//                tokenResult = false;
//            }
//
//            if (token.equals("test123456") && account.equals("test123456")) {
//                tokenResult = true;
//            }
//
//            if (tokenResult == false) {
//                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpServletResponse.setCharacterEncoding("UTF-8");
//                httpServletResponse.setContentType("application/json; charset=utf-8");
//                JSONObject res = new JSONObject();
//                res.put("code", 401);
//                res.put("data", null);
//                res.put("msg", "token validation failed");
//                PrintWriter out = null;
//                out = httpServletResponse.getWriter();
//                out.write(res.toString());
//                out.flush();
//                out.close();
//                return false;
//            }
//
//        }


        //record
        String requestAccount = account;
        String requestIp = getIpAddr(httpServletRequest);
        String requestServletPath = httpServletRequest.getServletPath(); // /api/httpForward/lx_uav_api
        //String requestMethod = httpServletRequest.getMethod();  //post

        if (requestAccount == null || requestAccount.equals("")) {
            requestAccount ="admin";
        }

        KoApiRecord apiRecord = new KoApiRecord();
        apiRecord.setAccount(requestAccount);
        apiRecord.setIp(requestIp);
        apiRecord.setApi(requestServletPath);
        apiRecord.setTime(new Date());

        record.set(apiRecord);
        startTimeThreadLocal.set(System.currentTimeMillis());


        return true;
    }

    //end
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            Integer endTime = (int) (System.currentTimeMillis() - startTimeThreadLocal.get());
            record.get().setTimes(endTime);
            apiRecordService.save(record.get());

//            if(record.get().getApi().equals("/api/ko-user/mapLogin")||record.get().getApi().equals("/api/ko-user/adminLogin")){
//                KoApiRecordLogin LOG = new  KoApiRecordLogin();
//                LOG.setId(record.get().getId());
//                LOG.setAccount(record.get().getAccount());
//                LOG.setIp(record.get().getIp());
//                LOG.setApi(record.get().getApi());
//                LOG.setTime(record.get().getTime());
//                LOG.setTimes(endTime);
//                apiRecordLoginService.save(LOG);
//            }

//            Map result = (Map) request.getSession().getAttribute("body");
//            String code = result.get("code").toString();   //result R.code

        } catch (Exception ex) {
            log.error("postHandle", ex);
        }
    }

    //over clear
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        record.remove();
        startTimeThreadLocal.remove();
        UserContext.remove();
    }


    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            String localIp = "127.0.0.1";
            String localIpv6 = "0:0:0:0:0:0:0:1";
            if (ipAddress.equals(localIp) || ipAddress.equals(localIpv6)) {
                // Take the configured IP of the machine according to the network card
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // In the case of multiple agents, the first IP is the real IP of the client, and multiple IPS are divided according to ','
        String ipSeparate = ",";
        int ipLength = 15;
        if (ipAddress != null && ipAddress.length() > ipLength) {
            if (ipAddress.indexOf(ipSeparate) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(ipSeparate));
            }
        }
        return ipAddress;
    }
}
