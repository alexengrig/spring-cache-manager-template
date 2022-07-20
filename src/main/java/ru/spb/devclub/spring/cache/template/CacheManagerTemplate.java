package ru.spb.devclub.spring.cache.template;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Working with the caches programmatically -
 * {@link org.springframework.cache.CacheManager}.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Grig Alex
 * @version 1.0
 */
public interface CacheManagerTemplate<K, V> {

    /**
     * Create named {@link CacheTemplate}.
     *
     * @param name cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @return {@link CacheTemplate} with {@code name}
     * @throws UnsupportedOperationException not supported by default
     * @since 1.0
     */
    default CacheTemplate<K, V> getCacheTemplate(String name) {
        throw new UnsupportedOperationException("Not supported named template for '" + name + "'");
    }

    /**
     * @return cache names
     * @see org.springframework.cache.CacheManager#getCacheNames()
     * @since 1.0
     */
    Collection<String> getCacheNames();

    /**
     * @param name cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @param key  cache key to {@link org.springframework.cache.Cache#get(Object)}
     * @return {@code Optional} with cache value
     * @see org.springframework.cache.Cache#get(Object)
     * @since 1.0
     */
    Optional<V> get(String name, K key);

    /**
     * @param name        cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @param key         cache key to {@link org.springframework.cache.Cache#get(Object, Callable)}
     * @param valueLoader cache value loader to {@link org.springframework.cache.Cache#get(Object, Callable)}
     * @return value of cache or {@code valueLoader}
     * @see org.springframework.cache.Cache#get(Object, Callable)
     * @since 1.0
     */
    V get(String name, K key, Callable<? extends V> valueLoader);

    /**
     * @param name  cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @param key   cache key to {@link org.springframework.cache.Cache#put(Object, Object)}
     * @param value cache value to {@link org.springframework.cache.Cache#put(Object, Object)}
     * @see org.springframework.cache.Cache#put(Object, Object)
     * @since 1.0
     */
    void put(String name, K key, V value);

    /**
     * @param name  cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @param key   cache key to {@link org.springframework.cache.Cache#putIfAbsent(Object, Object)}
     * @param value cache value to {@link org.springframework.cache.Cache#putIfAbsent(Object, Object)}
     * @see org.springframework.cache.Cache#putIfAbsent(Object, Object)
     * @since 1.0
     */
    void putIfAbsent(String name, K key, V value);

    /**
     * @param name cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @param key  cache key to {@link org.springframework.cache.Cache#evict(Object)}
     * @see org.springframework.cache.Cache#evict(Object)
     * @since 1.0
     */
    void evict(String name, K key);

    /**
     * @param name cache name to {@link org.springframework.cache.CacheManager#getCache(String)}
     * @see org.springframework.cache.Cache#clear()
     * @since 1.0
     */
    void clear(String name);

}
