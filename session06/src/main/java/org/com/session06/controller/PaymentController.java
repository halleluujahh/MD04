package org.com.session06.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.com.session06.dto.ResponseObject;
import org.com.session06.exception.BadRequestException;
import org.com.session06.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @PutMapping("/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request) throws BadRequestException {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseEntity<>(paymentService.savePaymentOrder(
                    Long.parseLong(request.getParameter("userId")),
                    request.getParameter("vnp_Amount"),
                    "VNPay",
                    request.getParameter("vnp_TransactionNo")),
                    HttpStatus.OK);
        } else {
            throw new BadRequestException("Thanh toán thất bại.");
        }
    }

    @GetMapping("/VietQR")
    public ResponseEntity<?> payVietQR(HttpServletRequest request) {
        return new ResponseEntity<>(paymentService.createQrCode(request.getParameter("amount")), HttpStatus.OK);
    }

    @PutMapping("/vietqr-callback")
    public ResponseEntity<?> vietQrCallbackHandler(HttpServletRequest request) throws BadRequestException {
        String status = request.getParameter("vnp_ResponseCode");
        return new ResponseEntity<>(paymentService.savePaymentOrder(
                Long.parseLong(request.getParameter("userId")),
                request.getParameter("amount"),
                "vietQR",
                request.getParameter("transactionNo")),
                HttpStatus.OK);
    }
}
