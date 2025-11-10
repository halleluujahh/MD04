package org.com.session06.repository;

import org.com.session06.entity.Category;
import org.com.session06.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasCategory(Category category) {
        return ((root, query, criteriaBuilder) -> category == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("category"), category));
    }

    public static Specification<Product> hasPriceRange(String priceMin, String priceMax) {
        return ((root, query, criteriaBuilder) -> (priceMin.isEmpty() || priceMax.isEmpty())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.between(root.get("unitPrice"), Long.parseLong(priceMin), Long.parseLong(priceMax)));
    }

    public static Specification<Product> hasKeyword(String keyword) {
        return  ((root, query, criteriaBuilder) -> keyword == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("productName"), "%" + keyword + "%"));
    }
}
