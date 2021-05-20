package com.example;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.InjectByType;

@Bean
public class FieldInjection {

    @InjectByType
    public NameProperties properties;
}
