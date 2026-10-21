package com.gym.http;

import java.util.Map;

public record Response(int status, Object body) {

    public static Response ok(Object body) {
        return new Response(200, body);
    }

    public static Response created(Object body) {
        return new Response(201, body);
    }

    public static Response noContent() {
        return new Response(204, null);
    }

    public static Response error(int status, String message) {
        return new Response(status, Map.of("error", message));
    }
}
