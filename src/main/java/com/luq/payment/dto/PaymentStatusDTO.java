package com.luq.payment.dto;

public record PaymentStatusDTO(
    Integer orderId,
    String status
) {}
