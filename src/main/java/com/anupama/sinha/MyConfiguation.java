package com.anupama.sinha;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguation {
    @Bean
    StringUtils stringUtils(){
        return new StringUtils();
    }
}
