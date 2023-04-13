package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import com.beite.log.search.logfilesearchdome.wapper.IdListWrapper;
import org.ehcache.Cache;
import org.ehcache.core.InternalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 10:43
 * @since 1.0
 */
@Component
public class LogCacheFileDateHolder implements LogCacheHolder {

    private final Cache<String, LogEntry> fileDateCache;


    @Autowired
    private Cache<String, IdListWrapper> dateToIdMapCache;

    private  static String MAX_KEY ;

    public LogCacheFileDateHolder(Cache<String, LogEntry> fileDateCache) {
        this.fileDateCache = fileDateCache;
    }

    @Override
    public void putCache(String date, LogEntry logEntry) {
        this.fileDateCache.put(date + "_" + logEntry.getId(), logEntry);
        MAX_KEY = date + "_" + logEntry.getId();
    }

    @Override
    public void removeCache(String date) {
    }

    @Override
    public List<LogEntry> getCache(String date) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        IdListWrapper dateIds =dateToIdMapCache.get(date);
        if (dateIds == null) {
            return searchLogEntry;
        }

        List<Long> ids = dateIds.getIds();
        for (Long dateId : ids) {
            String cacheKey = date + "_" + dateId;
            LogEntry logEntry = this.fileDateCache.get(cacheKey);
            if (logEntry != null) {
                searchLogEntry.add(logEntry);
            }
        }

        return searchLogEntry;
    }

    @Override
    public List<LogEntry> getCacheRange(String leftBoundaryValue, String rightBoundaryValue) {
        return null;
    }

    @Override
    public LogEntry getLatestCache() {
        return this.fileDateCache.get(MAX_KEY);
    }
}
