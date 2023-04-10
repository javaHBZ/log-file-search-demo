package com.beite.log.search.logfilesearchdome.resolver;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import com.beite.log.search.logfilesearchdome.util.DateTransUtils;
import com.beite.log.search.logfilesearchdome.util.EhcacheBuild;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileDateHolder;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileDateTimeHolder;
import com.beite.log.search.logfilesearchdome.util.LogCacheFileLevelHolder;
import com.beite.log.search.logfilesearchdome.util.LogCacheHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 2:14
 * @since 1.0
 */
@Slf4j
@Component
public class LogEntryResolver {

    private static Map<Class<? extends LogCacheHolder>, LogCacheHolder> LOG_CACHE_HOLDER_MAP = new HashMap<>();


    public LogEntryResolver(EhcacheBuild ehcacheBuild) {
        LogEntryResolver.LOG_CACHE_HOLDER_MAP = ehcacheBuild.buildLogCacheHolderMap();
    }

    public static LogEntry resolverLine(String filePath, String fileName, String line) {
        if (!StringUtils.hasText(line)) {
            return null;
        }
        LogCacheHolder logCacheFileDateHolder = LOG_CACHE_HOLDER_MAP.get(LogCacheFileDateHolder.class);
        LogCacheHolder logCacheFileDateTimeHolder = LOG_CACHE_HOLDER_MAP.get(LogCacheFileDateTimeHolder.class);
        LogCacheHolder logCacheFileLevelHolder = LOG_CACHE_HOLDER_MAP.get(LogCacheFileLevelHolder.class);
        // ^\[(?<traceId>[^\]]+)\] (?<time>\S+) \[(?<thread>[^\]]+)\] (?<level>\S+) (?<logger>\S+) - (?<message>.*\{.+?\})$
        //String regex = "^\\[(.*?)\\]\\s(.*?)\\s(.*?)\\s\\[(.*?)\\]\\s(.*?)\\s(.*)$";
        String patternStr = "\\[(?<traceId>[^\\]]+)]\\s(?<datetime>\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s\\[(?<thread>[^\\]]+)]\\s(?<level>\\S+)\\s+(?<logger>[^\\s]+)\\s+-\\s+(?<message>.*?(\\{.+\\})?.*)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(line);


        if (matcher.matches()) {
            String traceId = matcher.group("traceId");
            String datetime = matcher.group("datetime");
            String thread = matcher.group("thread");
            String level = matcher.group("level");
            String logger = matcher.group("logger");
            String message = matcher.group("message");
            Date date = DateTransUtils.transString2Date(datetime, DateTransUtils.DATE_TIME_PATTERN_MIL);
            String dateStr = DateTransUtils.convertTime2String(date, DateTransUtils.DATE_PATTERN);
            String timeStr = DateTransUtils.convertTime2String(date, DateTransUtils.TIME_PATTERN);
            logCacheFileDateHolder.putCache(dateStr, new LogEntry(filePath + fileName, traceId, thread, dateStr, timeStr, datetime, level, logger, message));
            logCacheFileDateTimeHolder.putCache(datetime, new LogEntry(filePath + fileName, traceId, thread, dateStr, timeStr, datetime, level, logger, message));
            logCacheFileLevelHolder.putCache(level, new LogEntry(filePath + fileName, traceId, thread, dateStr, timeStr, datetime, level, logger, message));
        } else {
            // 如果进入此方法 可能是普通消息(即没有带traceId的消息)或者是跟随上一条的错误消息
            String patternStr1 = "\\[?(?<traceId>[^\\]]*)\\]?\\s(?<datetime>\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s\\[(?<thread>[^\\]]+)]\\s(?<level>\\S+)\\s+(?<logger>[^\\s]+)\\s+-\\s+(?<message>.*?(\\{.+\\})?.*)";
            Pattern pattern1 = Pattern.compile(patternStr1);
            Matcher matcher1 = pattern1.matcher(line);
            if (matcher1.matches()) {
                String traceId = matcher1.group("traceId");
                String datetime = matcher1.group("datetime");
                String thread = matcher1.group("thread");
                String level = matcher1.group("level");
                String logger = matcher1.group("logger");
                String message = matcher1.group("message");
                Date date = DateTransUtils.transString2Date(datetime, DateTransUtils.DATE_TIME_PATTERN_MIL);
                String dateStr = DateTransUtils.convertTime2String(date, DateTransUtils.DATE_PATTERN);
                String timeStr = DateTransUtils.convertTime2String(date, DateTransUtils.TIME_PATTERN);
                logCacheFileDateHolder.putCache(dateStr, new LogEntry(filePath + fileName, "", thread, dateStr, timeStr, datetime, level, logger, message));
                logCacheFileDateTimeHolder.putCache(datetime, new LogEntry(filePath + fileName, "", thread, dateStr, timeStr, datetime, level, logger, message));
                logCacheFileLevelHolder.putCache(level, new LogEntry(filePath + fileName, "", thread, dateStr, timeStr, datetime, level, logger, message));
            } else {
                LogEntry latestCacheDate = logCacheFileDateHolder.getLatestCache();
                latestCacheDate.setMessage(latestCacheDate.getMessage() + "\n" + line);
                logCacheFileDateHolder.putCache(latestCacheDate.getDate(), latestCacheDate);

                LogEntry latestCacheDateTime = logCacheFileDateTimeHolder.getLatestCache();
                latestCacheDateTime.setMessage(latestCacheDateTime.getMessage() + "\n" + line);
                logCacheFileDateTimeHolder.putCache(latestCacheDateTime.getDateTime(), latestCacheDateTime);

                LogEntry latestCacheLevel = logCacheFileLevelHolder.getLatestCache();
                latestCacheLevel.setMessage(latestCacheLevel.getMessage() + "\n" + line);
                logCacheFileLevelHolder.putCache(latestCacheLevel.getLevel(), latestCacheLevel);

            }

        }

        return null;
    }
}
