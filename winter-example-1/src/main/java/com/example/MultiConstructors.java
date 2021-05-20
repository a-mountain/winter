package com.example;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.BeanConstructor;

@Bean
public class MultiConstructors {

    public Action action;

    @BeanConstructor
    public MultiConstructors(Action action) {
        this.action = action;
    }

    public MultiConstructors(int a, int b) {
    }

    @Override
    public String toString() {
        return "MultiConstructors{" +
                "action=" + action +
                '}';
    }
}
