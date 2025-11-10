package org.com.session06.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.session06.dto.request.CategoryRequestDTO;
import org.com.session06.entity.Category;
import org.com.session06.entity.Product;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.repository.CategoryRepository;
import org.com.session06.repository.ProductRepository;
import org.com.session06.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Category> findAll() {
        List<Object[]> results = categoryRepository.findAllFieldsByStatusAndStock(true);
        Map<Long, Category> categoryMap = new HashMap<>();

        for (Object[] row : results) {
            Long categoryId = ((Long) row[0]).longValue(); // c.id
            String categoryName = (String) row[1];              // c.name
            String categoryDescription = (String) row[2];       // c.description
            Boolean categoryStatus = (Boolean) row[3];          // c.status

            Long productId = ((Long) row[4]).longValue(); // p.id
            String productDescription = (String) row[5];        // p.description
            String productImage = (String) row[6];              // p.image
            String productName = (String) row[7];               // p.product_name
            String productSku = (String) row[8];                // p.sku
            Integer productStockQuantity = (Integer) row[9];    // p.stock_quantity
            BigDecimal productUnitPrice = (BigDecimal) row[10]; // p.unit_price

            Category category = categoryMap.getOrDefault(categoryId, new Category());
            category.setId(categoryId);
            category.setCategoryName(categoryName);
            category.setDescription(categoryDescription);
            category.setStatus(categoryStatus);

            if (category.getProducts() == null) {
                category.setProducts(new HashSet<>());
            }
            Product product = new Product();
            product.setId(productId);
            product.setDescription(productDescription);
            product.setImage(productImage);
            product.setProductName(productName);
            product.setSku(productSku);
            product.setStockQuantity(productStockQuantity);
            product.setUnitPrice(productUnitPrice);
            product.setCategory(category);

            category.getProducts().add(product);
            categoryMap.put(categoryId, category);
        }
        return new ArrayList<>(categoryMap.values());
    }

    @Override
    public Page<Category> findAll(Integer page, Integer limit, Boolean status) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Category> categories = categoryRepository.findCategoryByStatus(pageable, status);
        return categories;
    }

    @Override
    public Category createCategory(CategoryRequestDTO categoryRequestDTO) throws BadRequestException {
        if (categoryRepository.findCategoryByCategoryName(categoryRequestDTO.getCategoryName()) != null) {
            throw new BadRequestException(String.format("Danh mục %s này đã tồn tại!", categoryRequestDTO.getCategoryName()));
        }
        Category category = Category.builder()
                .categoryName(categoryRequestDTO.getCategoryName())
                .description(categoryRequestDTO.getDescription())
                .status(categoryRequestDTO.getStatus())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(CategoryRequestDTO categoryRequestDTO) throws NotFoundException, BadRequestException {
        Category categoryOldName = categoryRepository.findCategoryByCategoryName(categoryRequestDTO.getCategoryName());
        if (categoryOldName != null) {
            if (categoryOldName.getId() == categoryRequestDTO.getCategoryId()) {
                Category category = categoryRepository.findById(categoryRequestDTO.getCategoryId()).orElseThrow(
                        () -> new NotFoundException(String.format("Danh mục với id %d không tồn tại!", categoryRequestDTO.getCategoryId()))
                );
                category.setCategoryName(categoryRequestDTO.getCategoryName());
                category.setDescription(categoryRequestDTO.getDescription());
                category.setStatus(categoryRequestDTO.getStatus());
                return categoryRepository.save(category);
            } else {
                throw new BadRequestException(String.format("Danh mục %s đã tồn tại!", categoryRequestDTO.getCategoryName()));
            }
        }
        Category category = categoryRepository.findById(categoryRequestDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException(String.format("Danh mục với id %d không tồn tại!", categoryRequestDTO.getCategoryId()))
        );
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        category.setDescription(categoryRequestDTO.getDescription());
        category.setStatus(categoryRequestDTO.getStatus());
        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) throws NotFoundException {
        Category categoryToDelete = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Danh mục với id %d không tồn tại!", id)));
        if (categoryToDelete!=null) {
            if(!productRepository.findProductByCategory(categoryToDelete)){
                categoryRepository.deleteById(id);
                return String.format("Deleted Category with id %d", id);
            }
        }
        return String.format("Category with id %d have product already!", id);
    }

    @Override
    public Category findCategoryById(Long id) throws NotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Danh mục với id %d không tồn tại!", id)));
    }
}