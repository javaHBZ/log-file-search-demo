package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
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

    private final Cache fileLevelCache;

    public LogCacheFileLevelHolder(Cache fileLevelCache) {
        this.fileLevelCache = fileLevelCache;
    }

    @Override
    public void putCache(String level, LogEntry logEntry) {
        this.fileLevelCache.put(new Element(level + "_" + logEntry.getId(), logEntry));
    }

    @Override
    public void removeCache(String level) {
        Pattern pattern = Pattern.compile("^" + level + "_.*");
        for (Object key : this.fileLevelCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                this.fileLevelCache.remove(key);
            }
        }
    }

    @Override
    public List<LogEntry> geCache(String level) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + level + "_.*");
        for (Object key : this.fileLevelCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                Element element = this.fileLevelCache.get(key);
                if (element != null) {
                    searchLogEntry.add((LogEntry) element.getObjectValue());
                }
            }
        }
        return searchLogEntry;
    }

    @Override
    public LogEntry getLatestCache() {
        List<String> keysWithExpiryCheck = (List<String>) this.fileLevelCache.getKeysWithExpiryCheck();
        String lastKey = keysWithExpiryCheck.get(keysWithExpiryCheck.size() - 1);
        return (LogEntry) this.fileLevelCache.get(lastKey).getObjectValue();
    }
}
