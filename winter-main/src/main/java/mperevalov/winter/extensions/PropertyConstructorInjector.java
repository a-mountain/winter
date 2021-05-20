package mperevalov.winter.extensions;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Property;
import mperevalov.winter.core.ApplicationProperties;
import mperevalov.winter.core.BeanParameterValue;
import mperevalov.winter.extensions.interfaces.BeanBeforeConstructionEP;

import java.util.List;

@Bean
public class PropertyConstructorInjector implements BeanBeforeConstructionEP {

    private final ApplicationProperties properties;

    public PropertyConstructorInjector(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void configure(Class<?> beanType, List<BeanParameterValue> parameters) {
        for (BeanParameterValue beanParameter : parameters) {
            var parameter = beanParameter.parameter();
            if (parameter.isAnnotationPresent(Property.class)) {
                var annotation = parameter.getAnnotation(Property.class);
                String key;
                if (annotation.value().isEmpty()) {
                    key = parameter.getName();
                } else {
                    key = annotation.value();
                }
                beanParameter.setParameterValue(properties.get(key));
            }
        }
    }
}
