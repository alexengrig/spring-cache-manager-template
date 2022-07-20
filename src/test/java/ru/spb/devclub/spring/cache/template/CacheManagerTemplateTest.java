package ru.spb.devclub.spring.cache.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheManagerTemplateTest {

    @Test
    void should_throw_UOE_for_getCacheTemplate() {
        String name = "cacheName";
        CacheManagerTemplate<Object, Object> template = new CacheManagerTemplate<>() {
            @Override
            public Collection<String> getCacheNames() {
                return null;
            }

            @Override
            public Optional<Object> get(String name, Object key) {
                return Optional.empty();
            }

            @Override
            public Object get(String name, Object key, Callable<?> valueLoader) {
                return null;
            }

            @Override
            public void put(String name, Object key, Object value) {

            }

            @Override
            public void putIfAbsent(String name, Object key, Object value) {

            }

            @Override
            public void evict(String name, Object key) {

            }

            @Override
            public void clear(String name) {

            }
        };
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () ->
                template.getCacheTemplate(name));
        Assertions.assertEquals("Not supported named template for 'cacheName'", exception.getMessage(),
                "Exception message");
    }

}