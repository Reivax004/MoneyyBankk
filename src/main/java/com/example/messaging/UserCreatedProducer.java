package com.example.messaging;

import com.example.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserCreatedProducer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void sendUserCreatedEvent(User user) {
        ConnectionFactory factory = Jms.connectionFactory();

        try (JMSContext ctx = factory.createContext()) {
            Queue queue = ctx.createQueue("UserCreatedQueue");

            Map<String, Object> doc = new LinkedHashMap<>();
            doc.put("userId", user.getId());                 // Integer
            doc.put("timestamp", Instant.now().toString());  // ISO-8601 UTC
            doc.put("nom", user.getLastname());
            doc.put("prenom", user.getFirstname());
            doc.put("email", user.getEmail());

            String json = MAPPER.writeValueAsString(doc);

            ctx.createProducer().send(queue, json);
            System.out.println("📤 Sent USER_CREATED document: " + json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send USER_CREATED document message" + e.getStackTrace());
        }
    }
}
