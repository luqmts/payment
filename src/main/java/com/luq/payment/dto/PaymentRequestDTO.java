package com.luq.payment.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO (
    String orderId,
    BigDecimal unitPrice,
    Integer quantity,
    String product_name
){}
