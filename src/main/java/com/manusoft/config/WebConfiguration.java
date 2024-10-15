package com.manusoft.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Configures the path matching to use trailing slash match.
     *
     * @param configurer the PathMatchConfigurer to configure
     */
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.setUseTrailingSlashMatch(true);
    }
}
