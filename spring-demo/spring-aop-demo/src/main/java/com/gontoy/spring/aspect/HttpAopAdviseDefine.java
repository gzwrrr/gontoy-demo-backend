package com.gontoy.spring.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class HttpAopAdviseDefine {

    /**
     * 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise
     */
    @Pointcut("@annotation(com.gontoy.spring.annotation.AuthChecker)")
    public void pointcut() {
    }

    /**
     * 定义 advice
     */
    @Around("pointcut()")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        // 检查用户所传递的 token 是否合法
        String token = getUserToken(request);
        if (!token.equalsIgnoreCase("123456")) {
            return "错误, 权限不合法!";
        }

        return joinPoint.proceed();
    }


    /**
     * 获取 Token
     */
    private String getUserToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("user_token")) {
                return cookie.getValue();
            }
        }
        return "";
    }

}
