package org.com.session06.controller;

import lombok.RequiredArgsConstructor;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart/")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("findAll/{userId}")
    public ResponseEntity<?> findAll(
            @PathVariable(name = "userId", required = true) Long userId) throws NotFoundException {
        return new ResponseEntity<>(cartService.findAll(userId), HttpStatus.OK);
    }

    @PutMapping("addToCart")
    public ResponseEntity<?> addToCart(
            @RequestParam(name = "userId", required = true) Long userId,
            @RequestParam(name = "productId", required = true) Long productId
    ) throws BadRequestException {
        return new ResponseEntity<>(cartService.addToCart(userId, productId), HttpStatus.CREATED);
    }

    @PutMapping("reduceProductFromCart")
    public ResponseEntity<?> reduceProductFromCart(
            @RequestParam(name = "userId", required = true) Long userId,
            @RequestParam(name = "productId", required = true) Long productId
    ){
        return new ResponseEntity<>(cartService.reduceProductFromCart(userId, productId), HttpStatus.CREATED);
    }

    @DeleteMapping("remove")
    public ResponseEntity<?> removeProductFromCart(
            @RequestParam(name = "userId", required = true) Long userId,
            @RequestParam(name = "productId", required = true) Long productId
    ) {
        return new ResponseEntity<>(cartService.removeFromCart(userId, productId), HttpStatus.OK);
    }
}
