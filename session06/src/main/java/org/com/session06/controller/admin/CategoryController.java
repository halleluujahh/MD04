package org.com.session06.controller.admin;

import jakarta.validation.Valid;
import org.com.session06.dto.request.CategoryRequestDTO;
import org.com.session06.entity.Category;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<Page<Category>> findAllPagination(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @RequestParam(name = "status", defaultValue = "1") Boolean status
    ){
        return new ResponseEntity<>(categoryService.findAll(page, limit, status), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(
    ){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(categoryService.createCategory(categoryRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(categoryService.updateCategory(categoryRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }
}