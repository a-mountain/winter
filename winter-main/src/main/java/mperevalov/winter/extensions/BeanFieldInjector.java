package mperevalov.winter.extensions;

import mperevalov.winter.ReflectionHelper;
import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.InjectByType;
import mperevalov.winter.core.BeanProvider;
import mperevalov.winter.extensions.interfaces.BeanPostConstructionEP;

import java.lang.reflect.Field;

import static org.reflections.ReflectionUtils.getAllFields;

@Bean
public class BeanFieldInjector implements BeanPostConstructionEP {

    private final BeanProvider provider;

    public BeanFieldInjector(BeanProvider provider) {
        this.provider = provider;
    }

    @Override
    public void configure(Object bean) {
        var fields = getAllFields(bean.getClass(), (field -> field.isAnnotationPresent(InjectByType.class)));
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            Object fieldValue = provider.getBean(fieldType);
            ReflectionHelper.set(field, bean, fieldValue);
        }
    }
}
