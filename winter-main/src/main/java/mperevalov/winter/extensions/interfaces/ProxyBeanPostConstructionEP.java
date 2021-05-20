package mperevalov.winter.extensions.interfaces;

public interface ProxyBeanPostConstructionEP extends ExtensionPoint {
    Object proxifyIfNeeded(Object bean, Class<?> originalType);
}
