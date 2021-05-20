package mperevalov.winter.core;

import java.lang.reflect.Parameter;
import java.util.List;

public interface BeanConstructor<T> {
    List<Parameter> getParameters();

    T create(Object... params);
}
