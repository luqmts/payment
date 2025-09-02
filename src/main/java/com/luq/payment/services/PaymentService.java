package com.luq.payment.services;

import com.luq.payment.domain.*;
import com.luq.payment.dto.PaymentRequestDTO;
import com.luq.payment.dto.SessionDTO;
import com.luq.payment.exceptions.NegativeQuantityException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.checkout.SessionCollection;
import com.stripe.param.checkout.SessionListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private StripePaymentGateway paymentGateway;

    public Map<String, String> createCheckout(PaymentRequestDTO data) {
        if (data.quantity() <= 0)
            throw new NegativeQuantityException("Quantity must be greater or equal than 1");

        if (data.unitPrice().compareTo(BigDecimal.ZERO) == 0)
            throw new NegativeQuantityException("Unit price must be greater or equal than 1");
        return paymentGateway.createCheckoutSession(
            data,
            "http://localhost:8080/order/list"
        );
    }

    public SessionCollection getAllSessions() {
        try {
            SessionListParams params = SessionListParams.builder().setLimit(3L).build();
            return Session.list(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    public SessionDTO getSessionInfo(String session_id) {
        try {
            Session session = Session.retrieve(session_id);
            return new SessionDTO(session.getId(), session.getStatus(), session.getUrl());
        } catch (InvalidRequestException ex) {
            throw new RuntimeException("Invalid session's id");
        } catch (StripeException ex) {
            throw new RuntimeException(ex);
        }
    }
}

