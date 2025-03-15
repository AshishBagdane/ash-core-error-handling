package com.ashishbagdane.lib.eh.handler;

import com.ashishbagdane.lib.eh.metrics.ErrorMetrics;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for error handling in the application.
 */
@Configuration
public class ErrorHandlingConfig implements WebMvcConfigurer {

    @Bean
    public ErrorAttributes errorAttributes(ErrorResponseBuilder errorResponseBuilder,
                                           ErrorMetrics errorMetrics) {
        return new ApplicationErrorAttributes(errorResponseBuilder, errorMetrics);
    }

    @Bean
    public ErrorResponseBuilder errorResponseBuilder() {
        return new ErrorResponseBuilder();
    }
}
