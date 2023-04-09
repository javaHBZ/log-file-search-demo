package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import org.ehcache.Cache;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 0:57
 * @since 1.0
 */
@Component
public class EhcacheBuild {
    @Autowired
    private ObjectProvider<LogCacheHolder> cacheProvider;

    @Autowired
    @Qualifier("fileDateCache")
    private Cache<String, LogEntry> fileDateCache;

    public LogCacheHolder buildLogCacheHolder(Class<? extends LogCacheHolder> clazz) {
        Iterator<LogCacheHolder> iterator = cacheProvider.stream().iterator();
        while (iterator.hasNext()) {
            LogCacheHolder next = iterator.next();
            System.out.println(next.getClass().getName());
            if (next.getClass().getName().equals(clazz.getName())) {
                return next;
            }
        }

        return new LogCacheFileDateHolder(fileDateCache);
    }

    public Map<Class<? extends LogCacheHolder>, LogCacheHolder> buildLogCacheHolderMap() {
        Map<Class<? extends LogCacheHolder>, LogCacheHolder> logCacheHolderMap = new HashMap<>();

        Iterator<LogCacheHolder> iterator = cacheProvider.stream().iterator();
        while (iterator.hasNext()) {
            LogCacheHolder next = iterator.next();
            logCacheHolderMap.put(next.getClass(), next);
        }
        return logCacheHolderMap;
    }
}
