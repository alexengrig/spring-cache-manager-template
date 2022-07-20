package ru.spb.devclub.spring.cache.template.support;

import ru.spb.devclub.spring.cache.template.CacheManagerTemplate;
import ru.spb.devclub.spring.cache.template.CacheTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * {@link CacheManagerTemplate} based {@link CacheTemplate}.
 *
 * @param <T> the type of {@link CacheManagerTemplate}
 * @author Grig Alex
 * @version 1.0
 * @since 1.0
 */
public class ChildCacheTemplate<K, V, T extends CacheManagerTemplate<K, V>> implements CacheTemplate<K, V> {

    private final T template;
    private final String cacheName;

    /**
     * @param template  parent template
     * @param cacheName cache name
     * @since 1.0
     */
    public ChildCacheTemplate(T template, String cacheName) {
        this.template = Objects.requireNonNull(template, "The template must not be null");
        this.cacheName = Objects.requireNonNull(cacheName, "The cacheName must not be null");
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Optional<V> get(K key) {
        return template.get(cacheName, key);
    }

    @Override
    public V get(K key, Callable<? extends V> valueLoader) {
        return template.get(cacheName, key, valueLoader);
    }

    @Override
    public void put(K key, V value) {
        template.put(cacheName, key, value);
    }

    @Override
    public void putIfAbsent(K key, V value) {
        template.putIfAbsent(cacheName, key, value);
    }

    @Override
    public void evict(K key) {
        template.evict(cacheName, key);
    }

    @Override
    public void clear() {
        template.clear(cacheName);
    }

}
