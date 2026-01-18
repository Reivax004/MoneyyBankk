package com.example.resources;

import com.example.models.User;
import com.example.service.UserService;
import com.example.responses.SuccessResponse;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("/all")
    public Response list() {
        List<User> userList = userService.findAllUser();
        return SuccessResponse.of("Users fetched successfully", userList);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        User u = userService.findUser(id);
        if (u == null) throw new NotFoundException("User %d not found".formatted(id));
        return SuccessResponse.of("User fetched successfully", u);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, @Valid User in) {
        User u = userService.updateUser(in, id);
        if (u == null) throw new NotFoundException("User %d not found".formatted(id));
        return SuccessResponse.of("User updated successfully", u);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        User removed = userService.deleteUser(id);
        if (removed == null) throw new NotFoundException("User %d not found".formatted(id));
        return SuccessResponse.of("User deleted successfully", removed);
    }

    @POST
    @Path("/register")
    public Response register(User user) {
        User created = userService.createUser(user);
        return SuccessResponse.of("User registered successfully", created);
    }
}
