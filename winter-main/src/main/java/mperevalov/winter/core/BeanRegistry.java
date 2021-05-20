package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.BeanConstructor;
import mperevalov.winter.exceptions.PrimaryBeanDublicationBRE;

import java.util.*;
import java.util.stream.Collectors;

@Bean
public class BeanRegistry {

    private final Map<BeanDefinition<?>, Object> registry;

    @BeanConstructor
    public BeanRegistry() {
        this.registry = new HashMap<>();
    }

    public BeanRegistry(Collection<BeanDefinition<?>> definitions) {
        this();
        addNewBeanDefinitions(definitions);
    }

    public void addSingletonForBeanDefinition(BeanDefinition<?> beanDefinition, Object bean) {
        Optional<?> singleton = getSingleton(beanDefinition);
        if (singleton.isPresent()) {
            return;
        }
        registry.put(beanDefinition, bean);
    }

    public void addNewSingletonWithBeanDefinition(BeanDefinition<?> beanDefinition, Object bean) {
        if (containsBeanDefinition(beanDefinition)) {
            return;
        }
        validatePrimaryBeanUniqueness(beanDefinition);
        registry.put(beanDefinition, bean);
    }

    public void addSingletonBeanIfAbsent(BeanDefinition<?> beanDefinition, Object bean) {
        Optional<?> singleton = getSingleton(beanDefinition);
        if (singleton.isPresent()) {
            return;
        }
        validatePrimaryBeanUniqueness(beanDefinition);
        registry.put(beanDefinition, bean);
    }

    public void addBeanDefinitionIfAbsent(BeanDefinition<?> beanDefinition) {
        addSingletonBeanIfAbsent(beanDefinition, null);
    }

    public List<BeanDefinition<?>> addNewBeanDefinitions(Collection<BeanDefinition<?>> beanDefinitions) {
        return beanDefinitions.stream()
                .filter((beanDefinition) -> !containsBeanDefinition(beanDefinition))
                .peek((beanDefinition) -> {
                    validatePrimaryBeanUniqueness(beanDefinition);
                    registry.put(beanDefinition, null);
                })
                .toList();
    }

    public Set<BeanDefinition<?>> getAllBeanDefinitions() {
        return registry.keySet();
    }

    private void validatePrimaryBeanUniqueness(BeanDefinition<?> definitionToValidate) {
        for (BeanDefinition<?> definition : registry.keySet()) {
            var equalSuperTypes = definition.hasEqualSuperTypes(definitionToValidate);
            var bothArePrimaryBeans = definition.isPrimary() && definitionToValidate.isPrimary();
            if (equalSuperTypes && bothArePrimaryBeans) {
                throw new PrimaryBeanDublicationBRE(definitionToValidate, definition);
            }
        }
    }

    public <T> Optional<BeanDefinition<T>> getBeanDefinition(String name) {
        for (BeanDefinition<?> beanDefinition : registry.keySet()) {
            if (beanDefinition.name().equals(name)) {
                return Optional.of((BeanDefinition<T>) beanDefinition);
            }
        }
        return Optional.empty();
    }

    public <T> Collection<BeanDefinition<T>> findAllBeanDefinitions(Class<T> type) {
        return registry.keySet().stream()
                .filter(beanDefinition -> beanDefinition.isSuperTypeOrSameType(type))
                .map(beanDefinition -> (BeanDefinition<T>) beanDefinition)
                .collect(Collectors.toSet());
    }

    public <T> Optional<BeanDefinition<T>> findBeanDefinitionByType(Class<T> type) {
        var beanDefinitions = findAllBeanDefinitions(type);
        if (beanDefinitions.size() == 1) {
            return Optional.of(beanDefinitions.iterator().next());
        }
        for (BeanDefinition<T> beanDefinition : beanDefinitions) {
            if (beanDefinition.isPrimary()) {
                return Optional.of(beanDefinition);
            }
        }
        return Optional.empty();
    }

    public boolean containsSingleton(BeanDefinition<?> beanDefinition) {
        return registry.get(beanDefinition) != null;
    }

    public boolean containsBeanDefinition(BeanDefinition<?> beanDefinition) {
        return registry.containsKey(beanDefinition);
    }

    public <T> Optional<T> getSingleton(BeanDefinition<T> beanDefinition) {
        return Optional.ofNullable((T) registry.get(beanDefinition));
    }
}
