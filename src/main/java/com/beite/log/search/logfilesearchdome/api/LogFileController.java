package com.beite.log.search.logfilesearchdome.api;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import com.beite.log.search.logfilesearchdome.util.EhcacheBuild;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileDateHolder;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileDateTimeHolder;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileLevelHolder;
import org.apache.commons.text.StringEscapeUtils;
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
    public String searchByDate(@RequestParam("date") String date) {
        List<LogEntry> logEntries = ehcacheBuild.buildLogCacheHolder(LogCacheFileDateHolder.class).getCache(date);
        logEntries.sort(Comparator.comparingLong(LogEntry::getId));

        StringBuilder resultMessage = new StringBuilder();
        logEntries.forEach(item -> {
            resultMessage.append(item.toString());
        });

        return StringEscapeUtils.escapeHtml4(resultMessage.toString());
    }

    @GetMapping("/search_date_time_range")
    public String searchByDateTime(@RequestParam("dateTimeStart") String dateTimeStart,
                                           @RequestParam("dateTimeEnd") String dateTimeEnd) {
        List<LogEntry> logEntries = ehcacheBuild.buildLogCacheHolder(LogCacheFileDateTimeHolder.class).getCacheRange(dateTimeStart, dateTimeEnd);
        logEntries.sort(Comparator.comparingLong(LogEntry::getId));

        StringBuilder resultMessage = new StringBuilder();
        logEntries.forEach(item -> {
            resultMessage.append(item.toString());
        });

        return StringEscapeUtils.escapeHtml4(resultMessage.toString());
    }

    @GetMapping("/search_level_date_time_range")
    public String searchByLevelDateTime(@RequestParam("level") String level,
                                   @RequestParam("dateTimeStart") String dateTimeStart,
                                   @RequestParam("dateTimeEnd") String dateTimeEnd
    ){
        String key = level + "_" + dateTimeStart + "_" + dateTimeEnd;
        List<LogEntry> logEntries = ehcacheBuild.buildLogCacheHolder(LogCacheFileLevelHolder.class).getCache(key);

        logEntries.sort(Comparator.comparingLong(LogEntry::getId));
        StringBuilder resultMessage = new StringBuilder();
        logEntries.forEach(item -> {
            resultMessage.append(item.toString());
        });

        return StringEscapeUtils.escapeHtml4(resultMessage.toString());
    }

}
