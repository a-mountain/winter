package mperevalov.winter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ReflectionHelper {

    private ReflectionHelper() {
    }

    public static void set(Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createProxy(Class<?> type, InvocationHandler invocationHandler) {
        return Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), invocationHandler);
    }
}
