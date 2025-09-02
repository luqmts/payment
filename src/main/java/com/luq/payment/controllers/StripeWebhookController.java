package com.luq.payment.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luq.payment.domain.StripePaymentGateway;
import com.luq.payment.dto.PaymentStatusDTO;
import com.luq.payment.services.PaymentProducer;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

    @Autowired
    private PaymentProducer paymentProducer;
    @Autowired
    private StripePaymentGateway paymentGateway;

    private static final String ENDPOINT_SECRET = System.getenv("WK_SECRET");

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeEvent(
        @RequestBody String payload,
        @RequestHeader("Stripe-Signature") String sigHeader) throws JsonProcessingException {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);
        } catch (SignatureVerificationException e) {
            return ResponseEntity
                    .status(400)
                    .body("Webhook error: " + e.getMessage());
        }

        Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
        if (session != null) {
            String orderId = session.getClientReferenceId();
            if ("checkout.session.completed".equals(event.getType())) {
                PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO(Integer.parseInt(orderId), "PAID");
                System.out.println("success: " + paymentStatusDTO);
                paymentProducer.sendPaymentStatus(paymentStatusDTO);
            }

            if ("checkout.session.expired".equals(event.getType())) {
                PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO(Integer.parseInt(orderId), "EXPIRED_PAYMENT");
                System.out.println("expire: " + paymentStatusDTO);
                paymentProducer.sendPaymentStatus(paymentStatusDTO);
            }
        }

        return ResponseEntity.ok("Received");
    }
}