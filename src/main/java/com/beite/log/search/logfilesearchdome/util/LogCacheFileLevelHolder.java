package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import org.ehcache.Cache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 10:57
 * @since 1.0
 */
@Component
public class LogCacheFileLevelHolder implements LogCacheHolder {

    private final Cache<String, LogEntry> fileLevelCache;

    private static String MAX_KEY;

    public LogCacheFileLevelHolder(Cache<String, LogEntry> fileLevelCache) {
        this.fileLevelCache = fileLevelCache;
    }

    @Override
    public void putCache(String level, LogEntry logEntry) {
        this.fileLevelCache.put(level + "_" + logEntry.getId(), logEntry);
        MAX_KEY = level + "_" + logEntry.getId();
    }

    @Override
    public void removeCache(String level) {

    }

    @Override
    public List<LogEntry> getCache(String level) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        for (Cache.Entry<String, LogEntry> next : this.fileLevelCache) {
            if (level.equals(next.getValue().getLevel())) {
                searchLogEntry.add(next.getValue());
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
        return this.fileLevelCache.get(MAX_KEY);
    }
}
