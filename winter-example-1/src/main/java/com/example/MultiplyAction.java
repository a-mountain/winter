package com.example;

import mperevalov.winter.annotations.Bean;

@Bean
public class MultiplyAction implements Action {
    @Override
    public int invoke(int a, int b) {
        return a * b;
    }

    @Override
    public String toString() {
        return "MultiplyAction{}";
    }
}
