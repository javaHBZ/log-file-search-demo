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
 * @date 2023年04月07日 10:52
 * @since 1.0
 */
@Component
public class LogCacheFileDateTimeHolder implements LogCacheHolder {
    private final Cache fileDateTimeCache;

    public LogCacheFileDateTimeHolder(Cache fileDateTimeCache) {
        this.fileDateTimeCache = fileDateTimeCache;
    }

    @Override
    public void putCache(String dateTime, LogEntry logEntry) {
        this.fileDateTimeCache.put(new Element(dateTime + "_" + logEntry.getId(), logEntry));
    }

    @Override
    public void removeCache(String dateTime) {
        Pattern pattern = Pattern.compile("^" + dateTime + "_.*");
        for (Object key : this.fileDateTimeCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                this.fileDateTimeCache.remove(key);
            }
        }
    }

    @Override
    public List<LogEntry> geCache(String dateTime) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + dateTime + "_.*");
        for (Object key : this.fileDateTimeCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                Element element = this.fileDateTimeCache.get(key);
                if (element != null) {
                    searchLogEntry.add((LogEntry) element.getObjectValue());
                }
            }
        }
        return searchLogEntry;
    }

    @Override
    public LogEntry getLatestCache() {
        List<String> keysWithExpiryCheck = (List<String>) this.fileDateTimeCache.getKeysWithExpiryCheck();
        String lastKey = keysWithExpiryCheck.get(keysWithExpiryCheck.size() - 1);
        return (LogEntry) this.fileDateTimeCache.get(lastKey).getObjectValue();
    }
}
