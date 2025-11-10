package org.com.session06.repository;

import org.com.session06.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryName(String name);

    Page<Category> findCategoryByStatus(Pageable pageable, Boolean status);

    List<Category> findCategoryByStatus(Boolean status);

    @Query(value = "SELECT " +
            "c.id AS category_id, c.name AS category_name, c.description AS category_description, c.status AS category_status, " +
            "p.id AS product_id, p.description AS product_description, p.image AS product_image, " +
            "p.product_name AS product_name, p.sku AS product_sku, p.stock_quantity AS product_stock_quantity, " +
            "p.unit_price AS product_unit_price, p.category_id AS product_category_id, " +
            "p.created_at AS product_created_at, p.updated_at AS product_updated_at " +
            "FROM categories c " +
            "JOIN products p ON c.id = p.category_id " +
            "WHERE c.status = :status AND p.stock_quantity > 0",
            nativeQuery = true)
    List<Object[]> findAllFieldsByStatusAndStock(@Param("status") Boolean status);

}