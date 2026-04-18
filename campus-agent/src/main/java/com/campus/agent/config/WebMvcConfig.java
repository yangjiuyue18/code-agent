package com.campus.agent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置：支持 SPA（单页应用）路由
 * 所有非 API 请求都重定向到 index.html
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源已在 application.yml 配置：classpath:/static/
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 非 API 路径返回 index.html（支持 Vue Router history 模式）
        registry.addViewController("/{path:[^\\.]*}")
                .setViewName("forward:/index.html");
    }
}
