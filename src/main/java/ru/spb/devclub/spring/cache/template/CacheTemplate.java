package ru.spb.devclub.spring.cache.template;

import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Working with the cache programmatically -
 * {@link org.springframework.cache.Cache}.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Grig Alex
 * @version 1.0
 */
public interface CacheTemplate<K, V> {

    /**
     * @return cache name
     * @see org.springframework.cache.Cache#getName()
     * @since 1.0
     */
    String getName();

    /**
     * @param key cache key to {@link org.springframework.cache.Cache#get(Object)}
     * @return {@code Optional} with cache value
     * @see org.springframework.cache.Cache#get(Object)
     * @since 1.0
     */
    Optional<V> get(K key);

    /**
     * @param key         cache key to {@link org.springframework.cache.Cache#get(Object, Callable)}
     * @param valueLoader cache value loader to {@link org.springframework.cache.Cache#get(Object, Callable)}
     * @return value of cache or {@code valueLoader}
     * @see org.springframework.cache.Cache#get(Object, Callable)
     * @since 1.0
     */
    V get(K key, Callable<? extends V> valueLoader);

    /**
     * @param key   cache key to {@link org.springframework.cache.Cache#put(Object, Object)}
     * @param value cache value to {@link org.springframework.cache.Cache#put(Object, Object)}
     * @see org.springframework.cache.Cache#put(Object, Object)
     * @since 1.0
     */
    void put(K key, V value);

    /**
     * @param key   cache key to {@link org.springframework.cache.Cache#putIfAbsent(Object, Object)}
     * @param value cache value to {@link org.springframework.cache.Cache#putIfAbsent(Object, Object)}
     * @see org.springframework.cache.Cache#putIfAbsent(Object, Object)
     * @since 1.0
     */
    void putIfAbsent(K key, V value);

    /**
     * @param key cache key to {@link org.springframework.cache.Cache#evict(Object)}
     * @see org.springframework.cache.Cache#evict(Object)
     * @since 1.0
     */
    void evict(K key);

    /**
     * @see org.springframework.cache.Cache#clear()
     * @since 1.0
     */
    void clear();

}
