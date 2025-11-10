package org.com.session06.repository;

import org.com.session06.entity.Order;
import org.com.session06.entity.User;
import org.com.session06.entity.enumerate.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByStatusAndUser(Pageable pageable, Status status, User user);
    Page<Order> findOrdersByStatus(Pageable pageable, Status status);
    Order findOrderById(Long orderId);
}
