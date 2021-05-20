package mperevalov.winter.extensions;

import mperevalov.winter.ReflectionHelper;
import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Property;
import mperevalov.winter.core.ApplicationProperties;
import mperevalov.winter.extensions.interfaces.BeanPostConstructionEP;

import java.lang.reflect.Field;

import static org.reflections.ReflectionUtils.getAllFields;

@Bean
public class PropertyFieldInjector implements BeanPostConstructionEP {

    private final ApplicationProperties properties;

    public PropertyFieldInjector(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void configure(Object bean) {
        var fields = getAllFields(bean.getClass(), (f) -> f.isAnnotationPresent(Property.class));
        for (Field field : fields) {
            var annotation = field.getAnnotation(Property.class);
            String key;
            if (annotation.value().isEmpty()) {
                key = field.getName();
            } else {
                key = annotation.value();
            }
            ReflectionHelper.set(field, bean, properties.get(key));
        }
    }
}
