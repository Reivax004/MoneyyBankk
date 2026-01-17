package com.example.config;

import com.example.service.StatisticsService;
import com.example.service.UserService;
import com.example.service.LoginService;
import com.example.service.TransactionService;
import com.example.service.ConnectionHistoriesService;
import com.example.service.LoanService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserService.class);
        bind(LoginService.class).to(LoginService.class);
        bind(TransactionService.class).to(TransactionService.class);
        bind(StatisticsService.class).to(StatisticsService.class);
        bind(ConnectionHistoriesService.class).to(ConnectionHistoriesService.class);
        bind(LoanService.class).to(LoanService.class);
    }
}
