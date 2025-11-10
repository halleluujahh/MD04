package org.com.session06.service;

import org.com.session06.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<Order> findAll(Integer page, Integer size, String status, Long userId);
    Page<Order> findAllOrders(Integer page, Integer size, String status);
    Page<Order> updateStatus(String status, Long orderId);
}
