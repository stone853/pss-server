package com.flong.springboot.core.interceptor;


import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor{
    long start = System.currentTimeMillis();
//    @Bean
//    public MembershipService getMembershipService() {
//        return new MembershipImpl();
//    }
    //preHandle是在请求执行前执行的
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
//        String token = request.getHeader("token");// 从 http 请求头中取出 token
//        // 如果不是映射到方法直接通过
//        if(!(handler instanceof HandlerMethod)){
//            return true;
//        }
//
//        // 执行认证
//        if (token == null) {
//            throw new BizException("401","无token，请重新登录");
//        }
//        // 获取 token 中的 user name
//        Membership t = new Membership();
//        try {
//            t.setPhone(JWT.decode(token).getAudience().get(0));
//        } catch (JWTDecodeException j) {
//            throw new BizException("401","token获取用户信息失败");
//        }
//        //MembershipService service = getMembershipService();
//        ResultListModel<Membership> resultModel = service.selectOne(t);
//        if (resultModel.getCode() <0 || null == resultModel.getList() || resultModel.getList().size() ==0) {
//            throw new BizException("401","用户不存在，请重新登录");
//        }
//
//        // 验证 token
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(resultModel.getList().get(0).getPassword())).build();
//        try {
//            jwtVerifier.verify(token);
//        } catch (JWTVerificationException e) {
//            throw new BizException("401","token验证失败");
//        }

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
