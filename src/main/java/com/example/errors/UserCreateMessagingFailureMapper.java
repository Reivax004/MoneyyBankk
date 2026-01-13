package com.example.errors;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import com.example.exceptions.UserCreateMessagingFailureException;

@Provider
public class UserCreateMessagingFailureMapper implements ExceptionMapper<UserCreateMessagingFailureException> {
    @Override
    public Response toResponse(UserCreateMessagingFailureException e) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(Map.of("error", e.getMessage()))
                .build();
    }

}
