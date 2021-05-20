package com.example;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Log;
import mperevalov.winter.annotations.Primary;

@Bean
@Primary
public class SumAction implements Action {

    @Log
    @Override
    public int invoke(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return "SumAction{}";
    }
}
