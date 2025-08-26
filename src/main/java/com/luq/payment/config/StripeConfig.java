package com.luq.payment.config;
import com.stripe.Stripe;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {
    @PostConstruct
    public void init() {
        Stripe.apiKey = System.getenv("SK_SECRET");
    }
}
