package com.example.messaging;

import com.example.models.User;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

public class UserCreatedProducer {

    public void sendUserCreatedEvent(User user) {
        ConnectionFactory factory = Jms.connectionFactory();

        try (JMSContext ctx = factory.createContext()) {
            // Nom EXACT de la queue côté Artemis (sans "jms/")
            Queue queue = ctx.createQueue("UserCreatedQueue");

            String payload = "UserCreated:" + user.getId();
            ctx.createProducer().send(queue, payload);

            System.out.println("📤 Sent JMS message: " + payload);
        }
    }
}
