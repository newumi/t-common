package com.qmp.common.jsonutil.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateSerializers<T> extends JsonSerializer<T> {

    private DateSerializers() {}

    public static <T> DateSerializers<T> newInstance(Class<T> type) {
        return new DateSerializers<T>();
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value != null) {
            if (Date.class == value.getClass()) {
                Date date = (Date) value;
                gen.writeNumber(date.getTime());
            } else if (LocalDateTime.class == value.getClass()) {
                LocalDateTime dateTime = (LocalDateTime) value;
                long timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
            } else if (LocalDate.class == value.getClass()) {
                LocalDate date = (LocalDate) value;
                long timestamp =
                        date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
            } else if (LocalTime.class == value.getClass()) {
                LocalTime time = (LocalTime) value;
                long timestamp = LocalDateTime.of(LocalDate.now(), time)
                        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }
    }
}
