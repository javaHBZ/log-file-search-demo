package com.beite.log.search.logfilesearchdome.api;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import com.beite.log.search.logfilesearchdome.util.EhcacheBuild;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileDateHolder;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileDateTimeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 13:26
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/log_file")
public class LogFileController {
    @Autowired
    private EhcacheBuild ehcacheBuild;

    @GetMapping("/search_date")
    public List<LogEntry> searchByDate(@RequestParam("date") String date) {
        List<LogEntry> logEntries = ehcacheBuild.buildLogCacheHolder(LogCacheFileDateHolder.class).getCache(date);
        logEntries.sort(Comparator.comparingLong(LogEntry::getId));
        return logEntries;
    }

    @GetMapping("/search_date_time")
    public List<LogEntry> searchByDateTime(@RequestParam("dateTime") String dateTime) {
        List<LogEntry> logEntries = ehcacheBuild.buildLogCacheHolder(LogCacheFileDateTimeHolder.class).getCache(dateTime);
        logEntries.sort(Comparator.comparingLong(LogEntry::getId));
        return logEntries;
    }

    @GetMapping("/search_date_time_range")
    public List<LogEntry> searchByDateTime(@RequestParam("dateTimeStart") String dateTimeStart,
                                           @RequestParam("dateTimeEnd") String dateTimeEnd) {
        List<LogEntry> logEntries = ehcacheBuild.buildLogCacheHolder(LogCacheFileDateTimeHolder.class).getCacheRange(dateTimeStart, dateTimeEnd);
        logEntries.sort(Comparator.comparingLong(LogEntry::getId));
        return logEntries;
    }

}
