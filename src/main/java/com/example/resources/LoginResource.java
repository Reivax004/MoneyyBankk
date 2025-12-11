package com.example.resources;

import com.example.service.UserService;

import java.util.Map;

import com.example.models.User;
import com.example.service.LoginService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    
    @Inject
    private LoginService loginService;

    @Inject
    private UserService userService;

    @POST
@Path("/login")
public Response login(User user) {

    if (user == null || user.getEmail() == null || user.getPassword() == null) {
         throw new BadRequestException("Email and password are required");
    }

    String token = loginService.login(user.getEmail(), user.getPassword());

    if (token == null) {
        throw new NotAuthorizedException("Invalid credentials");
    }

    return Response.ok(token).build();
}


}
