package com.luq.payment.services;

import com.luq.payment.domain.*;
import com.luq.payment.dto.PaymentRequestDTO;
import com.luq.payment.dto.SessionDTO;
import com.luq.payment.exceptions.NegativeQuantityException;
import com.luq.payment.exceptions.NotFoundException;
import com.luq.payment.exceptions.StripeErrorException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private StripePaymentGateway paymentGateway;

    public SessionDTO createCheckout(PaymentRequestDTO data) {
        if (data.quantity() <= 0)
            throw new NegativeQuantityException("Quantity must be greater or equal than 1");

        if (data.unitPrice().compareTo(BigDecimal.ZERO) == 0)
            throw new NegativeQuantityException("Unit price must be greater or equal than 1");

        return paymentGateway.createCheckoutSession(data);
    }

    public List<SessionDTO> getLastSessions() {
        try {
            SessionListParams params = SessionListParams.builder().setLimit(5L).build();

            return Session
                .list(params)
                .getData()
                .stream()
                .map(data -> new SessionDTO(data.getId(), data.getStatus(), data.getUrl()))
                .toList();
        } catch (StripeException ex) {
            throw new StripeErrorException(ex.getMessage());
        }
    }

    public SessionDTO getSessionInfo(String session_id) {
        try {
            Session session = Session.retrieve(session_id);

            return new SessionDTO(session.getId(), session.getStatus(), session.getUrl());
        } catch (InvalidRequestException ex) {
            throw new NotFoundException("Invalid session's id");
        } catch (StripeException ex) {
            throw new StripeErrorException(ex.getMessage());
        }
    }
}

