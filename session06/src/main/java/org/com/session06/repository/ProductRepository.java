package org.com.session06.repository;

import org.com.session06.entity.Category;
import org.com.session06.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findProductByCategory(Pageable pageable, Category categoryFil);
    boolean findProductByCategory(Category categoryFil);
    Product findProductByProductName(String productName);
    List<Product> findTop4ByCategoryAndStockQuantityGreaterThanAndIdNotInOrderByCreatedAtDesc(Category category, Long number, List<Long> productId);
    Product findProductById(Long id);
}
