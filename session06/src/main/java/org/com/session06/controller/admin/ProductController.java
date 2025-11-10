package org.com.session06.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.session06.dto.request.ProductRequestDTO;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
                                            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sort,
                                            @RequestParam(name = "categoryId", required = false) Long categoryId
    ) throws BadRequestException {
        return new ResponseEntity<>(productService.findAllProducts(page, limit, sort, categoryId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @ModelAttribute ProductRequestDTO productRequestDTO, BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(productService.createProduct(productRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @ModelAttribute ProductRequestDTO productRequestDTO, BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(productService.updateProduct(productRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(productService.deleteCategory(id), HttpStatus.OK);
    }
}
