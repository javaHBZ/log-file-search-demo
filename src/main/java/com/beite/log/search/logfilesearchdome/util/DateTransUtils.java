package com.beite.log.search.logfilesearchdome.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date类型工具类
 *
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @version 1.0
 * @date 2022年03月07日 13:40
 */
public class DateTransUtils {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_MIL = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String COMPACT_DATE_PATTERN = "yyyyMMdd";
    public static final String COMPACT_TIME_PATTERN = "HHmmss";
    public static final String MM_TIME_PATTERN = "HH:mm";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String MIL_PATTERN = "HH:mm:ss:SSS";

    public static Date getHourDateBegin(Date date) {
        if (date == null) {
            return null;
        }
        Date dateBegin = date;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateBegin = cal.getTime();
        } catch (Exception e) {
        }
        return dateBegin;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1 起点时间
     * @param date2 终点时间
     * @return 相差的天数
     */
    public static int differentDays(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1 起点时间
     * @param date2 终点时间
     * @return 相差的小时
     */
    public static int differentHours(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1 起点时间
     * @param date2 终点时间
     * @return 相差的天数
     */
    public static int differentMinutes(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60));
    }

    /**
     * 将日期按指定格式转换为字符串
     *
     * @param time        日期
     * @param datePattern 指定日期格式
     * @return string 日期字符串
     */
    public static String convertTime2String(Date time, String datePattern) {
        String str = "";
        if (time == null || datePattern == null) {
            return str;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            str = sdf.format(time);
        } catch (Exception e) {

        }
        return str;
    }

    /**
     * Description: 将指定定日期格式字符串转换为日期
     *
     * <p>
     *
     * @param dateStr     日期字符串
     * @param datePattern 日期格式
     * @return Date 日期
     */
    public static Date transString2Date(String dateStr, String datePattern) {
        Date date = null;
        try {
            if (dateStr != null && datePattern != null && dateStr.length() == datePattern.length()) {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                date = sdf.parse(dateStr);
            }
        } catch (ParseException e) {

        }

        return date;
    }

