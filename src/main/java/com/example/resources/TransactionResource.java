package com.example.resources;

import com.example.models.Transaction;
import com.example.service.TransactionService;

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

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {
    @Inject
    private TransactionService transactionService;
    
    @GET
    @Path("/")
    public Response list(int idUser) {
        List<Transaction> transactionList = transactionService.findAllTransactionOfUser(idUser);
        if( transactionList == null) throw new NotFoundException("No transaction for user with the emai:  %d".formatted(idUser));
        return Response.ok(transactionList).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        Transaction u = transactionService.findTransaction(id);
        if (u == null) throw new NotFoundException("Transaction %d not found".formatted(id));
        return Response.ok(u).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, @Valid Transaction in) {
        Transaction u = transactionService.updateTransaction(in, id);
        if( u == null) throw new NotFoundException("Transaction %d not found".formatted(id));
        return Response.ok(u).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        Transaction removed = transactionService.deleteTransaction(id);
        if (removed == null) throw new NotFoundException("Transaction %d not found".formatted(id));
        return Response.noContent().build();
    }

    @POST
    @Path("/")
    public Response subscribe(Transaction transaction) {
        Transaction created = transactionService.createTransaction(transaction);
        return Response.ok(created).build();
    }
}