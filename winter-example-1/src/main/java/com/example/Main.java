package com.example;

import com.example.lazy.LazyBeanInterface;
import mperevalov.winter.core.ApplicationContext;
import mperevalov.winter.core.WinterApplication;

public class Main {

    ApplicationContext context;

    public Main(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        var context = WinterApplication.run("");
        var main = new Main(context);
        main.log();
    }

    void fieldInjection() {
        var fieldInjection = context.getBean(FieldInjection.class);
        System.out.println(fieldInjection.properties);
    }

    void constructorInjection() {
        var bean = context.getBean(ConstructorInjection.class);
        System.out.println(bean.properties);
    }

    void primary() {
        var bean = context.getBean(Action.class);
        System.out.println(bean.toString());
    }

    void multiConstructor() {
        var bean = context.getBean(MultiConstructors.class);
        System.out.println(bean.toString());
    }

    void log() {
        var bean = context.getBean(Action.class);
        bean.invoke(1, 1);
    }

    void lazy() {
        System.out.println("Get bean");
        var bean = context.getBean(LazyBeanInterface.class);
        System.out.println("Invoke method");
        bean.doSomething();
        bean.doSomething();
    }
}
