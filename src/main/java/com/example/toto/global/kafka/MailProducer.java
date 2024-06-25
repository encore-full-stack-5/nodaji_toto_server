package com.example.toto.global.kafka;

import com.example.toto.global.dto.KafkaStatus;
import com.example.toto.global.dto.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailProducer {
    private final KafkaTemplate<String, KafkaStatus<EmailRequest>> kafkaTemplate;
    private final String TOPIC = "email-topic";

    @Bean
    private NewTopic newTopic() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    public void send(EmailRequest email) {
        KafkaStatus<EmailRequest> kafkaStatus = new KafkaStatus<>(email, "result");
        kafkaTemplate.send(TOPIC, kafkaStatus);
    }
}
