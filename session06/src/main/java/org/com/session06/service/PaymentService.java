package org.com.session06.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.com.session06.config.VNPAYConfig;
import org.com.session06.controller.PaymentDTO;
import org.com.session06.dto.response.CartProductResponseDTO;
import org.com.session06.dto.response.VietQrResponseDTO;
import org.com.session06.entity.*;
import org.com.session06.entity.enumerate.Status;
import org.com.session06.helper.VNPayUtil;
import org.com.session06.repository.*;
import org.com.session06.security.UserDetailService;
import org.com.session06.security.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = (long) (Double.parseDouble(request.getParameter("amount")) * 100);
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    public Payment savePaymentOrder(Long userId, String totalAmount, String methodPayment, String transactionNo) {
        if (paymentRepository.findByTransactionId(transactionNo) == null) {
            User user = userRepository.findUserById(userId);
            Payment payment = Payment.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .totalAmount(Double.parseDouble(totalAmount))
                    .methodPayment(methodPayment)
                    .transactionId(transactionNo)
                    .build();
            ;
            //Lưu payment
            if (methodPayment.equalsIgnoreCase("vietQR") || methodPayment.equalsIgnoreCase("COD")) {
                payment.setTotalAmount(Double.parseDouble(totalAmount));
            } else {
                payment.setTotalAmount(Double.parseDouble(totalAmount) / 100);
            }
            Payment paymentAdded = paymentRepository.save(payment);
            //Lưu order
            Order order = Order.builder()
                    .payment(paymentAdded)
                    .createdAt(LocalDate.now())
                    .receiveAddress(user.getAddress().getSpecify() + "," + user.getAddress().getStreet()
                            + "," + user.getAddress().getDistrict() + "," + user.getAddress().getCity())
                    .receiveName(user.getUsername())
                    .receivePhone(user.getPhone())
                    .serialNumber(UUID.randomUUID().toString())
                    .totalPrice(BigDecimal.valueOf(paymentAdded.getTotalAmount()))
                    .user(user)
                    .payment(payment)
                    .build();
            if (methodPayment.equalsIgnoreCase("COD")) {
                order.setStatus(Status.WAITING);
            } else {
                order.setStatus(Status.CONFIRM);
            }
            orderRepository.save(order);
            ShoppingCart cartAfterDelete = cartRepository.findByUserId(userId);
            List<OrderDetails> orderDetails = cartAfterDelete.getShoppingCartProducts().stream()
                    .map(scProduct -> new OrderDetails(
                            order.getId(),
                            scProduct.getProduct().getId(),
                            scProduct.getProduct().getProductName(),
                            scProduct.getProduct().getUnitPrice(),
                            scProduct.getQuantity()
                    ))
                    .collect(Collectors.toList());
            for (OrderDetails o : orderDetails) {
                orderDetailRepository.save(o);
            }
            //xóa cart
            cartRepository.deleteByUser(userRepository.findUserById(userId));
            return paymentAdded;
        }
        return null;
    }

    public VietQrResponseDTO createQrCode(String amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
        ShoppingCart shoppingCart = cartRepository.findByUserId(userDetails.getUser().getId());
        String bankId = "MB";
        String accNo = "0981489665";
        String content = "Thanh toán đơn hàng " + shoppingCart.getId() + " uid " + userDetails.getUser().getId();
        String qrCode = "https://img.vietqr.io/image/" + bankId + "-" + accNo + "-compact2.png?amount=" + amount + "&addInfo=" + content;
        String contentDisplay = "THANH TOAN DON HANG " + shoppingCart.getId() + " UID " + userDetails.getUser().getId();
        return VietQrResponseDTO.builder()
                .vietQr(qrCode)
                .content(contentDisplay)
                .amount(amount)
                .build();
    }
}