package configuration.spring;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cm) {
        EhCacheCacheManager manager =  new EhCacheCacheManager(cm);
        return manager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheCacheManager = new EhCacheManagerFactoryBean();
        ehCacheCacheManager.setConfigLocation(new ClassPathResource("/ehcache.xml"));
        return ehCacheCacheManager;
    }
}
