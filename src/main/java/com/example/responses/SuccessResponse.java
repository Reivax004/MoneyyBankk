package com.example.responses;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

public class SuccessResponse {

    public static Response of(String message, Object data) {
        return Response.ok(Map.of(
                "message", message,
                "data", data
        ), MediaType.APPLICATION_JSON_TYPE).build();
    }

    public static Response of(String message) {
        return Response.ok(Map.of(
                "message", message
        ), MediaType.APPLICATION_JSON_TYPE).build();
    }
    public static Response of(String message, Object data, String headerName, String headerValue) {
        return Response.ok(Map.of(
                        "message", message,
                        "data", data
                ), MediaType.APPLICATION_JSON_TYPE)
                .header(headerName, headerValue)
                .build();
    }
}
