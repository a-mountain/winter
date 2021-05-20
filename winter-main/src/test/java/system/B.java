package system;

import mperevalov.winter.annotations.Bean;

@Bean
public class B {
    private String hello = "Hello, World!";

    public String getHello() {
        return hello;
    }
}