    /**
     * Description: 将指定定日期格式字符串转换为日期
     *
     * <p>
     *
     * @param dateStr     日期字符串
     * @param datePattern 日期格式
     * @return Date 日期
     */
    public static Date transString3Date(String dateStr, String datePattern) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            date = sdf.parse(dateStr);
        } catch (ParseException e) {

        }

        return date;
    }

    /**
     * @param date
     * @param addDays
     * @param datePattern
     * @return @Titile: 获取当前日期偏移天数字符串 @Description:
     */
    public static String addDay(Date date, int addDays, String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, addDays);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    public static Date addHours(Date date, int addHours) {
        Date dateTime = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, addHours);
            dateTime = cal.getTime();
        } catch (Exception e) {

        }
        return dateTime;
    }

    public static Date getDateBegin(Date date) {
        if (date == null) {
            return null;
        }
        Date dateBegin = date;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateBegin = cal.getTime();
        } catch (Exception e) {
        }
        return dateBegin;
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        Date dateBegin = date;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 0);
            dateBegin = cal.getTime();
        } catch (Exception e) {

        }
        return dateBegin;
    }

    /**
     * Description: 将日期字符串按从一种格式转换为另一种格式
     *
     * <p>
     *
     * @param sourceDateStr   日期字符串
     * @param fromDatePattern 源时间格式
     * @param toDatePattern   目标时间格式
     * @return
     */
    public static String format(String sourceDateStr, String fromDatePattern, String toDatePattern) {
        return transDateString2formatString(sourceDateStr, fromDatePattern, toDatePattern);
    }

    /**
     * Description: 将日期字符串按从一种格式转换为另一种格式
     *
     * <p>
     *
     * @param sourceDateStr   日期字符串
     * @param fromDatePattern 源时间格式
     * @param toDatePattern   目标时间格式
     * @return
     */
    public static String transDateString2formatString(
            String sourceDateStr, String fromDatePattern, String toDatePattern) {
        String dateString = sourceDateStr;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromDatePattern);
            Date date = sdf.parse(sourceDateStr);
            sdf.applyPattern(toDatePattern);
            dateString = sdf.format(date);

        } catch (ParseException e) {
        }

        return dateString;
    }

    /**
     * Description: 将日期字符串转换到到一个该月的最后一天,将该月的最后一天按指定时间格式转换为字符串输出。
     *
     * <p>
     *
     * @param sourceDateStr   日期字符串
     * @param fromDatePattern 日期字符串时间格式
     * @param toDatePattern   目标时间格式
     * @return
     */
    public static String transDateString2LastDayOfMonth(
            String sourceDateStr, String fromDatePattern, String toDatePattern) {
        String lastDayOfMonth = sourceDateStr;
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(fromDatePattern);
            Date month = sdf.parse(sourceDateStr);
            cal.setTime(month);
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            cal.add(Calendar.HOUR_OF_DAY, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);
            Date date = cal.getTime();
            sdf.applyPattern(toDatePattern);
            lastDayOfMonth = sdf.format(date);
        } catch (ParseException e) {

        }

        return lastDayOfMonth;
    }

    /**
     * Description: 将日期字符串转换到到一个该月的第一天,将该月的第一天按指定时间格式转换为字符串输出。
     *
     * <p>
     *
     * @param sourceDateStr   日期字符串
     * @param fromDatePattern 日期字符串时间格式
     * @param toDatePattern   目标时间格式
     * @return
     */
    public static String transDateString2FristDayOfMonth(
            String sourceDateStr, String fromDatePattern, String toDatePattern) {
        String lastDayOfMonth = sourceDateStr;
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(fromDatePattern);
            Date month = sdf.parse(sourceDateStr);
            cal.setTime(month);
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            cal.add(Calendar.HOUR_OF_DAY, 0);
            cal.add(Calendar.MINUTE, 0);
            cal.add(Calendar.SECOND, 0);
            Date date = cal.getTime();
            sdf.applyPattern(toDatePattern);
            lastDayOfMonth = sdf.format(date);
        } catch (ParseException e) {

        }

        return lastDayOfMonth;
    }

    public static String formate(Timestamp time, String datePattern) {
        return convertTime2String(time, datePattern);
    }

    /**
     * 将日期按指定格式转换为字符串
     *
     * @param time        日期
     * @param datePattern 指定日期格式
     * @return string 日期字符串
     */
    public static String convertTime2String(Timestamp time, String datePattern) {
        String str = "";
        if (time == null || datePattern == null) {
            return str;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            str = sdf.format(time);
        } catch (Exception e) {

        }
        return str;
    }

    public static String formate(Date time, String datePattern) {
        return convertTime2String(time, datePattern);
    }

    /**
     * Description: 将指定定日期格式字符串转换为日期
     *
     * <p>
     *
     * @param dateStr     日期字符串
     * @param datePattern 日期格式
     * @return Timestamp 日期
     */
    public static Timestamp transString2Timestamp(String dateStr, String datePattern) {
        Timestamp time = null;
        try {
            if (dateStr != null && datePattern != null && dateStr.length() == datePattern.length()) {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                long l = sdf.parse(dateStr).getTime();
                time = new Timestamp(l);
            }
        } catch (ParseException e) {

        }

        return time;
    }

    /**
     * Description: 判断日期字符串是否是指定格式。
     *
     * <p>
     *
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static boolean checkDateStrIsDateForamte(String dateStr, String datePattern) {
        boolean result = false;
        try {
            if (dateStr != null && datePattern != null && dateStr.length() == datePattern.length()) {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                // 新增判断错误格式，比如说 2011-14-14之类的，java还是能转换成日期\
                // author: yezhenhua
                Date date = sdf.parse(dateStr);
                String newDateStr = sdf.format(date);
                if (dateStr.equals(newDateStr)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {

        }

        return result;
    }

    /**
     * Description: 根据指定时间格式的当前时间字符串
     *
     * <p>
     *
     * @param datePattern
     * @return
     */
    public static String getCurrentDate(String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            dateStr = sdf.format(new Date());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 根据但前时间获取当前时间上一个月的开始时间
     *
     * <p>
     *
     * @param datePattern
     * @return
     */
    public static String getLastMonthBeigeDate(String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 根据但前时间获取当前时间上一个月的结束时间
     *
     * <p>
     *
     * @param datePattern
     * @return
     */
    public static String getLastMonthEndDate(String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 根据当前时间获取当前时间的月份开始时间
     *
     * <p>
     *
     * @param datePattern
     * @return
     */
    public static String getCurrentMonthBeigeDate(String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 根据当前时间获取当前时间的月份结束时间
     *
     * <p>
     *
     * @param datePattern
     * @return
     */
    public static String getCurrentMonthEndDate(String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 根据当前时间获取当前时间的月份开始时间
     *
     * <p>
     *
     * @return
     */
    public static Date getCurrentMonthBeigeDate() {
        Date date = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            date = cal.getTime();
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * Description: 根据当前时间获取当前时间的月份结束时间
     *
     * <p>
     *
     * @return
     */
    public static Date getCurrentMonthEndDate() {
        Date date = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            date = cal.getTime();
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * Description: 根据当前时间获取据当前时间前或者后几个月的份开始时间字符串
     *
     * <p>
     *
     * @param monthCount 据当前月份数
     * @return
     */
    public static Date getMonthBeginDateFromCurrent(int monthCount) {
        Date date = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, monthCount);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            date = cal.getTime();
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * Description: 根据当前时间获取据当前时间前或者后几个月的份结束时间字符串
     *
     * <p>
     *
     * @param monthCount 据当前月份数
     * @return
     */
    public static Date getMonthEndDateFromCurrent(int monthCount) {
        Date date = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, monthCount + 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            date = cal.getTime();
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * Description: 根据当前时间获取据当前时间前或者后几个月的份开始时间字符串
     *
     * <p>
     *
     * @param monthCount  据当前月份数
     * @param datePattern 指定返回日期格式字符串
     * @return
     */
    public static String getMonthBeginDateFromCurrent(int monthCount, String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, monthCount);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 根据当前时间获取据当前时间前或者后几个月的份结束时间字符串
     *
     * <p>
     *
     * @param monthCount  据当前月份数
     * @param datePattern 指定返回日期格式字符串
     * @return
     */
    public static String getMonthEndDateFromCurrent(int monthCount, String datePattern) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, monthCount + 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            dateStr = sdf.format(cal.getTime());
        } catch (Exception e) {

        }
        return dateStr;
    }

    /**
     * Description: 得到日期字符串的月份开始时间
     *
     * <p>
     *
     * @param sourceDateStr
     * @param fromDatePattern
     * @param toDatePattern
     * @return
     */
    public static String getMonthBeignDate(
            String sourceDateStr, String fromDatePattern, String toDatePattern) {
        String dateStr = sourceDateStr;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromDatePattern);
            Date date = sdf.parse(sourceDateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            sdf.applyPattern(toDatePattern);
            dateStr = sdf.format(cal.getTime());
        } catch (ParseException e) {

        }

        return dateStr;
    }

    /**
     * Description: 得到日期字符串的月份结束时间
     *
     * <p>
     *
     * @param sourceDateStr
     * @param fromDatePattern
     * @param toDatePattern
     * @return
     */
    public static String getMonthEndDate(
            String sourceDateStr, String fromDatePattern, String toDatePattern) {
        String dateStr = sourceDateStr;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromDatePattern);
            Date date = sdf.parse(sourceDateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            sdf.applyPattern(toDatePattern);
            dateStr = sdf.format(cal.getTime());
        } catch (ParseException e) {

        }

        return dateStr;
    }

    // 将字符串转换为数字
    public static int transStr2Number(String num) {
        Integer value = transStr2Number(num, Integer.class);
        if (value != null) {
            return value;
        } else {
            return 0;
        }
    }

    // 将字符串转换为数字
    @SuppressWarnings("unchecked")
    public static <T> T transStr2Number(String num, Class<T> clazz) {
        T instance = null;
        if (!isEmpty(num)) {
            num = trimToEmpty(num);
            try {
                if (Integer.class.equals(clazz)) {
                    instance = (T) Integer.valueOf(num);

                } else if (Float.class.equals(clazz)) {
                    instance = (T) Float.valueOf(num);

                } else if (Double.class.equals(clazz)) {
                    instance = (T) Double.valueOf(num);

                } else if (Byte.class.equals(clazz)) {
                    instance = (T) Byte.valueOf(num);

                } else if (Short.class.equals(clazz)) {
                    instance = (T) Short.valueOf(num);

                } else if (Long.class.equals(clazz)) {
                    instance = (T) Long.valueOf(num);

                } else if (BigDecimal.class.equals(clazz)) {
                    instance = (T) new BigDecimal(num);

                } else if (BigInteger.class.equals(clazz)) {
                    instance = (T) new BigInteger(num);

                } else {
                    instance = (T) Integer.valueOf(num);
                }
            } catch (NumberFormatException e) {
            }
        }

        return instance;
    }

    public static boolean isEmpty(String str) {
        return !(str != null ? str.trim().length() > 0 : false);
    }

    public static String trimToEmpty(String str) {
        return str != null ? str.trim() : "";
    }

    public static String trimToEmpty(Object obj) {
        return obj != null ? obj.toString().trim() : "";
    }

    /**
     * 获取传入时间字符串当前月的第一天的初始时刻 起始时间时分秒初始化为00:00:00
     *
     * @return
     */
    public static Date getTimeOfFristOrEndMonth(Date date, String b) {
        Calendar cal = Calendar.getInstance();
        try {
            cal = Calendar.getInstance();
            cal.setTime(date);
            if ("Y".equals(b)) {
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
            } else {
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
            }

        } catch (Exception e) {

        }
        return cal.getTime();
    }
}
