package com.beite.log.search.logfilesearchdome.model;

import com.beite.log.search.logfilesearchdome.LogFileSearchDomeApplication;
import org.ehcache.Cache;

import java.io.Serializable;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 0:40
 * @since 1.0
 */
public class LogEntry implements Serializable {
    private static final long serialVersionUID = -6849794470754669918L;

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 行号
     */
    private String lineNo;

    /**
     * 调用链跟踪ID
     */
    private String traceId;

    /**
     * 线程名称
     */
    private String thread;

    /**
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

    /**
     * 日期时间
     */
    private String dateTime;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 全限定类名
     */
    private String fullyQualifiedClassName;

    /**
     * 消息内容
     */
    private String message;

    public LogEntry(String fileName,String traceId,String thread,String date, String time,String dateTime, String level, String fullyQualifiedClassName, String message) {
        Cache<String,Long> fileMaxIdCache = LogFileSearchDomeApplication.SpringContextUtil.getBean("fileMaxIdCache", Cache.class);
        Long maxId = fileMaxIdCache.get(fileName);
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId++;
        }

        fileMaxIdCache.put(fileName, maxId);
        this.id = maxId;
        this.traceId = traceId;
        this.thread = thread;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.level = level;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.message = message;
    }


    public LogEntry(Long id, String lineNo, String traceId, String thread, String date, String time, String dateTime, String level, String fullyQualifiedClassName, String message) {
        this.id = id;
        this.lineNo = lineNo;
        this.traceId = traceId;
        this.thread = thread;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.level = level;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(this.traceId).append("]").append(" ");
        sb.append(this.dateTime).append(" ");
        sb.append("[").append(this.thread).append("]").append(" ");
        sb.append(this.level).append(" ");
        sb.append(this.fullyQualifiedClassName).append(" ");
        sb.append(this.message).append("\n");
        return sb.toString();
    }
}
