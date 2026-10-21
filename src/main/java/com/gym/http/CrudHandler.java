package com.gym.http;

import com.gym.exception.ApiException;
import com.gym.exception.MethodNotAllowedException;
import com.gym.exception.NotFoundException;
import com.gym.model.BaseEntity;
import com.gym.service.CrudService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Supplier;

public class CrudHandler<T extends BaseEntity> implements HttpHandler {

    private final CrudService<T> service;
    private final Class<T> type;

    public CrudHandler(CrudService<T> service, Class<T> type) {
        this.service = service;
        this.type = type;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Response response;
        try {
            response = route(new Request(exchange));
        } catch (ApiException e) {
            response = Response.error(e.getStatus(), e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            response = Response.error(500, "internal server error");
        }
        send(exchange, response);
    }

    protected Response nested(Request request, long id) {
        throw new NotFoundException("route not found");
    }

    protected Response get(Request request, Supplier<Object> supplier) {
        if (!request.method().equals("GET")) {
            throw new MethodNotAllowedException(request.method());
        }
        return Response.ok(supplier.get());
    }

    private Response route(Request request) {
        if (request.size() == 0) {
            return collection(request);
        }
        if (request.size() == 1) {
            return item(request, request.id(0));
        }
        return nested(request, request.id(0));
    }

    private Response collection(Request request) {
        return switch (request.method()) {
            case "GET" -> Response.ok(service.findAll());
            case "POST" -> Response.created(service.create(request.body(type)));
            default -> throw new MethodNotAllowedException(request.method());
        };
    }

    private Response item(Request request, long id) {
        return switch (request.method()) {
            case "GET" -> Response.ok(service.findById(id));
            case "PUT" -> Response.ok(service.update(id, request.body(type)));
            case "DELETE" -> {
                service.delete(id);
                yield Response.noContent();
            }
            default -> throw new MethodNotAllowedException(request.method());
        };
    }

    private void send(HttpExchange exchange, Response response) throws IOException {
        if (response.body() == null) {
            exchange.sendResponseHeaders(response.status(), -1);
            exchange.close();
            return;
        }
        byte[] bytes = Json.write(response.body());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(response.status(), bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
