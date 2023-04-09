package com.beite.log.search.logfilesearchdome.config;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.impl.serialization.StringSerializer;
import org.ehcache.shadow.org.terracotta.context.query.QueryBuilder;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 1:11
 * @since 1.0
 */
@Configuration
public class EhcacheConfig {

    @Bean
    public CacheManager cacheManager() {
        URL myUrl = getClass().getResource("/ehcache.xml");
        org.ehcache.config.Configuration xmlConfig = new XmlConfiguration(myUrl);
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        cacheManager.init();
        return cacheManager;
    }

    @Bean
    public Cache<String, LogEntry> fileDateCache(CacheManager cacheManager) {
        return cacheManager.getCache("fileDateCache", String.class, LogEntry.class);
    }

    @Bean
    public Cache<String, LogEntry> fileDateTimeCache(CacheManager cacheManager) {
        return cacheManager.getCache("fileDateTimeCache", String.class, LogEntry.class);
    }

    @Bean
    public Cache<String, LogEntry> fileLevelCache(CacheManager cacheManager) {
        return cacheManager.getCache("fileLevelCache", String.class, LogEntry.class);
    }

    @Bean
    public Cache<String, Long> fileOffsetCache(CacheManager cacheManager) {
        return cacheManager.getCache("fileOffsetCache", String.class, Long.class);
    }

    @Bean
    public Cache<String, Long> fileMaxIdCache(CacheManager cacheManager) {
        return cacheManager.getCache("fileMaxIdCache", String.class, Long.class);
    }



}
