package com.beite.log.search.logfilesearchdome.util;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 0:57
 * @since 1.0
 */
@Component
public class EhcacheUtil {
    private final Cache fileDateCache;

    private final Cache fileTimeCache;

    private final Cache fileLevelCache;

    public EhcacheUtil(Cache fileDateCache,
                       Cache fileTimeCache,
                       Cache fileLevelCache) {
        this.fileDateCache = fileDateCache;
        this.fileTimeCache = fileTimeCache;
        this.fileLevelCache = fileLevelCache;
    }



    public void putFileDateCache(String date, LogEntry logEntry) {
        this.fileDateCache.put(new Element(date + "_" + logEntry.getId(), logEntry));
    }

    public void removeDateCache(String date) {
        Pattern pattern = Pattern.compile("^" + date + "_.*");
        for (Object key : this.fileDateCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                this.fileDateCache.remove(key);
            }
        }
    }

    public List<LogEntry> getFileDateCache(String date) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + date + "_.*");
        for (Object key : this.fileDateCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                searchLogEntry.add((LogEntry) this.fileDateCache.get(key).getObjectValue());
            }
        }
        return searchLogEntry;
    }






    public void putFileTimeCache(String time, LogEntry logEntry) {
        this.fileTimeCache.put(new Element(time + "_" + logEntry.getId(), logEntry));
    }

    public void removeTimeCache(String time) {
        Pattern pattern = Pattern.compile("^" + time + "_.*");
        for (Object key : this.fileTimeCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                this.fileTimeCache.remove(key);
            }
        }
    }

    public List<LogEntry> getFileTimeCache(String time) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + time + "_.*");
        for (Object key : this.fileTimeCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                searchLogEntry.add((LogEntry) this.fileTimeCache.get(key).getObjectValue());
            }
        }
        return searchLogEntry;
    }




    public void putFileLevelCache(String level, LogEntry logEntry) {
        this.fileLevelCache.put(new Element(level + "_" + logEntry.getId(), logEntry));
    }

    public void removeLevelCache(String level) {
        Pattern pattern = Pattern.compile("^" + level + "_.*");
        for (Object key : this.fileLevelCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                this.fileLevelCache.remove(key);
            }
        }
    }

    public List<LogEntry> getFileLevelCache(String level) {
        List<LogEntry> searchLogEntry = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + level + "_.*");
        for (Object key : this.fileLevelCache.getKeys()) {
            if (pattern.matcher(key.toString()).matches()) {
                searchLogEntry.add((LogEntry) this.fileLevelCache.get(key).getObjectValue());
            }
        }
        return searchLogEntry;
    }


    public Cache getFileDateCache() {
        return fileDateCache;
    }

    public Cache getFileTimeCache() {
        return fileTimeCache;
    }

    public Cache getFileLevelCache() {
        return fileLevelCache;
    }


}
