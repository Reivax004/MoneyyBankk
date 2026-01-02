package com.example.resources;

import com.example.models.Transaction;
import com.example.models.User;
import com.example.service.TransactionService;
import com.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/statistics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatisticResource {

    @Inject
    private TransactionService transactionService;

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
        List<Transaction> transactions = transactionService.findAllTransactionOfUser(user.getId());
        if (transactions.isEmpty()) {
            return Response.ok(Map.of(
                    "message", "No transactions for this user"
            )).build();
        }
        List<Transaction> credits = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("CREDIT") || t.getType().equalsIgnoreCase("INCOME"))
                .collect(Collectors.toList());
        List<Transaction> debits = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("DEBIT") || t.getType().equalsIgnoreCase("EXPENSE"))
                .collect(Collectors.toList());
        double totalCredits = credits.stream().mapToDouble(Transaction::getPrice).sum();
        double totalDebits = debits.stream().mapToDouble(Transaction::getPrice).sum();
        double balance = totalCredits - totalDebits;
        Map<String, Object> response = Map.of(
                "userId", user.getId(),
                "totalTransactions", transactions.size(),
                "totalCredits", totalCredits,
                "totalDebits", totalDebits,
                "balance", balance,
                "maxTransaction", transactions.stream().mapToDouble(Transaction::getPrice).max().orElse(0),
                "minTransaction", transactions.stream().mapToDouble(Transaction::getPrice).min().orElse(0)
        );
        return Response.ok(response).build();
    }
}
