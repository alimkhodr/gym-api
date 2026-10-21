package com.gym.http;

import com.gym.exception.BadRequestException;
import com.sun.net.httpserver.HttpExchange;

import java.util.Arrays;
import java.util.List;

public class Request {

    private final HttpExchange exchange;
    private final List<String> segments;

    public Request(HttpExchange exchange) {
        this.exchange = exchange;
        String path = exchange.getRequestURI().getPath().substring(exchange.getHttpContext().getPath().length());
        this.segments = Arrays.stream(path.split("/")).filter(segment -> !segment.isEmpty()).toList();
    }

    public String method() {
        return exchange.getRequestMethod();
    }

    public int size() {
        return segments.size();
    }

    public String segment(int index) {
        return segments.get(index);
    }

    public long id(int index) {
        try {
            return Long.parseLong(segments.get(index));
        } catch (NumberFormatException e) {
            throw new BadRequestException("invalid id: " + segments.get(index));
        }
    }

    public <T> T body(Class<T> type) {
        return Json.read(exchange.getRequestBody(), type);
    }
}
