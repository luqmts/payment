package com.luq.payment.controllers;

import com.luq.payment.dto.PaymentRequestDTO;
import com.luq.payment.dto.SessionDTO;
import com.luq.payment.services.PaymentService;
import com.stripe.model.checkout.SessionCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-checkout")
    public ResponseEntity<Map<String, String>> processPayment(@RequestBody PaymentRequestDTO request) {
        Map<String, String> response = paymentService.createCheckout(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-sessions")
    public ResponseEntity<SessionCollection> getAllSessions() {
        SessionCollection response = paymentService.getAllSessions();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-session-info/{session_id}")
    public ResponseEntity<SessionDTO> getSessionInfo(@PathVariable String session_id) {
        SessionDTO response = paymentService.getSessionInfo(session_id);

        return ResponseEntity.ok(response);
    }
}
