package com.luq.payment.domain;

import com.luq.payment.dto.PaymentRequestDTO;
import com.luq.payment.dto.SessionDTO;
import com.luq.payment.exceptions.StripeErrorException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StripePaymentGateway{
    public SessionDTO createCheckoutSession(PaymentRequestDTO request) {
        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(System.getenv("SUCCESS_URL"))
            .setClientReferenceId(request.orderId())
            .setExpiresAt((System.currentTimeMillis() / 1000L) + 1800)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                .setQuantity(request.quantity().longValue())
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("BRL")
                    .setUnitAmount(request.unitPrice().multiply(BigDecimal.valueOf(100)).longValue())
                    .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(request.productName())
                            .build()
                    ).build()
                ).build()
            ).build();

        try {
            Session session = Session.create(params);

            return new SessionDTO(session.getId(), session.getStatus(), session.getUrl());
        } catch (StripeException ex) {
            throw new StripeErrorException(ex.getMessage());
        }
    }
}
