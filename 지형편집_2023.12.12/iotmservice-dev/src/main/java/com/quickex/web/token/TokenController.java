package com.quickex.web.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quickex.api.verification.PassToken;
import com.quickex.api.verification.UserLoginToken;
import com.quickex.core.redis.RedisCache;
import com.quickex.core.result.R;
import com.quickex.domain.systable.KoTable;
import com.quickex.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ko-token")
public class TokenController {

    @Autowired
    private RedisCache redisCache;

    @PostMapping("/login")
    public R login(@RequestBody String json) {
        return R.success();
    }

    @UserLoginToken
    @PostMapping("/test1")
    public R test1(@RequestBody String json) {

        JSONObject PAR = JSON.parseObject(json);
        JSONObject result = new JSONObject();
        Object sss = redisCache.getCacheObject(PAR.getString("token"));
        result.put("token",sss);
        return R.success(result);
    }

    @PassToken
    @PostMapping("/test2")
    public R test2(@RequestBody String json) {
        String account = UserContext.getUserAccount();
        return R.success(account);
    }

}
