package dev.ash.core.eh.errorattributes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomErrorHandlerConfig {

  @Bean
  public CustomErrorAttributes customErrorAttributes() {
    return new CustomErrorAttributes();
  }
}
