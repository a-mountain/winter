package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WinterApplication {

    private static final String CORE_PACKAGE = "mperevalov.winter";

    private WinterApplication() {
    }

    public static ApplicationContext run(String packageToScan) {
        var applicationProperties = new ApplicationProperties();
        var beanDefinitions = collectBeanDefinitions(List.of(CORE_PACKAGE, packageToScan));
        var appContext = new ApplicationContext(applicationProperties);
        appContext.addNewBeanDefinitions((Collection<BeanDefinition<?>>) beanDefinitions);
        return appContext;
    }

    public static ApplicationContext run(Class<?> type) {
        return run(type.getPackageName());
    }

    private static List<? extends BeanDefinition<?>> collectBeanDefinitions(List<String> packagesToScan) {
        var beanDefinitions = new ArrayList<BeanDefinition<?>>();
        for (String packageToScan : packagesToScan) {
            var reflections = new Reflections(packageToScan);
            var beans = reflections.getTypesAnnotatedWith(Bean.class);
            var list = beans.stream()
                    .map(AnnotationBeanDefinition::new)
                    .toList();
            beanDefinitions.addAll(list);
        }
        return beanDefinitions;
    }
}
