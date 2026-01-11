package com.example.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.example.service.UserService;
import com.example.service.LoginService;
import com.example.service.TransactionService;

public class AppBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserService.class);
        bind(LoginService.class).to(LoginService.class);
        bind(TransactionService.class).to(TransactionService.class);
    }
}
