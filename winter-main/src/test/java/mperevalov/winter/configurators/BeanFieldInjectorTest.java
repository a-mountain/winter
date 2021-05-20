package mperevalov.winter.configurators;

import mperevalov.winter.core.BeanProvider;
import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.InjectByType;
import mperevalov.winter.extensions.BeanFieldInjector;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BeanFieldInjectorTest {

    @Test
    void shouldInjectByTypeInAnnotatedField() {
        BeanProvider provider = mock(BeanProvider.class);
        var b = new B();
        when(provider.getBean(B.class)).thenReturn(b);
        var injectByTypeBeanConfigurator = new BeanFieldInjector(provider);
        var a = new A();
        injectByTypeBeanConfigurator.configure(a);
        assertThat(a.b).isEqualTo(b);
    }

    @Bean
    private static class A {
        @InjectByType
        private B b;
    }

    @Bean
    private static class B {

    }

}