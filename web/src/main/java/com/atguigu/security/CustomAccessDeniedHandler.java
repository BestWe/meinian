package com.atguigu.security;

import com.alibaba.fastjson.JSON;
import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /*判断是否是ajax请求*/
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request.getHeader("accept").contains("application/json") || request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        if (isAjaxRequest(httpServletRequest)) {
            Result result = new Result(false, MessageConstant.NO_ACCESS);
            String s = JSON.toJSONString(result);
            httpServletResponse.getWriter().print(s);
        } else {
            //同步请求就跳转至403页面
            httpServletRequest.getRequestDispatcher("/pages/error/403.html").forward(httpServletRequest, httpServletResponse);
        }
    }
}
