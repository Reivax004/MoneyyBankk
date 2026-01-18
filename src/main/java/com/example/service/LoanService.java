package com.example.service;

import com.example.messaging.LoanRequestProducer;
import com.example.models.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class LoanService {

    @Inject
    private UserService userService;

    @Inject
    private StatisticsService statisticsService;

    private final LoanRequestProducer loanProducer = new LoanRequestProducer();

    public JsonNode requestLoan(String email, double amount) {
        if (email == null || email.isBlank()) {
            throw new NotFoundException("User email missing");
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        Map<String, Object> stats = statisticsService.getAccountStatistics(user);

        // JMS Request/Reply
        return loanProducer.sendLoanRequestAndWaitDecision(user, amount, stats);
    }
}
