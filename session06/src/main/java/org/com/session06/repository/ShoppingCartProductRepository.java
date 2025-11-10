package org.com.session06.repository;

import jakarta.transaction.Transactional;
import org.com.session06.entity.Product;
import org.com.session06.entity.ShoppingCart;
import org.com.session06.entity.ShoppingCartProduct;
import org.com.session06.entity.ShoppingCartProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, ShoppingCartProductId> {
    ShoppingCartProduct findByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
    @Modifying
    @Transactional
    void deleteByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
}
