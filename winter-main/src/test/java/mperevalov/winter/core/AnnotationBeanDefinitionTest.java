package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.BeanConstructor;
import mperevalov.winter.annotations.Primary;
import mperevalov.winter.exceptions.BeanDefinitionException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AnnotationBeanDefinitionTest {

    @Test
    void shouldHaveCorrectName() {
        var definition = new AnnotationBeanDefinition<>(SingletonNotPrimary.class);
        assertThat(definition.name()).isEqualTo("SingletonNotPrimary");
    }

    @Test
    void shouldHaveCorrectType() {
        var definition = new AnnotationBeanDefinition<>(AnnotatedConstructor.class);
        assertThat(definition.type()).isEqualTo(AnnotatedConstructor.class);
    }

    @Test
    void shouldHaveSingletonScope() {
        var definition = new AnnotationBeanDefinition<>(AnnotatedConstructor.class);
        assertThat(definition.scope()).isEqualTo(BeanScope.Singleton);
    }

    @Test
    void shouldBeNotPrimary() {
        var definition = new AnnotationBeanDefinition<>(AnnotatedConstructor.class);
        assertThat(definition.isPrimary()).isFalse();
    }

    @Test
    void shouldHaveRightConstructorParameters() {
        var definition = new AnnotationBeanDefinition<>(SingletonNotPrimary.class);
        var expectedParams = List.of(int.class, int.class);
        assertThat(definition.constructor().getParameters().stream().map(Parameter::getType).toList())
                .isEqualTo(expectedParams);
    }

    @Test
    void shouldBePrimary() {
        var definition = new AnnotationBeanDefinition<>(PrototypePrimary.class);
        assertThat(definition.isPrimary()).isTrue();
    }

    @Test
    void shouldBePrototype() {
        var definition = new AnnotationBeanDefinition<>(PrototypePrimary.class);
        assertThat(definition.scope()).isEqualTo(BeanScope.Prototype);
    }

    @Test
    void shouldHaveCustomName() {
        var definition = new AnnotationBeanDefinition<>(PrototypePrimary.class);
        assertThat(definition.name()).isEqualTo("prototype_primary");
    }

    @Test
    void shouldUseBeanConstructor() {
        var definition = new AnnotationBeanDefinition<>(AnnotatedConstructor.class);
        var expectedParams = List.of(String.class, String.class);
        assertThat(definition.constructor().getParameters().stream().map(Parameter::getType).toList()).isEqualTo(expectedParams);
    }

    @Test
    void shouldThrowExceptionForInterface() {
        Throwable throwable = catchThrowable(() -> new AnnotationBeanDefinition<>(InterfaceBean.class));
        assertThat(throwable)
                .isInstanceOf(BeanDefinitionException.class)
                .hasMessage("Bean: InterfaceBean can't be interface or abstract class");
    }

    @Test
    void shouldThrowExceptionForAbstractClass() {
        Throwable throwable = catchThrowable(() -> new AnnotationBeanDefinition<>(AbstractBean.class));
        assertThat(throwable)
                .isInstanceOf(BeanDefinitionException.class)
                .hasMessage("Bean: AbstractBean can't be interface or abstract class");
    }

    @Test
    void shouldHaveBeanAnnotation() {
        Throwable throwable = catchThrowable(() -> new AnnotationBeanDefinition<>(NoBeanAnnotation.class));
        assertThat(throwable)
                .isInstanceOf(BeanDefinitionException.class)
                .hasMessage("Bean: NoBeanAnnotation has to have @Bean annotation");
    }

    @Test
    void shouldThrowExceptionIfFewNoAnnotatedConstructors() {
        Throwable throwable = catchThrowable(() -> new AnnotationBeanDefinition<>(ManyConstructors.class));
        assertThat(throwable)
                .isInstanceOf(BeanDefinitionException.class)
                .hasMessage("Bean: ManyConstructors has 2 constructor. Annotate one constructor with @BeanConstructor annotation");
    }

    @Test
    void shouldHaveOnlyOneBeanConstructorAnnotation() {
        Throwable throwable = catchThrowable(() -> new AnnotationBeanDefinition<>(ManyBeanConstructorAnnotations.class));
        assertThat(throwable)
                .isInstanceOf(BeanDefinitionException.class)
                .hasMessage("Bean: ManyBeanConstructorAnnotations has 2 @BeanConstructor annotations. Annotate only one constructor with @BeanConstructor annotation");
    }

    @Test
    void shouldHaveCorrectSuperTypes() {
        var definition = new AnnotationBeanDefinition<>(ImplBean.class);
        var expected = Set.of(InterfaceBean.class);
        assertThat(definition.superTypes()).isEqualTo(expected);
    }

    @Bean
    private interface InterfaceBean {
    }

    @Bean
    private static class ManyBeanConstructorAnnotations {
        @BeanConstructor
        ManyBeanConstructorAnnotations(int a, int b) {
        }

        @BeanConstructor
        ManyBeanConstructorAnnotations(String a, String b) {

        }
    }

    @Bean
    private static class ManyConstructors {
        ManyConstructors(int a, int b) {
        }

        ManyConstructors(String a, String b) {

        }
    }

    private static class NoBeanAnnotation {

    }

    @Bean
    private static abstract class AbstractBean {

    }

    @Bean
    private static class SingletonNotPrimary {
        SingletonNotPrimary(int a, int b) {
        }
    }

    @Bean
    private static class AnnotatedConstructor {
        AnnotatedConstructor(int a, int b) {
        }

        @BeanConstructor
        AnnotatedConstructor(String a, String b) {

        }
    }

    @Bean(scope = BeanScope.Prototype, name = "prototype_primary")
    @Primary
    private static class PrototypePrimary {

    }

    @Bean
    private class ImplBean implements InterfaceBean {

    }
}