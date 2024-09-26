package com.ra.session01.service;

import com.ra.session01.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category create(Category category);
    Category findById(Long id);
    Category update(Category category);
    void delete(Long id);
}
