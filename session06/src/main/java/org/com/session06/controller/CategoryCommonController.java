package org.com.session06.controller;

import lombok.RequiredArgsConstructor;
import org.com.session06.exception.NotFoundException;
import org.com.session06.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category/")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryCommonController {
    private final CategoryService categoryService;

    @GetMapping("{categoryId}")
    public ResponseEntity<?> filterProductByCategororyId(@PathVariable("categoryId") Long id) throws NotFoundException {
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
}
