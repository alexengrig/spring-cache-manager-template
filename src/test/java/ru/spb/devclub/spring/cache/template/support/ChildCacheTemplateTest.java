package ru.spb.devclub.spring.cache.template.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spb.devclub.spring.cache.template.CacheManagerTemplate;

import java.util.Optional;
import java.util.concurrent.Callable;

@ExtendWith(MockitoExtension.class)
class ChildCacheTemplateTest {

    @Mock
    CacheManagerTemplate<String, String> managerTemplate;
    String cacheName = "testCache";

    ChildCacheTemplate<String, String, CacheManagerTemplate<String, String>> template;

    @BeforeEach
    void setUp() {
        template = new ChildCacheTemplate<>(managerTemplate, cacheName);
    }

    @Test
    void shouldNot_create_with_NPE() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () ->
                new ChildCacheTemplate<>(null, cacheName));
        Assertions.assertEquals("The template must not be null", exception.getMessage(),
                "Exception message");
        exception = Assertions.assertThrows(NullPointerException.class, () ->
                new ChildCacheTemplate<>(managerTemplate, null));
        Assertions.assertEquals("The cacheName must not be null", exception.getMessage(),
                "Exception message");
    }

    @Test
    void should_return_name() {
        String name = template.getName();
        Assertions.assertEquals(cacheName, name, "Cache name");
    }

    @Test
    void should_return_optional() {
        String key = "key";
        Optional<String> expected = Optional.empty();
        Mockito.when(managerTemplate.get(cacheName, key)).thenReturn(expected);
        Optional<String> optional = template.get(key);
        Assertions.assertSame(expected, optional, "Optional");
        Mockito.verify(managerTemplate).get(cacheName, key);
    }

    @Test
    void should_return_value() {
        String key = "key";
        Callable<String> valueLoader = () -> "test";
        String expected = "value";
        Mockito.when(managerTemplate.get(cacheName, key, valueLoader)).thenReturn(expected);
        String value = template.get(key, valueLoader);
        Assertions.assertSame(expected, value, "Value");
        Mockito.verify(managerTemplate).get(cacheName, key, valueLoader);
    }

    @Test
    void should_put_value() {
        String key = "key";
        String value = "value";
        template.put(key, value);
        Mockito.verify(managerTemplate).put(cacheName, key, value);
    }

    @Test
    void should_put_value_ifAbsent() {
        String key = "key";
        String value = "value";
        template.putIfAbsent(key, value);
        Mockito.verify(managerTemplate).putIfAbsent(cacheName, key, value);
    }

    @Test
    void should_evict() {
        String key = "key";
        template.evict(key);
        Mockito.verify(managerTemplate).evict(cacheName, key);
    }

    @Test
    void should_clear() {
        template.clear();
        Mockito.verify(managerTemplate).clear(cacheName);
    }

}