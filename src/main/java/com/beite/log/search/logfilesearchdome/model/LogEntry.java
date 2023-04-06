package com.beite.log.search.logfilesearchdome.model;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月07日 0:40
 * @since 1.0
 */
public class LogEntry {
    private static int nextId = 0; // 日志项ID自增值

    /**
     * 日志ID
     */
    private int id;

    /**
     * 行号
     */
    private String lineNo;

    /**
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

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

    public LogEntry(String date, String time, String level, String fullyQualifiedClassName, String message) {
        this.id = nextId++;
        this.date = date;
        this.time = time;
        this.level = level;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.message = message;
    }


    public LogEntry(int id, String lineNo, String date, String time, String level, String fullyQualifiedClassName, String message) {
        this.id = id;
        this.lineNo = lineNo;
        this.date = date;
        this.time = time;
        this.level = level;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.message = message;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        LogEntry.nextId = nextId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
