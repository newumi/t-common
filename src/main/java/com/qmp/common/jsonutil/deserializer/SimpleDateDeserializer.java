package com.qmp.common.jsonutil.deserializer;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.*;
import java.util.Date;

public class SimpleDateDeserializer<T> {

    private static final String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
            "yyyy.MM.dd HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒", "yyyy-MM-dd", "yyyy/MM/dd",
            "yyyy.MM.dd", "HH:mm:ss", "HH时mm分ss秒", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyyMMddHHmmss", "yyyyMMddHHmmssSSS", "yyyyMMdd", "EEE, dd MMM yyyy HH:mm:ss z",
            "EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM", "yyyyMM", "yyyy/MM",};


    @SuppressWarnings("unchecked")
    public static <T> T deserializer(Class<T> clazz, String text) {
        if (clazz == Date.class) {
            return (T) toDate(text);
        } else if (clazz == LocalDateTime.class) {
            try {
                return (T) LocalDateTime.parse(text);
            } catch (Exception e) {
                Date date = toDate(text);
                if (date != null) {
                    Instant instant = date.toInstant();
                    return (T) instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                }
            }
        } else if (clazz == LocalDate.class) {
            try {
                return (T) LocalDate.parse(text);
            } catch (Exception e) {
                Date date = toDate(text);
                if (date != null) {
                    Instant instant = date.toInstant();
                    return (T) instant.atZone(ZoneId.systemDefault()).toLocalDate();
                }
            }
        } else if (clazz == LocalTime.class) {
            try {
                return (T) LocalTime.parse(text);
            } catch (Exception e) {
                Date date = toDate(text);
                if (date != null) {
                    Instant instant = date.toInstant();
                    return (T) instant.atZone(ZoneId.systemDefault()).toLocalTime();
                }
            }
        }
        return null;
    }


    private static Date toDate(String text) {
        Date date = null;
        if (NumberUtils.isCreatable(text)) {
            if (text.length() == 10) {
                date = new Date(Long.valueOf(text) * 1000);
            } else {
                date = new Date(Long.valueOf(text));
            }
        } else {
            try {
                date = DateUtils.parseDate(text, PATTERNS);
            } catch (ParseException e) {
            }
        }
        return date;

    }

}
