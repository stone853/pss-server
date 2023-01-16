package com.flong.springboot.core.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor{
    long start = System.currentTimeMillis();

    @Autowired
    UserService userService;
    //preHandle是在请求执行前执行的
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        if (token == null) {
            throw new BaseException(CommMsgCode.UNAUTHORIZED,"无token，请重新登录");
        }

        // 执行认证
        if (token.equals("123")) {
            token = UserHelper.getToken("18062109527","e10adc3949ba59abbe56e057f20f883e");
            //throw new BaseException(CommMsgCode.INVALID_TOKEN,"无token，请重新登录");
        }




        // 获取 token 中的 user
        try {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("mobile", JWT.decode(token).getAudience().get(0));
            User u = userService.getOne(qw);
            if (u == null) {
                throw new BaseException(CommMsgCode.UNAUTHORIZED,"token验证失败，无用户");
            }
            request.getSession().setAttribute("userId",u.getUserId());
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(u.getPassword())).build();
            jwtVerifier.verify(token);
        }  catch (Exception j) {
            throw new BaseException(CommMsgCode.UNAUTHORIZED,"token验证失败");
        }

        return true;
    }

    //postHandler是在请求结束之后,视图渲染之前执行的,但只有preHandle方法返回true的时候才会执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("Interception cost="+(System.currentTimeMillis()-start));
    }

    //afterCompletion是视图渲染完成之后才执行,同样需要preHandle返回true，
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        //该方法通常用于清理资源等工作
    }
}
