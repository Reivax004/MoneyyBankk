package com.example.errors;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import com.example.exceptions.LoanMessagingFailureException;

@Provider
public class LoanMessagingFailureMapper implements ExceptionMapper<LoanMessagingFailureException> {
    @Override
    public Response toResponse(LoanMessagingFailureException e) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(Map.of("error", e.getMessage()))
                .build();
    }

}
