package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import org.ehcache.Cache;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 10:52
 * @since 1.0
 */
@Component
public class LogCacheFileDateTimeHolder implements LogCacheHolder {
    private final Cache<String, LogEntry> fileDateTimeCache;

    private  static String MAX_KEY ;

    public LogCacheFileDateTimeHolder(Cache<String, LogEntry> fileDateTimeCache) {
        this.fileDateTimeCache = fileDateTimeCache;
    }

    @Override
    public void putCache(String dateTime, LogEntry logEntry) {
        this.fileDateTimeCache.put(dateTime + "_" + logEntry.getId(), logEntry);
        MAX_KEY = dateTime + "_" + logEntry.getId();
    }

    @Override
    public void removeCache(String dateTime) {

    }

    @Override
    public List<LogEntry> getCache(String dateTime) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        for (Cache.Entry<String, LogEntry> next : this.fileDateTimeCache) {
            if (dateTime.equals(next.getValue().getDateTime())) {
                searchLogEntry.add(next.getValue());
            }
        }
        return searchLogEntry;
    }

    @Override
    public List<LogEntry> getCacheRange(String leftBoundaryValue, String rightBoundaryValue) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.parse(leftBoundaryValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        LocalDateTime endTime = LocalDateTime.parse(rightBoundaryValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        for (Cache.Entry<String, LogEntry> next : this.fileDateTimeCache) {
            LocalDateTime time = LocalDateTime.parse(next.getValue().getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            if (time.isAfter(startTime) && time.isBefore(endTime)) {
                searchLogEntry.add(next.getValue());
            }
        }
        return searchLogEntry;
    }

    @Override
    public LogEntry getLatestCache() {
        return this.fileDateTimeCache.get(MAX_KEY);
    }
}
