package org.com.session06.service;

import org.com.session06.dto.response.CartResponseDTO;
import org.com.session06.entity.ShoppingCart;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;

public interface CartService {
    CartResponseDTO findAll(Long userId) throws NotFoundException;
    CartResponseDTO addToCart(Long userId, Long productId) throws BadRequestException;
    CartResponseDTO removeFromCart(Long userId, Long productId);
    CartResponseDTO reduceProductFromCart(Long userId, Long productId);
}
