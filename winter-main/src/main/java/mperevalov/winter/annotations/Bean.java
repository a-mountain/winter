package mperevalov.winter.annotations;

import mperevalov.winter.core.BeanScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    BeanScope scope() default BeanScope.Singleton;

    String name() default "";
}
