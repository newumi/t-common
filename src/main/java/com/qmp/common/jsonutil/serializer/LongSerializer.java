package com.qmp.common.jsonutil.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 描述: long序列化，可以解决前端不能接收19位数字问题
 * 作者: 刘院民
 * 日期: 2021-10-23 14:53:30
 */
public class LongSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value.toString().length() >= 16) {
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(value.longValue());
        }
    }

}
