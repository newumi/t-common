package com.qmp.common.jsonutil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.qmp.common.jsonutil.deserializer.DateDeserializer;
import com.qmp.common.jsonutil.serializer.LongSerializer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 描述: java bean对象与json之间的转换
 * 作者: 刘院民
 * 日期: 2021-10-23 14:53:30
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class,DateDeserializer.newInstance(LocalDate.class));
        javaTimeModule.addDeserializer(LocalTime.class, DateDeserializer.newInstance(LocalTime.class));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addSerializer(Long.class,new LongSerializer());
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.registerModule(javaTimeModule);
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("json序列化出错：{}", obj, e);
            throw new RuntimeException(String.format("json序列化出错：%s",obj));
        }
    }
    
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception e) {
            log.error("json解析出错：{}", json, e);
            throw new RuntimeException(String.format("json序列化出错：%s",json));
        }
    }

    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (Exception e) {
            log.error("json解析出错：{}", json, e);
            throw new RuntimeException(String.format("json序列化出错：%s",json));
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (Exception e) {
            log.error("json解析出错：{}", json, e);
            throw new RuntimeException(String.format("json序列化出错：%s",json));
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            log.error("json解析出错：{}", json, e);
            throw new RuntimeException(String.format("json序列化出错：%s",json));
        }
    }
}
