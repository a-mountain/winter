package mperevalov.winter.extensions.interfaces;

public interface BeanPostConstructionEP extends ExtensionPoint {
    void configure(Object bean);
}
