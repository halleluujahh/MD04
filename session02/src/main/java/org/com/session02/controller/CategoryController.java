package org.com.session02.controller;

import lombok.AllArgsConstructor;
import org.com.session02.model.entity.Category;
import org.com.session02.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Page<Category>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Category> categories = categoryService.paginate(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> save(@RequestBody Category category) {
        Category categoryNew = categoryService.save(category);
        return new ResponseEntity<Category>(categoryNew, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>("Đã xóa thành công !",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        category.setCategoryId(id);
        Category categoryUpdate =categoryService.save(category);
        return new ResponseEntity<>(categoryUpdate, HttpStatus.OK);
    }
}
