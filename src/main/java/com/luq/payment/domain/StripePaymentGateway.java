package com.luq.payment.domain;

import com.luq.payment.dto.PaymentRequestDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripePaymentGateway{
    public Map<String, String> createCheckoutSession(
        PaymentRequestDTO request,
        String successUrl
    ) {
        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                .setQuantity(request.quantity().longValue())
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("brl")
                    .setUnitAmount(request.unitPrice().longValue())
                    .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(request.product_name())
                            .build()
                    ).build()
                ).build()
            ).build();
        try {
            Session session = Session.create(params);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("checkoutUrl", session.getUrl());
            return responseData;
        } catch (StripeException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
