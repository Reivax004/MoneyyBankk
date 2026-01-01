package com.example.resources;

import com.example.models.Transaction;
import com.example.models.User;
import com.example.service.TransactionService;
import com.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    private TransactionService transactionService;

    @Inject
    private UserService userService;

    @GET
    @Path("/all")
    public Response list(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotAuthorizedException("User not authenticated");
        }
        List<Transaction> transactionList =
                transactionService.findAllTransactionOfUser(user.getId());
        if (transactionList.isEmpty()) {
            return Response.ok(transactionList).build();
        }
        return Response.ok(transactionList).build();
    }
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        Transaction t = transactionService.findTransaction(id);
        if (t == null) {
            throw new NotFoundException("Transaction %d not found".formatted(id));
        }
        return Response.ok(t).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, @Valid Transaction in) {
        Transaction t = transactionService.updateTransaction(in, id);
        if (t == null) {
            throw new NotFoundException("Transaction %d not found".formatted(id));
        }
        return Response.ok(t).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        Transaction removed = transactionService.deleteTransaction(id);
        if (removed == null) {
            throw new NotFoundException("Transaction %d not found".formatted(id));
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/new")
    public Response create(
            @Valid Transaction transaction,
            @Context SecurityContext securityContext
    ) {
        String email = securityContext.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotAuthorizedException("User not authenticated");
        }
        transaction.setUser(user);
        Transaction created = transactionService.createTransaction(transaction);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}
