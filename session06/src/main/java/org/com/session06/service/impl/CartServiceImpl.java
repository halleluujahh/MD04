package org.com.session06.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.com.session06.dto.response.CartProductResponseDTO;
import org.com.session06.dto.response.CartResponseDTO;
import org.com.session06.entity.Product;
import org.com.session06.entity.ShoppingCart;
import org.com.session06.entity.ShoppingCartProduct;
import org.com.session06.entity.ShoppingCartProductId;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.repository.CartRepository;
import org.com.session06.repository.ProductRepository;
import org.com.session06.repository.ShoppingCartProductRepository;
import org.com.session06.repository.UserRepository;
import org.com.session06.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ShoppingCartProductRepository shoppingCartProductRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponseDTO findAll(Long userId) throws NotFoundException {
        ShoppingCart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = ShoppingCart.builder()
                    .user(userRepository.findUserById(userId))
                    .build();
            ShoppingCart cartCreated = cartRepository.save(cart);
            return CartResponseDTO.builder()
                    .cartId(cartCreated.getId())
                    .userId(cartCreated.getUser().getId())
                    .build();
        }
        if (!cart.getShoppingCartProducts().isEmpty()) {
            List<CartProductResponseDTO> cartProductResponseDTOS = cart.getShoppingCartProducts().stream()
                    .map(scProduct -> new CartProductResponseDTO(
                            scProduct.getProduct().getId(),
                            scProduct.getProduct().getSku(),
                            scProduct.getProduct().getProductName(),
                            scProduct.getProduct().getDescription(),
                            scProduct.getProduct().getUnitPrice(),
                            scProduct.getProduct().getImage(),
                            scProduct.getProduct().getCategory(),
                            scProduct.getQuantity()
                    ))
                    .collect(Collectors.toList());
            return CartResponseDTO.builder()
                    .cartId(cart.getId())
                    .cartItems(cartProductResponseDTOS)
                    .userId(cart.getUser().getId())
                    .build();
        }
        return CartResponseDTO.builder()
                .cartId(cart.getId())
                .userId(cart.getUser().getId())
                .build();
    }

    @Override
    public CartResponseDTO addToCart(Long userId, Long productId) throws BadRequestException {
        Product product = productRepository.findProductById(productId);
        if(product.getStockQuantity() <= 0){
            throw new BadRequestException("Sản phẩm đã hết hàng.");
        }
        ShoppingCart cart = cartRepository.findByUserId(userId);
        ShoppingCartProductId shoppingCartProductId = ShoppingCartProductId.builder()
                .shoppingCartId(cart.getId())
                .productId(productId)
                .build();
        ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository.findByProductAndShoppingCart(product, cart);
        if (shoppingCartProduct == null) {
            shoppingCartProduct = ShoppingCartProduct.builder()
                    .id(shoppingCartProductId)
                    .shoppingCart(cart)
                    .product(product)
                    .quantity(1)
                    .build();
            shoppingCartProductRepository.save(shoppingCartProduct);
            product.setStockQuantity(product.getStockQuantity()-1);
            productRepository.save(product);
            ShoppingCart cartAfterAddProduct = cartRepository.findByUserId(userId);
            List<CartProductResponseDTO> cartProductResponseDTOS = cartAfterAddProduct.getShoppingCartProducts().stream()
                    .map(scProduct -> new CartProductResponseDTO(
                            scProduct.getProduct().getId(),
                            scProduct.getProduct().getSku(),
                            scProduct.getProduct().getProductName(),
                            scProduct.getProduct().getDescription(),
                            scProduct.getProduct().getUnitPrice(),
                            scProduct.getProduct().getImage(),
                            scProduct.getProduct().getCategory(),
                            scProduct.getQuantity()
                    ))
                    .collect(Collectors.toList());
            return CartResponseDTO.builder()
                    .cartId(cartAfterAddProduct.getId())
                    .cartItems(cartProductResponseDTOS)
                    .userId(cartAfterAddProduct.getUser().getId())
                    .build();
        }
        ShoppingCartProduct shoppingCartProductOld = shoppingCartProductRepository.findByProductAndShoppingCart(product, cart);
        shoppingCartProductOld.setQuantity(shoppingCartProductOld.getQuantity() + 1);
        shoppingCartProductRepository.save(shoppingCartProductOld);
        product.setStockQuantity(product.getStockQuantity()-1);
        productRepository.save(product);
        ShoppingCart cartAfterAddProduct = cartRepository.findByUserId(userId);
        List<CartProductResponseDTO> cartProductResponseDTOS = cartAfterAddProduct.getShoppingCartProducts().stream()
                .map(scProduct -> new CartProductResponseDTO(
                        scProduct.getProduct().getId(),
                        scProduct.getProduct().getSku(),
                        scProduct.getProduct().getProductName(),
                        scProduct.getProduct().getDescription(),
                        scProduct.getProduct().getUnitPrice(),
                        scProduct.getProduct().getImage(),
                        scProduct.getProduct().getCategory(),
                        scProduct.getQuantity()
                ))
                .collect(Collectors.toList());
        return CartResponseDTO.builder()
                .cartId(cartAfterAddProduct.getId())
                .cartItems(cartProductResponseDTOS)
                .userId(cartAfterAddProduct.getUser().getId())
                .build();
    }

    @Override
    public CartResponseDTO removeFromCart(Long userId, Long productId) {
        Product product = productRepository.findProductById(productId);
        ShoppingCart cart = cartRepository.findByUserId(userId);
        ShoppingCartProduct shoppingCartProductToDelete = shoppingCartProductRepository.findByProductAndShoppingCart(product, cart);
        shoppingCartProductRepository.deleteByProductAndShoppingCart(product, cart);
        product.setStockQuantity(product.getStockQuantity() + shoppingCartProductToDelete.getQuantity());
        productRepository.save(product);
        ShoppingCart cartAfterRemoveProduct = cartRepository.findByUserId(userId);
        List<CartProductResponseDTO> cartProductResponseDTOS = cartAfterRemoveProduct.getShoppingCartProducts().stream()
                .map(scProduct -> new CartProductResponseDTO(
                        scProduct.getProduct().getId(),
                        scProduct.getProduct().getSku(),
                        scProduct.getProduct().getProductName(),
                        scProduct.getProduct().getDescription(),
                        scProduct.getProduct().getUnitPrice(),
                        scProduct.getProduct().getImage(),
                        scProduct.getProduct().getCategory(),
                        scProduct.getQuantity()
                ))
                .collect(Collectors.toList());
        return CartResponseDTO.builder()
                .cartId(cartAfterRemoveProduct.getId())
                .cartItems(cartProductResponseDTOS)
                .userId(cartAfterRemoveProduct.getUser().getId())
                .build();
    }

    @Override
    public CartResponseDTO reduceProductFromCart(Long userId, Long productId) {
        Product product = productRepository.findProductById(productId);
        ShoppingCart cart = cartRepository.findByUserId(userId);
        ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository.findByProductAndShoppingCart(product, cart);
        shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity()-1);
        if(shoppingCartProduct.getQuantity()<=0){
            shoppingCartProductRepository.deleteByProductAndShoppingCart(product, cart);
        }else{
            shoppingCartProductRepository.save(shoppingCartProduct);
        }
        product.setStockQuantity(product.getStockQuantity()+1);
        productRepository.save(product);
        ShoppingCart cartAfterReduceProduct = cartRepository.findByUserId(userId);
        List<CartProductResponseDTO> cartProductResponseDTOS = cartAfterReduceProduct.getShoppingCartProducts().stream()
                .map(scProduct -> new CartProductResponseDTO(
                        scProduct.getProduct().getId(),
                        scProduct.getProduct().getSku(),
                        scProduct.getProduct().getProductName(),
                        scProduct.getProduct().getDescription(),
                        scProduct.getProduct().getUnitPrice(),
                        scProduct.getProduct().getImage(),
                        scProduct.getProduct().getCategory(),
                        scProduct.getQuantity()
                ))
                .collect(Collectors.toList());
        return CartResponseDTO.builder()
                .cartId(cartAfterReduceProduct.getId())
                .cartItems(cartProductResponseDTOS)
                .userId(cartAfterReduceProduct.getUser().getId())
                .build();
    }
}
