package com.example.resources;

import com.example.models.User;
import com.example.service.StatisticsService;
import com.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Map;

@Path("/statistics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatisticsResource {

    @Inject
    private StatisticsService statisticService;

    @Inject
    private UserService userService;

    @GET
    @Path("/account")
    public Response getAccountStatistics(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotAuthorizedException("User not authenticated");
        }

        Map<String, Object> stats =
                statisticService.getAccountStatistics(user);

        return Response.ok(stats).build();
    }
}
