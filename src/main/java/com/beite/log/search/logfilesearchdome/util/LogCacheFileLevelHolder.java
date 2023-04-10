package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import org.ehcache.Cache;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        String[] keySplit = level.split("_");
        String levelStr = keySplit[0];
        String leftBoundaryValue = keySplit[1];
        String rightBoundaryValue = keySplit[2];

        LocalDateTime startTime = LocalDateTime.parse(leftBoundaryValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(rightBoundaryValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (Cache.Entry<String, LogEntry> next : this.fileLevelCache) {
            LocalDateTime time = LocalDateTime.parse(next.getValue().getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            if (time.isAfter(startTime) && time.isBefore(endTime)) {
                if (levelStr.equals(next.getValue().getLevel())) {
                    searchLogEntry.add(next.getValue());
                }
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
