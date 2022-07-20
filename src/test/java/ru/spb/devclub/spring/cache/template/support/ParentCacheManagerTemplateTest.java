package ru.spb.devclub.spring.cache.template.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import ru.spb.devclub.spring.cache.template.CacheTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@ExtendWith(MockitoExtension.class)
class ParentCacheManagerTemplateTest {

    @Mock
    CacheManager manager;
    @Mock
    Cache cache;
    @Mock
    Cache.ValueWrapper wrapper;

    ParentCacheManagerTemplate<String, String, CacheManager> template;

    @BeforeEach
    void setUp() {
        template = new ParentCacheManagerTemplate<>(manager);
    }

    @Test
    void shouldNot_create_with_NPE() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () ->
                new ParentCacheManagerTemplate<>(null));
        Assertions.assertEquals("The manager must not be null", exception.getMessage(),
                "Exception message");
    }

    @Test
    void should_create_template() {
        String name = "cacheName";
        CacheTemplate<String, String> cacheTemplate = template.getCacheTemplate(name);
        Assertions.assertInstanceOf(ChildCacheTemplate.class, cacheTemplate, "CacheManager");
    }

    @Test
    void should_return_cacheNames() {
        List<String> expected = List.of("cacheName");
        Mockito.when(manager.getCacheNames()).thenReturn(expected);
        Collection<String> names = template.getCacheNames();
        Assertions.assertEquals(expected, names, "Cache names");
        Mockito.verify(manager).getCacheNames();
    }

    @Test
    void should_return_emptyOptional() {
        String name = "cacheName";
        String key = "key";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        Mockito.when(cache.get(key)).thenReturn(wrapper);
        Mockito.when(wrapper.get()).thenReturn(null);
        Optional<String> optional = template.get(name, key);
        Assertions.assertTrue(optional.isEmpty(), "Optional has value");
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).get(key);
        Mockito.verify(wrapper).get();
    }

    @Test
    void should_return_optional() {
        String name = "cacheName";
        String key = "key";
        String expected = "value";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        Mockito.when(cache.get(key)).thenReturn(wrapper);
        Mockito.when(wrapper.get()).thenReturn(expected);
        Optional<String> optional = template.get(name, key);
        Assertions.assertTrue(optional.isPresent(), "Optional is empty");
        Assertions.assertEquals(expected, optional.get(), "Value");
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).get(key);
        Mockito.verify(wrapper).get();

    }

    @Test
    void should_return_value() {
        String name = "cacheName";
        String key = "key";
        Callable<String> valueLoader = () -> "test";
        String expected = "value";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        Mockito.when(cache.get(key, valueLoader)).thenReturn(expected);
        String value = template.get(name, key, valueLoader);
        Assertions.assertEquals(expected, value, "Value");
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).get(key, valueLoader);
    }

    @Test
    void should_put_value() {
        String name = "cacheName";
        String key = "key";
        String value = "value";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        template.put(name, key, value);
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).put(key, value);
    }

    @Test
    void should_put_value_ifAbsent() {
        String name = "cacheName";
        String key = "key";
        String value = "value";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        template.putIfAbsent(name, key, value);
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).putIfAbsent(key, value);
    }

    @Test
    void should_evict() {
        String name = "cacheName";
        String key = "key";
        String value = "value";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        template.evict(name, key);
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).evict(key);
    }

    @Test
    void should_clear() {
        String name = "cacheName";
        Mockito.when(manager.getCache(name)).thenReturn(cache);
        template.clear(name);
        Mockito.verify(manager).getCache(name);
        Mockito.verify(cache).clear();
    }

}