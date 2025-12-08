package com.kwezal.bearinmind.exception;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration
public class BearInMindExceptionsConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ControllerExceptionHandler controllerExceptionHandler(ObjectMapper objectMapper) {
        return new ControllerExceptionHandler(objectMapper);
    }
}
