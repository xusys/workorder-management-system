package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 使用 CorsFilter 解决跨域访问问题
 * 由于在SpringMVC中，自定义拦截器 的优先级高于 框架自带的拦截器，而本项目中使用了自定义的 JWT拦截器 JwtInterceptor
 * 因此请求将无法到达 拦截器CorsRegistry，所以无法在 WebConfig配置文件中使用 addCorsMappings(CorsRegistry registry)解决跨域访问问题
 * 而在SpringMVC中，过滤器(filter)的优先级高于拦截器(interceptor)，所以本项目使用 CorsFilter 来解决跨域访问问题
 */
@Configuration
public class CorsConfig {
    // 过滤器配置
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); // 设置允许跨域请求的域名
        corsConfiguration.addAllowedHeader("*");        // 允许任何头
        corsConfiguration.addAllowedMethod("*");        // 设置允许的方法
        corsConfiguration.setAllowCredentials(true);    // 是否允许证书
        return corsConfiguration;
    }

    // 过滤器方法
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
