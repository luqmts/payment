package com.luq.payment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luq.payment.dto.PaymentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {
    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void process(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        PaymentRequestDTO data = mapper.readValue(message, PaymentRequestDTO.class);
        paymentService.createCheckout(data);
    }
}
