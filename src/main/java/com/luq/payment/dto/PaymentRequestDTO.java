package com.luq.payment.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO (
        BigDecimal unitPrice,
        Integer quantity,
        String product_name
){}
