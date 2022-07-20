package ru.spb.devclub.spring.cache.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import ru.spb.devclub.spring.cache.template.CacheManagerTemplate;
import ru.spb.devclub.spring.cache.template.support.ParentCacheManagerTemplate;

/**
 * Configuration of {@link CacheManagerTemplate}.
 *
 * @author Grig Alex
 * @version 1.0
 * @since 1.0
 */
public class CacheManagerTemplateConfiguration {

    @Bean
    public <K, V, T extends CacheManager> CacheManagerTemplate<K, V>
    cacheManagerTemplate(T cacheManager) {
        return new ParentCacheManagerTemplate<>(cacheManager);
    }

}
