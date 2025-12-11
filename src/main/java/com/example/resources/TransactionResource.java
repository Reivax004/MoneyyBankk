package com.example.resources;

import com.example.models.Transaction;
import com.example.models.User;
import com.example.service.TransactionService;
import com.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Context;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {
    @Context
    SecurityContext securityContext;
    UserService userService;

    @Inject
    private TransactionService transactionService;

    private static final Map<Integer, Transaction> DB = new ConcurrentHashMap<>();
    private static final AtomicInteger SEQ = new AtomicInteger(0);
    static {
        DB.put(SEQ.incrementAndGet(), new Transaction(50.00, LocalDate.of(2025, 12, 06),"euro","depense"));
        DB.put(SEQ.incrementAndGet(), new Transaction());
    }
    @GET
    @Path("/")
    public Response list() {
        String email = securityContext.getUserPrincipal().getName();
        List<Transaction> transactionList = transactionService.findAllTransactionOfUser(email);
        if( transactionList == null) throw new NotFoundException("Aucune transaction trouvée pour l'utilisateur %d".formatted((email)));
        return Response.ok(transactionList).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        String email = securityContext.getUserPrincipal().getName();
        Transaction u = transactionService.findTransaction(id);
        if (u == null) throw new NotFoundException("Transaction %d introuvable".formatted(id));
        if (u.getUser().getEmail() != email) throw new NotAllowedException("Not authorized access");
        return Response.ok(u).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, @Valid Transaction in) {
        String email = securityContext.getUserPrincipal().getName();
        Transaction u = transactionService.findTransaction(id);
        if( u == null) throw new NotFoundException("Transaction %d introuvable".formatted(id));
        if (u.getUser().getEmail() != email) throw new NotAllowedException("Not authorized access");
        u = transactionService.updateTransaction(in, id);
        return Response.ok(u).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        String email = securityContext.getUserPrincipal().getName();
        Transaction removed = transactionService.findTransaction(id);
        if (removed == null) throw new NotFoundException("Transaction %d introuvable".formatted(id));
        if (removed.getUser().getEmail() != email) throw new NotAllowedException("Not authorized access");
        removed = transactionService.deleteTransaction(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/new")
    public Response create(Transaction transaction, int id) {
        String email = securityContext.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        Transaction created = transactionService.createTransaction(transaction, user);
        return Response.ok(created).build();
    }
}