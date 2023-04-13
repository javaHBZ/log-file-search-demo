package com.beite.log.search.logfilesearchdome.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月13日 2:20
 * @since 1.0
 */
@Component
public class EhcacheShutdownHook {

    @Autowired
    private CacheManager cacheManager;

    public void closeCacheManager() {
        if (cacheManager != null) {
            for (String cacheAlias : getCacheNames()) {
                Cache<?, ?> cache = cacheManager.getCache(cacheAlias, Object.class, Object.class);
                if (cache != null) {
                    cache.clear();
                }
            }
            cacheManager.close();
        }
    }

    public List<String> getCacheNames() {
        return Arrays.asList("fileDateCache", "fileDateTimeCache", "fileLevelCache", "fileOffsetCache", "fileMaxIdCache");
    }
}
