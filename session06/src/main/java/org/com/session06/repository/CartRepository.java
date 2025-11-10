package org.com.session06.repository;

import jakarta.transaction.Transactional;
import org.com.session06.entity.Product;
import org.com.session06.entity.ShoppingCart;
import org.com.session06.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(Long userId);
    @Modifying
    @Transactional
    void deleteByUser(User user);
}
