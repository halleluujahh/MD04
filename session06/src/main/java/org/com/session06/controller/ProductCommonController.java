package org.com.session06.controller;

import lombok.RequiredArgsConstructor;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/")
@CrossOrigin
@RequiredArgsConstructor
public class ProductCommonController {
    private final ProductService productService;

    @GetMapping("{productId}")
    public ResponseEntity<?> findProductById(@PathVariable("productId") Long id) throws NotFoundException {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sort,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "priceRange", required = false) String priceRange,
            @RequestParam(name = "keyword", required = false) String keyword
    ) throws BadRequestException {
        return new ResponseEntity<>(productService.findAllProducts(page, limit, sort, categoryId, priceRange, keyword), HttpStatus.OK);
    }

    @GetMapping("findTop4/{categoryId}/{productId}")
    public ResponseEntity<?> findByCategoryId(
            @PathVariable(value = "categoryId", required = true) Long categoryId,
            @PathVariable(value = "productId", required = true) Long productId
    ) throws BadRequestException {
        return new ResponseEntity<>(productService.findByCategoryId(categoryId, productId), HttpStatus.OK);
    }
}
