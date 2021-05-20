package mperevalov.winter.core;

import java.util.Objects;
import java.util.Set;

public abstract class BeanDefinition<T> {
    public abstract String name();

    public abstract Class<T> type();

    public abstract BeanConstructor<T> constructor();

    public abstract BeanScope scope();

    public abstract boolean isPrimary();

    public abstract Set<Class<?>> superTypes();

    public abstract boolean isLazy();

    public boolean isSingleton() {
        return scope() == BeanScope.Singleton;
    }

    public boolean hasEqualSuperTypes(BeanDefinition<?> definition) {
        return superTypes().equals(definition.superTypes());
    }

    public boolean isSuperTypeOrSameType(Class<?> type) {
        return isSameType(type) || isSuperType(type);
    }

    public boolean isSuperType(Class<?> type) {
        return superTypes().contains(type);
    }

    public boolean isSameType(Class<?> type) {
        return this.type().equals(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotationBeanDefinition<?> that = (AnnotationBeanDefinition<?>) o;
        return name().equals(that.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }
}
