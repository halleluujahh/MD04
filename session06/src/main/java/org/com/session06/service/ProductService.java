package org.com.session06.service;

import org.com.session06.dto.request.ProductRequestDTO;
import org.com.session06.entity.Product;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<Product> findAllProducts(Integer page, Integer size, String sort, Long categoryId) throws BadRequestException;
    Product createProduct(ProductRequestDTO productRequestDTO) throws BadRequestException, NotFoundException;
    Product updateProduct(ProductRequestDTO productRequestDTO) throws NotFoundException, BadRequestException;
    String deleteCategory(Long id) throws NotFoundException;
    Product findById(Long id) throws NotFoundException;
    Page<Product> findAllProducts(Integer page, Integer size, String sort, Long categoryId, String priceRange, String keyword) throws BadRequestException;
    List<Product> findByCategoryId(Long categoryId, Long productId) throws BadRequestException;
}
