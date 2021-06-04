/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.maxkey.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.maxkey.web.interceptor.HistoryLogsAdapter;
import org.maxkey.web.interceptor.PermissionAdapter;
import org.maxkey.web.interceptor.RestApiPermissionAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableWebMvc
public class MaxKeyMgtMvcConfig implements WebMvcConfigurer {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtMvcConfig.class);
    @Autowired
    PermissionAdapter permissionAdapter;

    @Autowired
    HistoryLogsAdapter historyLogsAdapter;

    @Autowired
    LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    RestApiPermissionAdapter restApiPermissionAdapter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        _logger.debug("add addResourceHandler");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
        //excludePathPatterns 表示改路径不用拦截
        registry.addInterceptor(permissionAdapter)
                .addPathPatterns("/auth/manage/**")
                .addPathPatterns("/auth/osskey/userInfo")
                .addPathPatterns("/main/**")
                .addPathPatterns("/orgs/**")
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/apps/**")
                .addPathPatterns("/app/accounts/**")
                .addPathPatterns("/groups/**")
                .addPathPatterns("/groupMember/**")
                .addPathPatterns("/groupPrivileges/**")
                .addPathPatterns("/roles/**")
                .addPathPatterns("/rolemembers/**")
                .addPathPatterns("/resources/**")
                .addPathPatterns("/permissions/**")
                .addPathPatterns("/config/**")
                .addPathPatterns("/logs/**")
                ;

        _logger.debug("add PermissionAdapter");

        registry.addInterceptor(historyLogsAdapter)
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/enterprises/**")
                .addPathPatterns("/employees/**")
                .addPathPatterns("/authInfo/**")
                .addPathPatterns("/usercenter/**")
                .addPathPatterns("/retrievePassword/**")
                .addPathPatterns("/roles/**")
                .addPathPatterns("/apps/**")
                .addPathPatterns("/approles/**")
                ;
        _logger.debug("add HistoryLogsAdapter");

        registry.addInterceptor(localeChangeInterceptor);
        _logger.debug("add LocaleChangeInterceptor");


        registry.addInterceptor(restApiPermissionAdapter)
                .addPathPatterns("/identity/api/**")
                ;

        _logger.debug("add RestApiPermissionAdapter");

    }
    @Bean(name = "mapperObject")
    public ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        om.registerModule(javaTimeModule);
        return om;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        };
    }




}
