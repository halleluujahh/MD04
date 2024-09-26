package org.com.session02.service.impl;

import lombok.AllArgsConstructor;
import org.com.session02.model.dto.response.ProductResponseDTO;
import org.com.session02.model.entity.Product;
import org.com.session02.repository.ProductRepository;
import org.com.session02.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> p = productRepository.findAll(pageable);
        List<ProductResponseDTO> listResponse = new ArrayList<>();
        listResponse = p.stream().map(en->new ProductResponseDTO(String.valueOf(en.getProductId()), en.getProductName(), en.isStatus()?"Working":"Not Working", en.getCategory().getCategoryName()
               )).collect(Collectors.toList());
        return new PageImpl<>(listResponse, pageable, listResponse.size());
    }

    @Override
    public Product save() {
        return null;
    }

    @Override
    public Product findById() {
        return null;
    }
}
