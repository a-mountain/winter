package mperevalov.winter.core;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SingletonNoCreationBeanDefinition<T> extends BeanDefinition<T> {

    private final Class<T> type;

    public SingletonNoCreationBeanDefinition(Class<T> type) {
        this.type = type;
    }

    @Override
    public String name() {
        return type.getSimpleName();
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public BeanConstructor<T> constructor() {
        return new BeanConstructor<>() {
            @Override
            public List<Parameter> getParameters() {
                return Collections.emptyList();
            }

            @Override
            public T create(Object... params) {
                throw new UnsupportedOperationException("This bean can't be created: " + name());
            }
        };
    }

    @Override
    public BeanScope scope() {
        return BeanScope.Singleton;
    }

    @Override
    public boolean isPrimary() {
        return true;
    }

    @Override
    public Set<Class<?>> superTypes() {
        return AnnotationBeanDefinition.getSuperTypes(type);
    }

    @Override
    public boolean isLazy() {
        return false;
    }
}
