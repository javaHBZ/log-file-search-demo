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
 * @date 2023年04月07日 10:43
 * @since 1.0
 */
@Component
public class LogCacheFileDateHolder implements LogCacheHolder {

    private final Cache fileDateCache;

    public LogCacheFileDateHolder(Cache fileDateCache) {
        this.fileDateCache = fileDateCache;
    }

    @Override
    public void putCache(String date, LogEntry logEntry) {
        this.fileDateCache.put(new Element(date + "_" + logEntry.getId(), logEntry));
    }

    @Override
    public void removeCache(String date) {
        Pattern pattern = Pattern.compile("^" + date + "_.*");
        for (Object key : this.fileDateCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                this.fileDateCache.remove(key);
            }
        }
    }

    @Override
    public List<LogEntry> geCache(String date) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + date + "_.*");
        for (Object key : this.fileDateCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                Element element = this.fileDateCache.get(key);
                if (element != null) {
                    searchLogEntry.add((LogEntry) element.getObjectValue());
                }
            }
        }
        return searchLogEntry;
    }

    @Override
    public LogEntry getLatestCache() {
        List<String> keysWithExpiryCheck = (List<String>) this.fileDateCache.getKeysWithExpiryCheck();
        String lastKey = keysWithExpiryCheck.get(keysWithExpiryCheck.size() - 1);
        return (LogEntry) this.fileDateCache.get(lastKey).getObjectValue();
    }
}
