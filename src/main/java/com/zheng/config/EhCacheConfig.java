package com.zheng.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 注入ehcache
 * Created by zhenglian on 2016/9/25.
 */
@Configuration
@EnableCaching
public class EhCacheConfig {
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean factory) {
        return new EhCacheCacheManager(factory.getObject());
    }

    /**
     * 根据shared的设置，spring分别通过CacheManager.create()或new CacheManager()
     * 的方式来创建一个cacheManager
     * 主要是为了表示当前的cacheManager是spring自己独用还是和其他缓存对象共享，比如hibernate的Ehcache
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factory.setShared(true);
        return factory;
    }
}
