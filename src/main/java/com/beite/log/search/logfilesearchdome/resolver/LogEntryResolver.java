package com.beite.log.search.logfilesearchdome.resolver;

import com.beite.log.search.logfilesearchdome.model.LogEntry;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 2:14
 * @since 1.0
 */
public class LogEntryResolver {


    public static LogEntry resolverLine(String line) {
        if (!StringUtils.hasText(line)) {
            return null;
        }

        String regex = "^\\[(.*?)\\]\\s(.*?)\\s(.*?)\\s\\[(.*?)\\]\\s(.*?)\\s(.*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String traceId = matcher.group(1);
            String date = matcher.group(2);
            String time = matcher.group(3);
            String thread = matcher.group(4);
            String logLevel = matcher.group(5);
            String logMessage = matcher.group(6);

            System.out.println("TraceId: " + traceId);
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Thread: " + thread);
            System.out.println("LogLevel: " + logLevel);
            System.out.println("LogMessage: " + logMessage);
        } else {
            System.out.println("No match found.");
        }

        return null;
    }
}
