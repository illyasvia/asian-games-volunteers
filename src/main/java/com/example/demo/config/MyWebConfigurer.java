package com.example.demo.config;

import com.example.demo.Interceptor.ParaFormatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: MyWebConfigurer
 * Description:
 * date: 2022/5/26 22:29
 *
 * @author zyu
 * @since JDK 1.8
 */
@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Autowired
    private ParaFormatInterceptor paraFormatInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paraFormatInterceptor).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
