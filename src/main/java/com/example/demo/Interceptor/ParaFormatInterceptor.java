package com.example.demo.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ParaFormatInterceptor implements HandlerInterceptor {
    private static final Pattern SHOULD_NOT_FILTER_URL_PATTERN;
    static {
        List<String> urlList = new ArrayList<>();
        // 将不走拦截器的请求存放到Pattern
        urlList.add("(/img/.*)");
        urlList.add("(/profile/.*)");
        urlList.add("(/volunteering/.*)");
        StringBuilder sb = new StringBuilder();
        for (String url : urlList) {
            sb.append(url);
            sb.append("|");
        }
        sb.setLength(sb.length() - 1);
        SHOULD_NOT_FILTER_URL_PATTERN = Pattern.compile(sb.toString());
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        // 获取访问的url
        String servletPath = request.getServletPath();
        // 排除特定请求
        if (SHOULD_NOT_FILTER_URL_PATTERN.matcher(servletPath).find()) {
            return true;
        }
        if ("GET".equals(request.getMethod())) {
            return true;
        }
        if("application/json".equals(request.getContentType())){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("别发json,谢谢配合");
            return false;
        }
        return true;
    }
//x-www-form-urlencoded
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}