package com.luq.payment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luq.payment.dto.PaymentStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPaymentStatus(PaymentStatusDTO request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(request);
        kafkaTemplate.send("payment-status-topic", message);
    }
}
