package com.example;

import mperevalov.winter.annotations.Bean;

@Bean
public class ConstructorInjection {

    public NameProperties properties;

    public ConstructorInjection(NameProperties properties) {
        this.properties = properties;
    }
}
