package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;

import java.util.List;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 10:39
 * @since 1.0
 */
public interface LogCacheHolder {
    void putCache(String key, LogEntry logEntry);

    void removeCache(String key);

    List<LogEntry> getCache(String key);

    List<LogEntry> getCacheRange(String leftBoundaryValue, String rightBoundaryValue);

    LogEntry getLatestCache();
}
