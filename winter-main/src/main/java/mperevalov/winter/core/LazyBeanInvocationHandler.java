package mperevalov.winter.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LazyBeanInvocationHandler implements InvocationHandler {

    private final BeanFactory factory;
    private final BeanDefinition<?> definition;
    private Object bean;

    public LazyBeanInvocationHandler(BeanDefinition<?> definition, BeanFactory factory) {
        this.definition = definition;
        this.factory = factory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (bean != null) {
            return method.invoke(bean, args);
        }
        synchronized (this) {
            if (bean != null) {
                return method.invoke(bean, args);
            }
            this.bean = factory.createBean(definition);
        }
        return method.invoke(bean, args);
    }
}
