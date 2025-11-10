package org.com.session06.controller.admin;

import lombok.RequiredArgsConstructor;
import org.com.session06.exception.BadRequestException;
import org.com.session06.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/order")
@RequiredArgsConstructor
public class OrderAdminController {
    private final OrderService orderService;

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "limit", defaultValue = "5") Integer limit,
                                                  @RequestParam(name = "status", defaultValue = "WAITING") String status
    ) throws BadRequestException {
        return new ResponseEntity<>(orderService.findAllOrders(page, limit, status), HttpStatus.OK);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus
            (
                    @RequestParam(name = "status", required = true) String status,
                    @RequestParam(name = "orderId", required = true) Long orderId
            )
    {
        return new ResponseEntity<>(orderService.updateStatus(status, orderId), HttpStatus.OK);
    }
}