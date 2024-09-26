package org.com.session02.service;

import org.com.session02.model.dto.response.ProductResponseDTO;
import org.com.session02.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductResponseDTO> findAll(Pageable pageable);

    Product save();

    Product findById();
}
