package com.qmp.common.jsonutil.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DateDeserializer<T> extends JsonDeserializer<T> {

    private Class<T> clazz;

    private DateDeserializer(Class<T> type) {
        this.clazz = type;
    }

    public static <T> DateDeserializer<T> newInstance(Class<T> type) {
        return new DateDeserializer<T>(type);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return SimpleDateDeserializer.deserializer(clazz, p.getText());
    }
}
