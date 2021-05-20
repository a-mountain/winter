package com.example;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Property;

@Bean
public class NameProperties {

    @Property
    private String name;
    private final String lastname;

    public NameProperties(@Property("lastname") String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "NameProperties{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
