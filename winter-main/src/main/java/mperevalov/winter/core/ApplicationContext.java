package mperevalov.winter.core;

import mperevalov.winter.extensions.BeanFactoryExtensionPointsCollector;

import java.util.Collection;
import java.util.List;

public class ApplicationContext implements BeanProvider {

    private final BeanRegistry registry;
    private final BeanFactory factory;
    private final ApplicationProperties properties;

    public ApplicationContext(BeanRegistry registry, ApplicationProperties properties) {
        this.properties = properties;
        this.registry = registry;
        factory = createBeanFactory();
        registerBean(this);
        registerBean(factory);
        registerBean(properties);
    }

    public ApplicationContext(ApplicationProperties properties) {
        this(new BeanRegistry(), properties);
    }

    private BeanFactory createBeanFactory() {
        var extensionPoints = new BeanFactoryExtensionPoints();
        var extensionPointsCollector = new BeanFactoryExtensionPointsCollector(extensionPoints);
        extensionPoints.addBeanPostConstructionEp(extensionPointsCollector);
        return new BeanFactory(this, extensionPoints);
    }

    private void registerBean(Object object) {
        var definition = new SingletonNoCreationBeanDefinition<>(object.getClass());
        registry.addSingletonBeanIfAbsent(definition, object);
    }

    public List<Object> addNewBeanDefinitions(Collection<BeanDefinition<?>> definitions) {
        var addedBeanDefinitions = registry.addNewBeanDefinitions(definitions);
        return addedBeanDefinitions.stream()
                .filter((definition) -> definition.isSingleton() && !definition.isLazy())
                .map((definition) -> (Object) createSingleton(definition))
                .toList();
    }

    @Override
    public <T> T getBean(BeanDefinition<T> beanDefinition) {
        if (beanDefinition.isSingleton()) {
            return getSingletonBean(beanDefinition);
        }
        return createBean(beanDefinition);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        var beanDefinition = registry.findBeanDefinitionByType(type);
        return beanDefinition
                .map(this::getBean)
                .orElse(null);
    }

    private <T> T getSingletonBean(BeanDefinition<T> beanDefinition) {
        return registry.getSingleton(beanDefinition)
                .orElseGet(() -> createSingleton(beanDefinition));
    }

    private <T> T createSingleton(BeanDefinition<T> beanDefinition) {
        var bean = createBean(beanDefinition);
        registry.addSingletonForBeanDefinition(beanDefinition, bean);
        return bean;
    }

    private <T> T createBean(BeanDefinition<T> beanDefinition) {
        if (beanDefinition.isLazy()) {
            return factory.createLazyBean(beanDefinition);
        }
        return (T) factory.createBean(beanDefinition);
    }
}
