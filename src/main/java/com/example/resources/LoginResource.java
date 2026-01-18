package com.example.resources;

import com.example.models.User;
import com.example.service.LoginService;
import com.example.responses.SuccessResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;
import java.util.Map;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    private LoginService loginService;

    @POST
    @Path("/login")
    public Response login(
            User user,
            @HeaderParam("X-Correlation-Id") String correlationIdHeader
    ) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new BadRequestException("Email and password are required");
        }

        String correlationId = (correlationIdHeader != null && !correlationIdHeader.isBlank())
                ? correlationIdHeader
                : UUID.randomUUID().toString();

        String token = loginService.login(user.getEmail(), user.getPassword(), correlationId);

        return SuccessResponse.of("Login successful", Map.of("token", token), "X-Correlation-Id", correlationId);

    }
}
