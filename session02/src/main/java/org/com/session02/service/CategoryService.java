package org.com.session02.service;

import org.com.session02.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    void delete(Long id);

    Category findById(Long id);

    Page<Category> paginate(Pageable pageable);
}
