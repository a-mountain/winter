package mperevalov.winter.configurators;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Property;
import mperevalov.winter.core.ApplicationProperties;
import mperevalov.winter.extensions.PropertyFieldInjector;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyFieldInjectorTest {

    @Test
    void shouldInjectPropertyByFieldName() {
        var applicationProperties = new ApplicationProperties();
        var configurator = new PropertyFieldInjector(applicationProperties);
        var a = new A();
        configurator.configure(a);
        assertThat(a.name).isEqualTo("maxim");
        assertThat(a.lastname).isEqualTo("perevalov");
    }

    @Test
    void shouldInjectPropertyByAnnotationValue() {
        var applicationProperties = new ApplicationProperties();
        var configurator = new PropertyFieldInjector(applicationProperties);
        var bean = new B();
        configurator.configure(bean);
        assertThat(bean.a).isEqualTo("maxim");
        assertThat(bean.b).isEqualTo("perevalov");
    }

    @Bean
    private static class A {
        @Property
        public String name;
        @Property
        public String lastname;
    }

    @Bean
    private static class B {
        @Property("name")
        public String a;
        @Property("lastname")
        public String b;
    }
}