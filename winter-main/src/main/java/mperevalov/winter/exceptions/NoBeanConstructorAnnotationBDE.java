package mperevalov.winter.exceptions;

import java.lang.reflect.Constructor;

public class NoBeanConstructorAnnotationBDE extends BeanDefinitionException {
    public NoBeanConstructorAnnotationBDE(Constructor[] constructors, Class<?> type) {
        super("Bean: %s has %s constructor. Annotate one constructor with @BeanConstructor annotation"
                .formatted(type.getSimpleName(), constructors.length));
    }

    public NoBeanConstructorAnnotationBDE(Throwable throwable) {
        super(throwable);
    }
}
