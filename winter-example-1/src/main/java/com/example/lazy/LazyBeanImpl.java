package com.example.lazy;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Lazy;

@Bean
@Lazy
public class LazyBeanImpl implements LazyBeanInterface {
    LazyBeanImpl() {
        System.out.println("Lazy bean is created");
    }

    @Override
    public void doSomething() {
        System.out.println("Do something");
    }
}
