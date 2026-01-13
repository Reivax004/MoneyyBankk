package com.example.service;

import com.example.models.Transaction;
import com.example.models.User;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class StatisticsService {

    @Inject
    private TransactionService transactionService;

    public Map<String, Object> getAccountStatistics(User user) {
        List<Transaction> transactions =
                transactionService.findAllTransactionOfUser(user.getId());

        if (transactions.isEmpty()) {
            return Map.of(
                    "message", "No transactions for this user"
            );
        }

        List<Transaction> credits = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("CREDIT")
                        || t.getType().equalsIgnoreCase("INCOME"))
                .collect(Collectors.toList());

        List<Transaction> debits = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("DEBIT")
                        || t.getType().equalsIgnoreCase("EXPENSE"))
                .collect(Collectors.toList());

        double totalCredits = credits.stream()
                .mapToDouble(Transaction::getPrice)
                .sum();

        double totalDebits = debits.stream()
                .mapToDouble(Transaction::getPrice)
                .sum();

        double balance = totalCredits - totalDebits;
        return Map.of(
                "userId", user.getId(),
                "totalTransactions", transactions.size(),
                "totalCredits", totalCredits,
                "totalDebits", totalDebits,
                "balance", balance,
                "maxTransaction", transactions.stream()
                        .mapToDouble(Transaction::getPrice)
                        .max().orElse(0),
                "minTransaction", transactions.stream()
                        .mapToDouble(Transaction::getPrice)
                        .min().orElse(0)
        );
    }
}
