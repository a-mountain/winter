package mperevalov.winter.core;

public interface BeanProvider {
    <T> T getBean(BeanDefinition<T> definition);

    <T> T getBean(Class<T> type);
}
