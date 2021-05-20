package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Lazy;
import mperevalov.winter.annotations.Primary;
import mperevalov.winter.exceptions.BeanDefinitionException;
import mperevalov.winter.exceptions.ManyConstructorAnnotationsBDE;
import mperevalov.winter.exceptions.NoBeanConstructorAnnotationBDE;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllSuperTypes;

public class AnnotationBeanDefinition<T> extends BeanDefinition<T> {

    private String name;
    private Class<T> type;
    private BeanScope scope;
    private BeanConstructor<T> constructor;
    private boolean isPrimary;
    private Set<Class<?>> superTypes;
    private boolean isLazy;

    public AnnotationBeanDefinition(Class<T> annotatedType) {
        if (isInterfaceOrAbstractClass(annotatedType)) {
            throw new BeanDefinitionException("Bean: %s can't be interface or abstract class".formatted(annotatedType.getSimpleName()));
        }
        if (!annotatedType.isAnnotationPresent(Bean.class)) {
            throw new BeanDefinitionException("Bean: %s has to have @Bean annotation".formatted(annotatedType.getSimpleName()));
        }
        this.name = getBeanName(annotatedType);
        this.type = annotatedType;
        this.scope = getBeanScope(annotatedType);
        this.constructor = new ClassConstructorBeanConstructor<>((Constructor<T>) getBeanConstructor(annotatedType));
        this.isPrimary = isPrimary(annotatedType);
        this.superTypes = getSuperTypes(annotatedType);
        this.isLazy = getIsLazy(annotatedType);
    }

    public static Set<Class<?>> getSuperTypes(Class<?> type) {
        var allSuperTypes = getAllSuperTypes(type);
        allSuperTypes.remove(type);
        return allSuperTypes;
    }

    public boolean isPrimary(Class<?> type) {
        return type.isAnnotationPresent(Primary.class);
    }

    private String getBeanName(Class<?> type) {
        var annotation = type.getAnnotation(Bean.class);
        var name = annotation.name();
        if (name.equals("")) {
            return type.getSimpleName();
        }
        return name;
    }

    public Constructor<?> getBeanConstructor(Class<?> type) {
        var constructors = type.getDeclaredConstructors();
        if (constructors.length == 1) {
            return constructors[0];
        } else {
            return findAnnotatedConstructor(constructors, type);
        }
    }

    public Constructor<?> findAnnotatedConstructor(Constructor<?>[] constructors, Class<?> type) {
        var annotatedConstructors = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(mperevalov.winter.annotations.BeanConstructor.class))
                .toArray(Constructor[]::new);
        if (annotatedConstructors.length == 1) {
            return annotatedConstructors[0];
        }
        if (annotatedConstructors.length == 0) {
            throw new NoBeanConstructorAnnotationBDE(constructors, type);
        }
        throw new ManyConstructorAnnotationsBDE(constructors, type);
    }

    public boolean getIsLazy(Class<?> type) {
        return type.isAnnotationPresent(Lazy.class);
    }

    public BeanScope getBeanScope(Class<?> type) {
        return type.getAnnotation(Bean.class).scope();
    }

    public boolean isInterfaceOrAbstractClass(Class<?> type) {
        return type.isInterface() || Modifier.isAbstract(type.getModifiers());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public BeanConstructor<T> constructor() {
        return constructor;
    }

    @Override
    public Set<Class<?>> superTypes() {
        return superTypes;
    }

    @Override
    public boolean isLazy() {
        return isLazy;
    }

    @Override
    public BeanScope scope() {
        return scope;
    }

    @Override
    public boolean isPrimary() {
        return isPrimary;
    }
}
