package com.gym.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gym.exception.BadRequestException;

import java.io.IOException;
import java.io.InputStream;

public final class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(SerializationFeature.INDENT_OUTPUT);

    private Json() {
    }

    public static <T> T read(InputStream input, Class<T> type) {
        try {
            return MAPPER.readValue(input, type);
        } catch (IOException e) {
            throw new BadRequestException("invalid json body");
        }
    }

    public static byte[] write(Object value) {
        try {
            return MAPPER.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
