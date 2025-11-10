package org.com.session06.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.session06.dto.request.ProductRequestDTO;
import org.com.session06.entity.Category;
import org.com.session06.entity.Product;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.repository.CategoryRepository;
import org.com.session06.repository.ProductRepository;
import org.com.session06.repository.ProductSpecification;
import org.com.session06.service.ProductService;
import org.com.session06.service.UploadFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UploadFileService uploadFileService;

    @Override
    public Page<Product> findAllProducts(Integer page, Integer size, String sort, Long categoryId) throws BadRequestException {
        Pageable pageable;
        Sort sortProduct;
        if (sort != null) {
            if (sort.equals("stockQuantity") || sort.equals(("unitPrice"))) {
                sortProduct = Sort.by(Sort.Order.asc(sort));
            } else {
                sortProduct = Sort.by(Sort.Order.desc(sort));
            }
            pageable = PageRequest.of(page, size, sortProduct);
        } else {
            pageable = PageRequest.of(page, size);
        }
        if (categoryId != null) {
            Category categoryFil = categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException(String.format("Không tìm thấy danh mục với id %l.", categoryId)));
            return productRepository.findProductByCategory(pageable, categoryFil);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO) throws BadRequestException, NotFoundException {
        if (productRepository.findProductByProductName(productRequestDTO.getProductName()) != null) {
            throw new BadRequestException(String.format("Sản phẩm %s này đã tồn tại!", productRequestDTO.getProductName()));
        }
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục để thêm mới sản phẩm."));
        String imageUrl = "";
        if (productRequestDTO.getImage() != null) {
            imageUrl = uploadFileService.uploadFileToLocal(productRequestDTO.getImage());
        }
        Product product = Product.builder()
                .productName(productRequestDTO.getProductName())
                .unitPrice(productRequestDTO.getPrice())
                .stockQuantity(productRequestDTO.getStock())
                .description(productRequestDTO.getDescription())
                .image(imageUrl)
                .createdAt(LocalDate.now())
                .sku(UUID.randomUUID().toString())
                .category(category)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductRequestDTO productRequestDTO) throws NotFoundException, BadRequestException {
        String imageUrl = "";
        Product productOldName = productRepository.findProductByProductName(productRequestDTO.getProductName());
        if (productOldName != null) {
            if (productOldName.getId() == productRequestDTO.getProductId()) {
                Product product = productRepository.findById(productRequestDTO.getProductId()).orElseThrow(
                        () -> new NotFoundException(String.format("Sản phẩm với id %d không tồn tại!", productRequestDTO.getProductId()))
                );
                product.setProductName(productRequestDTO.getProductName());
                product.setUnitPrice(productRequestDTO.getPrice());
                product.setStockQuantity(productRequestDTO.getStock());
                product.setDescription(productRequestDTO.getDescription());
                product.setCategory(categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException(String.format("Danh mục với id %d không tồn tại!", productRequestDTO.getCategoryId()))));
                if (productRequestDTO.getImage() != null) {
                    imageUrl = uploadFileService.uploadFileToLocal(productRequestDTO.getImage());
                    product.setImage(imageUrl);
                }
                product.setUpdatedAt(LocalDate.now());
                return productRepository.save(product);
            } else {
                throw new BadRequestException(String.format("Sản phẩm %s đã tồn tại!", productRequestDTO.getProductName()));
            }
        }
        Product product = productRepository.findById(productRequestDTO.getProductId()).orElseThrow(
                () -> new NotFoundException(String.format("Sản phẩm với id %d không tồn tại!", productRequestDTO.getProductId()))
        );
        product.setProductName(productRequestDTO.getProductName());
        product.setUnitPrice(productRequestDTO.getPrice());
        product.setStockQuantity(productRequestDTO.getStock());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException(String.format("Danh mục với id %d không tồn tại!", productRequestDTO.getCategoryId()))));
        if (productRequestDTO.getImage() != null) {
            imageUrl = uploadFileService.uploadFileToLocal(productRequestDTO.getImage());
            product.setImage(imageUrl);
        }
        product.setUpdatedAt(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public String deleteCategory(Long id) throws NotFoundException {
        Product productToDelete = productRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Category with id %d not exist!", id)));
        productRepository.deleteById(productToDelete.getId());
        return String.format("Deleted product with id %d", id);
    }

    @Override
    public Product findById(Long id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Không tìm thấy sản phẩm với id %l", id)));
    }

    @Override
    public Page<Product> findAllProducts(Integer page, Integer size, String sort, Long categoryId, String priceRange, String keyword) throws BadRequestException {
        Pageable pageable;
        Sort sortProduct;
        if (sort != null) {
            if (sort.equals("stockQuantity") || sort.equals(("unitPrice"))) {
                sortProduct = Sort.by(Sort.Order.asc(sort));
            } else {
                sortProduct = Sort.by(Sort.Order.desc(sort));
            }
            pageable = PageRequest.of(page, size, sortProduct);
        } else {
            pageable = PageRequest.of(page, size);
        }
        String priceMin = "";
        String priceMax = "";
        Category categoryFil = null;
        if (categoryId != null) {
            categoryFil = categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException(String.format("Không tìm thấy danh mục với id %l.", categoryId)));
        }
        if (priceRange != null) {
            String[] priceRangeString = null;
            priceRangeString = priceRange.split("-");
            priceMin = priceRangeString[0];
            priceMax = priceRangeString[1];
        }
        Specification<Product> specificationProduct = Specification.where(ProductSpecification.hasCategory(categoryFil))
                .and(ProductSpecification.hasPriceRange(priceMin, priceMax))
                .and(ProductSpecification.hasKeyword(keyword)).and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("stockQuantity"), 0));
        return productRepository.findAll(specificationProduct, pageable);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId, Long productId) throws BadRequestException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new BadRequestException(String.format("Không tìm thấy danh mục với %d", categoryId))
        );
        List<Long> productIdList = Arrays.asList(productId);
        return productRepository.findTop4ByCategoryAndStockQuantityGreaterThanAndIdNotInOrderByCreatedAtDesc(category, 0L, productIdList);
    }
}
