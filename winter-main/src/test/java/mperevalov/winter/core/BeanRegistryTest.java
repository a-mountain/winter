package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Primary;
import mperevalov.winter.exceptions.BeanRegistryException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BeanRegistryTest {

    @Test
    void shouldRegisterBeanDefinition() {
        var beanDefinition = new AnnotationBeanDefinition<>(Bean1.class);
        var registry = new BeanRegistry();
        registry.addBeanDefinitionIfAbsent(beanDefinition);
        assertThat(registry.getBeanDefinition("Bean1").get()).isEqualTo(beanDefinition);
    }

    @Test
    void shouldThrowExceptionIfSeveralPrimaryDefinitions() {
        var registry = new BeanRegistry();
        var beanDefinition3 = new AnnotationBeanDefinition<>(Bean3.class);
        var beanDefinition4 = new AnnotationBeanDefinition<>(Bean4.class);
        boolean b = beanDefinition3.hasEqualSuperTypes(beanDefinition4);
        var throwable = catchThrowable(() -> registry.addNewBeanDefinitions(List.of(beanDefinition3, beanDefinition4)));
        assertThat(throwable)
                .isInstanceOf(BeanRegistryException.class)
                .hasMessageContaining("Bean3")
                .hasMessageContaining("Bean4")
                .hasMessageMatching("There is more than one primary annotated beans: (Bean3|Bean4), (Bean4|Bean3)");

    }

    @Test
    void shouldRegisterAllBeanDefinitions() {
        var registry = new BeanRegistry();
        var beanDefinition1 = new AnnotationBeanDefinition<>(Bean1.class);
        var beanDefinition2 = new AnnotationBeanDefinition<>(Bean2.class);
        registry.addNewBeanDefinitions(List.of(beanDefinition1, beanDefinition2));
        assertThat(registry.containsBeanDefinition(beanDefinition1)).isTrue();
        assertThat(registry.containsBeanDefinition(beanDefinition2)).isTrue();
    }

    @Test
    void shouldFindAllBeanDefinitionsWhereTypeIsAssignableFormOfBeanInterface() {
        var registry = new BeanRegistry();
        var beanDefinition1 = new AnnotationBeanDefinition<>(Bean1.class);
        var beanDefinition2 = new AnnotationBeanDefinition<>(Bean2.class);
        registry.addNewBeanDefinitions(List.of(beanDefinition1, beanDefinition2));
        var actual = registry.findAllBeanDefinitions(BeanInterface.class);
        var expected = Set.of(beanDefinition1, beanDefinition2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetPrimaryBeanDefinition() {
        var registry = new BeanRegistry();
        var beanDefinition1 = new AnnotationBeanDefinition<>(Bean1.class);
        var beanDefinition2 = new AnnotationBeanDefinition<>(Bean2.class);
        registry.addNewBeanDefinitions(Set.of(beanDefinition1, beanDefinition2));
        var actual = registry.findBeanDefinitionByType(BeanInterface.class);
        assertThat(actual.get()).isEqualTo(beanDefinition1);
    }

    @Test
    void shouldPutSingleton() {
        var registry = new BeanRegistry();
        var beanDefinition1 = new AnnotationBeanDefinition<>(Bean1.class);
        var beanDefinition2 = new AnnotationBeanDefinition<>(Bean2.class);
        var bean2 = new Bean2();
        registry.addNewBeanDefinitions(Set.of(beanDefinition1, beanDefinition2));
        registry.addSingletonForBeanDefinition(beanDefinition1, new Bean1());
        registry.addSingletonForBeanDefinition(beanDefinition2, bean2);
        assertThat(registry.containsSingleton(beanDefinition1)).isTrue();
        assertThat(registry.getSingleton(beanDefinition2).get()).isEqualTo(bean2);
    }

    private interface BeanInterface {
    }

    private interface PrimaryInterface {

    }

    @Bean
    @Primary
    private static class Bean1 implements BeanInterface {

    }

    @Bean
    private static class Bean2 implements BeanInterface {

    }

    @Bean
    @Primary
    private static class Bean3 implements PrimaryInterface {

    }

    @Bean
    @Primary
    private static class Bean4 implements PrimaryInterface {

    }
}