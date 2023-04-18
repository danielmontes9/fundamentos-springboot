package com.fundamentos.springboot.fundamentos.configuration;

import com.fundamentos.springboot.fundamentos.been.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithPropertiesImplement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfiguration {
    @Value("${value.name}")
    private String name;

    @Value("${value.surname}")
    private String surname;

    @Value("${value.random}")
    private String random;

    @Bean
    public MyBeanWithProperties function() {
        return new MyBeanWithPropertiesImplement(name, surname);
    }
}
