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

        List<Transaction> credits = getCredits(transactions);
        List<Transaction> debits = getDebits(transactions);

        double totalCredits = getTotalAmount(credits);
        double totalDebits = getTotalAmount(debits);

        double balance = calculateBalance(totalCredits, totalDebits);

        return Map.of(
                "userId", user.getId(),
                "totalTransactions", transactions.size(),
                "totalCredits", totalCredits,
                "totalDebits", totalDebits,
                "balance", balance,
                "maxTransaction", getMaxTransaction(transactions),
                "minTransaction", getMinTransaction(transactions)
        );
    }

    private List<Transaction> getCredits(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("CREDIT")
                        || t.getType().equalsIgnoreCase("INCOME"))
                .collect(Collectors.toList());
    }

    private List<Transaction> getDebits(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("DEBIT")
                        || t.getType().equalsIgnoreCase("EXPENSE"))
                .collect(Collectors.toList());
    }

    private double getTotalAmount(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getPrice)
                .sum();
    }

    private double calculateBalance(double totalCredits, double totalDebits) {
        return totalCredits - totalDebits;
    }

    private double getMaxTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getPrice)
                .max()
                .orElse(0);
    }

    private double getMinTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getPrice)
                .min()
                .orElse(0);
    }
}
