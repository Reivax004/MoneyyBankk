package com.example.resources;

import com.example.models.User;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.sql.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private static final Map<Integer, User> DB = new ConcurrentHashMap<>();
    private static final AtomicInteger SEQ = new AtomicInteger(0);
    static {
        DB.put(SEQ.incrementAndGet(), new User("huang","Huang","Steven@huang.com",new Date(-2020),"steven"));
        DB.put(SEQ.incrementAndGet(), new User());
    }

    @GET
    public Collection<User> list() { return DB.values(); }

    @GET @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        User u = DB.get(id);
        if (u == null) throw new NotFoundException("User %d introuvable".formatted(id));
        return Response.ok(u).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, @Valid User in) {
        if (!DB.containsKey(id)) throw new NotFoundException("User %d introuvable".formatted(id));
        in.setId(id);
        DB.put(id, in);
        return Response.ok(in).build();
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        User removed = DB.remove(id);
        if (removed == null) throw new NotFoundException("User %d introuvable".formatted(id));
        return Response.noContent().build();
    }
}
