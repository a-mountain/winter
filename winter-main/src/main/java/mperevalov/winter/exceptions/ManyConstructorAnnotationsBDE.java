package mperevalov.winter.exceptions;

import java.lang.reflect.Constructor;

public class ManyConstructorAnnotationsBDE extends BeanDefinitionException {
    public ManyConstructorAnnotationsBDE(Constructor<?>[] constructors, Class<?> type) {
        super("Bean: %s has %s @BeanConstructor annotations. Annotate only one constructor with @BeanConstructor annotation"
                .formatted(type.getSimpleName(), constructors.length));
    }

    public ManyConstructorAnnotationsBDE(Throwable throwable) {
        super(throwable);
    }
}
