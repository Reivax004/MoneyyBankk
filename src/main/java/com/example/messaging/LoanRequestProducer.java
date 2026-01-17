package com.example.messaging;

import com.example.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class LoanRequestProducer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Map<String, Object> sendLoanRequestAndWaitDecision(User user, double amount, Map<String, Object> stats) {
        ConnectionFactory factory = Jms.connectionFactory();

        try (JMSContext ctx = factory.createContext()) {
            Queue requestQueue = ctx.createQueue("LoanRequestQueue");

            TemporaryQueue replyQueue = ctx.createTemporaryQueue();
            JMSConsumer replyConsumer = ctx.createConsumer(replyQueue);

            String requestId = UUID.randomUUID().toString();

            Map<String, Object> doc = new LinkedHashMap<>();
            doc.put("type", "LOAN_REQUEST");
            doc.put("requestId", requestId);
            doc.put("timestamp", Instant.now().toString());
            doc.put("userId", user.getId());
            doc.put("nom", user.getLastname());
            doc.put("prenom", user.getFirstname());
            doc.put("email", user.getEmail());
            doc.put("amount", amount);
            doc.put("stats", stats);

            String json = MAPPER.writeValueAsString(doc);

            TextMessage msg = ctx.createTextMessage(json);
            msg.setJMSReplyTo(replyQueue);
            msg.setJMSCorrelationID(requestId);

            ctx.createProducer().send(requestQueue, msg);

            String replyJson = replyConsumer.receiveBody(String.class, 5000);
            if (replyJson == null) {
                return Map.of(
                        "requestId", requestId,
                        "status", "TIMEOUT",
                        "message", "No decision received within timeout"
                );
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> reply = MAPPER.readValue(replyJson, Map.class);
            return reply;

        } catch (Exception e) {
            throw new RuntimeException("Loan JMS request/reply failed", e);
        }
    }
}
