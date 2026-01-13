package com.example.errors;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

import com.example.exceptions.MessageSendException;

@Provider
public class MessageSendExceptionMapper implements ExceptionMapper<MessageSendException> {

    @Override
    public Response toResponse(MessageSendException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(Map.of("error", e.getMessage()))
                .build();
    }
}
