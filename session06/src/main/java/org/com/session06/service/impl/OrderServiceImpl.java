package org.com.session06.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.session06.entity.Category;
import org.com.session06.entity.Order;
import org.com.session06.entity.Product;
import org.com.session06.entity.enumerate.Status;
import org.com.session06.exception.BadRequestException;
import org.com.session06.repository.OrderRepository;
import org.com.session06.repository.UserRepository;
import org.com.session06.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Order>  findAll(Integer page, Integer size, String status, Long userId) {
        Pageable pageable;
        Sort sortOrder;
        sortOrder = Sort.by(Sort.Order.desc("createdAt"));
        pageable = PageRequest.of(page, size, sortOrder);
        if(status.equalsIgnoreCase("WAITING")){
            return orderRepository.findOrdersByStatusAndUser(pageable, Status.WAITING, userRepository.findUserById(userId));
        }else if(status.equalsIgnoreCase("CANCEL")){
            return orderRepository.findOrdersByStatusAndUser(pageable, Status.CANCEL, userRepository.findUserById(userId));
        }else if(status.equalsIgnoreCase("CONFIRM")){
            return orderRepository.findOrdersByStatusAndUser(pageable, Status.CONFIRM, userRepository.findUserById(userId));
        }else if(status.equalsIgnoreCase("DENIED")){
            return orderRepository.findOrdersByStatusAndUser(pageable, Status.DENIED, userRepository.findUserById(userId));
        }else if(status.equalsIgnoreCase("DELIVERY")){
            return orderRepository.findOrdersByStatusAndUser(pageable, Status.DELIVERY, userRepository.findUserById(userId));
        }
        return null;
    }

    @Override
    public Page<Order> findAllOrders(Integer page, Integer size, String status) {
        Pageable pageable;
        Sort sortOrder;
        sortOrder = Sort.by(Sort.Order.desc("createdAt"));
        pageable = PageRequest.of(page, size, sortOrder);
        if(status.equalsIgnoreCase("WAITING")){
            return orderRepository.findOrdersByStatus(pageable, Status.WAITING);
        }else if(status.equalsIgnoreCase("CANCEL")){
            return orderRepository.findOrdersByStatus(pageable, Status.CANCEL);
        }else if(status.equalsIgnoreCase("CONFIRM")){
            return orderRepository.findOrdersByStatus(pageable, Status.CONFIRM);
        }else if(status.equalsIgnoreCase("DENIED")){
            return orderRepository.findOrdersByStatus(pageable, Status.DENIED);
        }else if(status.equalsIgnoreCase("DELIVERY")){
            return orderRepository.findOrdersByStatus(pageable, Status.DELIVERY);
        }
        return null;
    }

    @Override
    public Page<Order> updateStatus(String status, Long orderId) {
        Order orderToUpdate = orderRepository.findOrderById(orderId);
        if(status.equalsIgnoreCase("WAITING")){
            orderToUpdate.setStatus(Status.WAITING);
        }else if(status.equalsIgnoreCase("CANCEL")){
            orderToUpdate.setStatus(Status.CANCEL);
        }else if(status.equalsIgnoreCase("CONFIRM")){
            orderToUpdate.setStatus(Status.CONFIRM);
        }else if(status.equalsIgnoreCase("DENIED")){
            orderToUpdate.setStatus(Status.DENIED);
        }else if(status.equalsIgnoreCase("DELIVERY")){
            orderToUpdate.setStatus(Status.DELIVERY);
        }
        orderRepository.save(orderToUpdate);
        Pageable pageable;
        Sort sortOrder;
        sortOrder = Sort.by(Sort.Order.desc("createdAt"));
        pageable = PageRequest.of(0, 10, sortOrder);
        return orderRepository.findOrdersByStatus(pageable, Status.WAITING);
    }
}
