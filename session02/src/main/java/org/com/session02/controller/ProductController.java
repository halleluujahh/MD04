package org.com.session02.controller;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import org.com.session02.model.dto.response.ProductResponseDTO;
import org.com.session02.model.entity.Product;
import org.com.session02.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLEngineResult;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/findAll")
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ){
        PageRequest pageRequest = PageRequest.of(pageNo, limit);
        Page<ProductResponseDTO> products = productService.findAll(pageRequest);
        return new ResponseEntity(products, HttpStatus.OK);
    }
}
