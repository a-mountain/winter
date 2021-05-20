package mperevalov.winter.extensions;

import mperevalov.winter.ReflectionHelper;
import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.Log;
import mperevalov.winter.extensions.interfaces.ProxyBeanPostConstructionEP;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.reflections.ReflectionUtils.getAllMethods;

@Bean
public class MethodLogger implements ProxyBeanPostConstructionEP {
    @Override
    public Object proxifyIfNeeded(Object bean, Class<?> originalType) {
        var methodsToLog = getMethodsToLog(originalType);
        if (methodsToLog.isEmpty()) {
            return bean;
        }
        var map = createMapNameToMethod(methodsToLog);
        return proxify(bean, originalType, map);
    }

    private Map<String, Method> createMapNameToMethod(Set<Method> methods) {
        return methods.stream()
                .collect(toMap(Method::getName, Function.identity()));
    }

    private Set<Method> getMethodsToLog(Class<?> originalType) {
        return getAllMethods(originalType, (method) -> method.isAnnotationPresent(Log.class));
    }

    private Object proxify(Object bean, Class<?> originalType, Map<String, Method> loggedMethods) {
        return ReflectionHelper.createProxy(originalType, (proxy, method, args) -> {
            var result = method.invoke(bean, args);
            if (loggedMethods.containsKey(method.getName())) {
                System.out.printf(
                        "Method: %s.%s is invoked with args: %s and returned %s%n",
                        originalType.getSimpleName(), method.getName(), Arrays.toString(args), result.toString()
                );
            }
            return result;
        });
    }
}
