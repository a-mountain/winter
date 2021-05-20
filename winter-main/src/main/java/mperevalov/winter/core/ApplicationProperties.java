package mperevalov.winter.core;

import mperevalov.winter.annotations.Bean;
import mperevalov.winter.annotations.BeanConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

public class ApplicationProperties implements Map<String, String> {

    private final Map<String, String> properties;

    public ApplicationProperties(String propertiesFilename) {
        Map<String, String> propertiesMap;
        try {
            var resource = ClassLoader.getSystemClassLoader().getResource(propertiesFilename);
            String path = requireNonNull(resource).getPath();
            Stream<String> lines;
            lines = new BufferedReader(new FileReader(path)).lines();
            propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
        } catch (Exception e) {
            propertiesMap = Collections.emptyMap();
        }
        this.properties = new ConcurrentHashMap<>(propertiesMap);
    }

    public ApplicationProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public ApplicationProperties() {
        this("application.properties");
    }

    @Override
    public int size() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return properties.get(key);
    }

    @Override
    public String put(String key, String value) {
        return properties.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        properties.putAll(m);
    }

    @Override
    public void clear() {
        properties.clear();
    }

    @Override
    public Set<String> keySet() {
        return properties.keySet();
    }

    @Override
    public Collection<String> values() {
        return properties.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return properties.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return properties.equals(o);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }

    @Override
    public String getOrDefault(Object key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super String> action) {
        properties.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        properties.replaceAll(function);
    }

    @Override
    public String putIfAbsent(String key, String value) {
        return properties.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return properties.remove(key, value);
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        return properties.replace(key, oldValue, newValue);
    }

    @Override
    public String replace(String key, String value) {
        return properties.replace(key, value);
    }

    @Override
    public String computeIfAbsent(String key, Function<? super String, ? extends String> mappingFunction) {
        return properties.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public String computeIfPresent(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return properties.computeIfPresent(key, remappingFunction);
    }

    @Override
    public String compute(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return properties.compute(key, remappingFunction);
    }

    @Override
    public String merge(String key, String value, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return properties.merge(key, value, remappingFunction);
    }
}
