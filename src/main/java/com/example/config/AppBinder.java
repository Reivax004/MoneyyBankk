package com.example.config;

import com.example.service.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserService.class);
        bind(LoginService.class).to(LoginService.class);
        bind(TransactionService.class).to(TransactionService.class);
        bind(StatisticsService.class).to(StatisticsService.class);
        bind(ConnectionHistoriesService.class).to(ConnectionHistoriesService.class);
    }
}
