package common.interceptor;

import common.entity.RedisKey;
import common.entity.StatusEnum;
import common.exception.MyException;
import common.util.JwtUtil;
import common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import system.util.CurrentLoginUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 前端的post，put，delete请求仍然是跨域异常。
 * 前后端分离的代码，对三种方法的请求，每次都是先提交一个预检请求，检查服务器是否支持跨域。
 * method:OPTIONS
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private CurrentLoginUser cu;

    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            return true;
        }
        //从header中获取token
        String token = request.getHeader("token");
        if (redisUtil.exists(RedisKey.BLACKLIST_KEY + token)) {
            throw new MyException(StatusEnum.TOKEN_INVALID);
        }
        //前端已做校验，此处再做校验，防止恶意调用接口
        if (token != null) {
            Claims claim = JwtUtil.getClaim(token);
            //如果token失效，取出来的claim为null
            if (claim != null) {
                Object uphone = claim.get("uphone");
                //可以获取登录用户信息
                cu.setLoginUser((String) uphone);
                String s = refreshToken(claim);
                if (s != null) response.addHeader("token", s);
            } else {
                //token已过期
                throw new MyException(StatusEnum.TOKEN_EXPIRE);
            }
        } else {
            throw new MyException(StatusEnum.NO_LOGIN);
        }
    /*
     todo  如果第一次登陆，获取其权限列表，加入session中；
     否则获取其请求url
      */
        return true;
    }

    private String refreshToken(Claims claim) {
        Date nowDate = new Date();
        Date expiration = claim.getExpiration();
        //如果剩余有效时间仅剩3分钟的话，刷新token
        if ((expiration.getTime() - nowDate.getTime()) <= 3 * 60 * 1000) {
            //要刷新token
            Object uphone = claim.get("uphone");
            Map<String, Object> map = new HashMap<>();
            map.put("uphone", uphone);
            String token = JwtUtil.generate(map);
            return token;
        }
        return null;
    }

/*
做权限处理
每次登进一个用户，查看其角色，通过角色获取
 */
}

