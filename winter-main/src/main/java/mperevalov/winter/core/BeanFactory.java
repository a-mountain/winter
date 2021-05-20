package mperevalov.winter.core;

import mperevalov.winter.ReflectionHelper;

import java.lang.reflect.Parameter;
import java.util.List;

public class BeanFactory {

    private final BeanProvider beanProvider;
    private final BeanFactoryExtensionPoints extensionPoints;

    public BeanFactory(BeanProvider beanProvider, BeanFactoryExtensionPoints extensionPoints) {
        this.extensionPoints = extensionPoints;
        this.beanProvider = beanProvider;
    }

    public <T> T createLazyBean(BeanDefinition<T> beanDefinition) {
        var type = beanDefinition.type();
        return (T) ReflectionHelper.createProxy(type, new LazyBeanInvocationHandler(beanDefinition, this));
    }

    public Object createBean(BeanDefinition<?> beanDefinition) {
        var constructor = beanDefinition.constructor();
        var parameters = constructor.getParameters();
        var parameterValues = getParameterValues(parameters, beanDefinition.type());
        var bean = constructor.create(parameterValues);
        extensionPoints.postConstruction(bean);
        bean = extensionPoints.proxyPostConstruction(bean, beanDefinition.type());
        return bean;
    }

    private Object[] getParameterValues(List<Parameter> parameterTypes, Class<?> type) {
        List<BeanParameterValue> beanParameters = parameterTypes.stream()
                .map(BeanParameterValue::new)
                .toList();
        extensionPoints.beforeConstruction(type, beanParameters);
        return beanParameters.stream()
                .peek(this::setBeanIfNeeded)
                .map(BeanParameterValue::parameterValue)
                .toArray();
    }

    private void setBeanIfNeeded(BeanParameterValue parameterValue) {
        if (parameterValue.isEmpty()) {
            Class<?> type = parameterValue.parameter().getType();
            Object bean = beanProvider.getBean(type);
            parameterValue.setParameterValue(bean);
        }
    }
}
