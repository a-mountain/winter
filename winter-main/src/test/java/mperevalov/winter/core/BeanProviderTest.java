package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.exceptions.BeanConstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BeanProviderTest {

    private BeanRegistry registry;
    private BeanProvider factory;

    @BeforeEach
    void setUp() {
        registry = new BeanRegistry();
        factory = new ApplicationContext(registry, Mockito.mock(ApplicationProperties.class));
    }

    @Test
    void shouldCreateEmptyConstructorBean() {
        registerBeanDefinition(EmptyConstructor.class);

        var bean = factory.getBean(EmptyConstructor.class);

        assertThat(bean).isInstanceOf(EmptyConstructor.class);
    }

    @Test
    void shouldCreateParametersConstructorBean() {
        registerBeanDefinition(A.class);
        registerBeanDefinition(B.class);
        registerBeanDefinition(ParamsConstructor.class);

        var bean = factory.getBean(ParamsConstructor.class);

        assertThat(bean).isInstanceOf(ParamsConstructor.class);
        assertThat(bean.a).isInstanceOf(A.class);
        assertThat(bean.b).isInstanceOf(B.class);
    }

    @Test
    void shouldCreateSingletonOnce() {
        registerBeanDefinition(EmptyConstructor.class);

        var bean1 = factory.getBean(EmptyConstructor.class);
        var bean2 = factory.getBean(EmptyConstructor.class);

        assertThat(bean1 == bean2).isTrue();
    }

    @Test
    void shouldCreateNewPrototypeEverytime() {
        registerBeanDefinition(Prototype.class);

        var bean1 = factory.getBean(Prototype.class);
        var bean2 = factory.getBean(Prototype.class);

        assertThat(bean1 == bean2).isFalse();
    }

    @Test

    void shouldThrowExceptionIfNoBeanDefinition() {
//        var throwable = catchThrowable(() -> factory.getBean(EmptyConstructor.class));
//        assertThat(throwable)
//                .isInstanceOf(BeanConstructionException.class)
//                .hasMessage("There is no bean definition for type: EmptyConstructor");
    }

    private <T> void registerBeanDefinition(Class<T> type) {
        registry.addBeanDefinitionIfAbsent(new AnnotationBeanDefinition<>(type));
    }

    @Bean(scope = BeanScope.Prototype)
    private static class Prototype {

    }

    @Bean
    private static class EmptyConstructor {

    }

    @Bean
    private static class A {

    }

    @Bean
    private static class B {
        B(A a) {

        }
    }

    @Bean
    private static class ParamsConstructor {
        public A a;
        public B b;

        ParamsConstructor(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }
}