package com.example.service;

import com.example.messaging.LoanRequestProducer;
import com.example.models.User;
import jakarta.inject.Inject;

import java.util.Map;

public class LoanService {

    @Inject
    private UserService userService;

    @Inject
    private StatisticsService statisticsService;

    private final LoanRequestProducer loanProducer = new LoanRequestProducer();

    public Map<String, Object> requestLoan(int userId, double amount) {
        User user = userService.findUser(userId);
        if (user == null) {
            throw new jakarta.ws.rs.NotFoundException("User not found");
        }

        // stats calculées côté producer (comme tu l’as dit)
        Map<String, Object> stats = statisticsService.getAccountStatistics(user);

        // JMS Request/Reply
        return loanProducer.sendLoanRequestAndWaitDecision(user, amount, stats);
    }
}
