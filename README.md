###springboot 与ehacche集成
    1.与redis集成相同的思路，先构建一个可以执行jpa的maven web项目，并测试通过
    2.添加ehcache依赖
        <!--ehcache-->
        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>
    3.编写EhcacheConfig类，注入ehcache到springboot中
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
        }