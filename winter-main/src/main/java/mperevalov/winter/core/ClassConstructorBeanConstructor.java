package mperevalov.winter.core;

import mperevalov.winter.exceptions.BeanConstructionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;

import static java.util.Arrays.asList;

public class ClassConstructorBeanConstructor<T> implements BeanConstructor<T> {

    private final Constructor<T> constructor;

    public ClassConstructorBeanConstructor(Constructor<T> constructor) {
        constructor.setAccessible(true);
        this.constructor = constructor;
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public List<Parameter> getParameters() {
        var parameters = constructor.getParameters();
        return asList(parameters);
    }

    @Override
    public T create(Object... params) {
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new BeanConstructionException(e);
        }
    }
}
