package mperevalov.winter.extensions.interfaces;

import mperevalov.winter.core.BeanParameterValue;

import java.util.List;

public interface BeanBeforeConstructionEP extends ExtensionPoint {
    void configure(Class<?> beanType, List<BeanParameterValue> parameters);
}
