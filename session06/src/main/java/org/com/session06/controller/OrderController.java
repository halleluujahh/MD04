package org.com.session06.controller;

import lombok.RequiredArgsConstructor;
import org.com.session06.entity.enumerate.Status;
import org.com.session06.exception.BadRequestException;
import org.com.session06.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("findAll")
    public ResponseEntity<?> getAllOrdersByUserId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
                                            @RequestParam(name = "status", defaultValue = "WAITING") String status,
                                          @RequestParam(name = "userId", required = true) Long userId
    ) throws BadRequestException {
        return new ResponseEntity<>(orderService.findAll(page, limit, status, userId), HttpStatus.OK);
    }
}
