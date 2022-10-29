package com.wanan.webgoat.container;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public AjaxAuthenticationEntryPoint(String loginFormUrl){
        super(loginFormUrl);
    }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException{
        if (request.getHeader("x-requested-with")!=null){
//            x-requested-with 请求头 区分ajax请求还是普通请求
            response.sendError(401,authenticationException.getMessage());
        }else {
            super.commence(request,response,authenticationException);
        }
    }

}
