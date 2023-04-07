package com.beite.log.search.logfilesearchdome.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 1:11
 * @since 1.0
 */
@Configuration
public class EhcacheConfig {

    @Bean
    public Cache fileDateCache(@Qualifier("ehCacheCacheManager") CacheManager ehCacheCacheManager) {
        return ehCacheCacheManager.getCache("fileDateCache");
    }

    @Bean
    public Cache fileDateTimeCache(@Qualifier("ehCacheCacheManager") CacheManager ehCacheCacheManager) {
        return ehCacheCacheManager.getCache("fileDateTimeCache");
    }

    @Bean
    public Cache fileLevelCache(@Qualifier("ehCacheCacheManager") CacheManager ehCacheCacheManager) {
        return ehCacheCacheManager.getCache("fileLevelCache");
    }
}
