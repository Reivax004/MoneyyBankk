package com.example.messaging;
import com.example.models.User;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.*;

@Stateless
public class UserSendInformationProducer {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory factory;

    @Resource(lookup = "jms/UserCreatedQueue")
    private Queue queue;

    public void sendUserCreatedEvent(User user) {
        try (JMSContext ctx = factory.createContext()) {
            String payload = "Informations statistiques:" + user.getJsonInformation();
            ctx.createProducer().send(queue, payload);
            System.out.println("📤 Sent JMS message: " + payload);
        }
    }

}
