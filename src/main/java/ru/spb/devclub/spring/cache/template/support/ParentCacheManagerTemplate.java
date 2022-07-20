package ru.spb.devclub.spring.cache.template.support;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import ru.spb.devclub.spring.cache.template.CacheManagerTemplate;
import ru.spb.devclub.spring.cache.template.CacheTemplate;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * {@link CacheManager} based {@link CacheManagerTemplate}.
 *
 * @param <T> the type of {@link CacheManager}
 * @author Grig Alex
 * @version 1.0
 * @since 1.0
 */
public class ParentCacheManagerTemplate<K, V, T extends CacheManager> implements CacheManagerTemplate<K, V> {

    private final T manager;

    /**
     * @param manager cache manager
     * @since 1.0
     */
    public ParentCacheManagerTemplate(T manager) {
        this.manager = Objects.requireNonNull(manager, "The manager must not be null");
    }

    /**
     * @return {@link ChildCacheTemplate}
     */
    @Override
    public CacheTemplate<K, V> getCacheTemplate(String name) {
        return new ChildCacheTemplate<>(this, name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return manager.getCacheNames();
    }

    /**
     * @throws IllegalArgumentException if a cache by {@code name} doesn't exist
     */
    @Override
    public Optional<V> get(String name, K key) {
        Cache cache = obtainCache(name);
        Cache.ValueWrapper wrapper = cache.get(key);
        return unwrap(wrapper);
    }

    /**
     * @throws IllegalArgumentException if a cache by {@code name} doesn't exist
     */
    @Override
    public V get(String name, K key, Callable<? extends V> valueLoader) {
        Cache cache = obtainCache(name);
        return cache.get(key, valueLoader);
    }

    /**
     * @throws IllegalArgumentException if a cache by {@code name} doesn't exist
     */
    @Override
    public void put(String name, K key, V value) {
        Cache cache = obtainCache(name);
        cache.put(key, value);
    }

    /**
     * @throws IllegalArgumentException if a cache by {@code name} doesn't exist
     */
    @Override
    public void putIfAbsent(String name, K key, V value) {
        Cache cache = obtainCache(name);
        cache.putIfAbsent(key, value);
    }

    /**
     * @throws IllegalArgumentException if a cache by {@code name} doesn't exist
     */
    @Override
    public void evict(String name, K key) {
        Cache cache = obtainCache(name);
        cache.evict(key);
    }

    /**
     * @throws IllegalArgumentException if a cache by {@code name} doesn't exist
     */
    @Override
    public void clear(String name) {
        Cache cache = obtainCache(name);
        cache.clear();
    }

    protected Cache obtainCache(String name) {
        Objects.requireNonNull(name, "The name must not be null");
        Cache cache = manager.getCache(name);
        if (cache == null) {
            throw new IllegalArgumentException("No cache named '" + name + "'");
        }
        return cache;
    }

    protected Optional<V> unwrap(Cache.ValueWrapper wrapper) {
        if (wrapper == null) {
            return Optional.empty();
        }
        @SuppressWarnings("unchecked")
        V value = (V) wrapper.get();
        return Optional.ofNullable(value);
    }

}
