package com.example.errors;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

import com.example.exceptions.EmailAlreadyUsedException;

@Provider
public class EmailAlreadyUsedMapper
        implements ExceptionMapper<EmailAlreadyUsedException> {

    @Override
    public Response toResponse(EmailAlreadyUsedException e) {
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(Map.of("error", e.getMessage()))
                .build();
    }
}
