package com.example.messaging;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.*;
import com.example.service.*;

@Stateless
public class UserSendInformationProducer {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory factory;

    @Resource(lookup = "jms/UserSendInfomrationQueue")
    private Queue queue;

    public void sendUserInformation(String jsonInformation) {
        try (JMSContext ctx = factory.createContext()) {
            String payload = "Informations statistiques:" + jsonInformation;
            ctx.createProducer().send(queue, payload);
            System.out.println("📤 Message envoyé au conseiller ! Voici le contenu: " + payload);
        }
    }

}
