package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import com.beite.log.search.logfilesearchdome.wapper.IdListWrapper;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 10:52
 * @since 1.0
 */
@Component
public class LogCacheFileDateTimeHolder implements LogCacheHolder {
    private final Cache<String, LogEntry> fileDateTimeCache;


    @Autowired
    private Cache<LocalDateTime, IdListWrapper> dateTimeToIdMap;


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
        LocalDateTime startTime = LocalDateTime.parse(leftBoundaryValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(rightBoundaryValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<LocalDateTime, IdListWrapper> subMap = getSubMap(startTime, endTime);

        for (Map.Entry<LocalDateTime, IdListWrapper> entry : subMap.entrySet()) {
            for (Long id : entry.getValue().getIds()) {
                String cacheKey = entry.getKey().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "_" + id;
                LogEntry logEntry = this.fileDateTimeCache.get(cacheKey);
                if (logEntry != null) {
                    searchLogEntry.add(logEntry);
                }
            }

        }

        return searchLogEntry;
    }

    @Override
    public LogEntry getLatestCache() {
        return this.fileDateTimeCache.get(MAX_KEY);
    }

    private Map<LocalDateTime, IdListWrapper> getSubMap(LocalDateTime startTime, LocalDateTime endTime) {
        Map<LocalDateTime, IdListWrapper> subMap = new HashMap<>();
        for (Cache.Entry<LocalDateTime, IdListWrapper> entry : this.dateTimeToIdMap) {
            if (!entry.getKey().isBefore(startTime) && !entry.getKey().isAfter(endTime)) {
                subMap.put(entry.getKey(), entry.getValue());
            }
        }
        return subMap;
    }

}
