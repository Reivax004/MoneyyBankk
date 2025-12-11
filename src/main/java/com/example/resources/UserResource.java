package com.example.resources;

import com.example.models.User;
import com.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
        import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private UserService userService;
    private static final Map<Integer, User> DB = new ConcurrentHashMap<>();
    private static final AtomicInteger SEQ = new AtomicInteger(0);
    static {
        DB.put(SEQ.incrementAndGet(), new User("huang","Huang","Steven@huang.com",LocalDate.of(2025, 12, 06),"steven"));
        DB.put(SEQ.incrementAndGet(), new User());
    }
    @GET
    @Path("/")
    public Response list() {
        List<User> userList = userService.findAllUser();
        return Response.ok(userList).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        User u = userService.findUser(id);
        if (u == null) throw new NotFoundException("user %d introuvable".formatted(id));
        return Response.ok(u).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, @Valid User in) {
        if (!DB.containsKey(id)) throw new NotFoundException("user %d introuvable".formatted(id));
        in.setId(id);
        User u = userService.updateUser(in, id);
        return Response.ok(u).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        User removed = userService.deleteUser(id);
        if (removed == null) throw new NotFoundException("user %d introuvable".formatted(id));
        return Response.noContent().build();
    }

    @POST
    @Path("/")
    public Response subscribe(User user) {
        User created = userService.createUser(user);
        return Response.ok(created).build();
    }
}