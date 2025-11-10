package org.com.session06.service;




import org.com.session06.dto.request.CategoryRequestDTO;
import org.com.session06.entity.Category;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Page<Category> findAll(Integer page, Integer limit, Boolean status);
    Category createCategory(CategoryRequestDTO categoryRequestDTO) throws BadRequestException;
    Category updateCategory(CategoryRequestDTO categoryRequestDTO) throws NotFoundException, BadRequestException;
    String deleteCategory(Long id) throws NotFoundException;
    Category findCategoryById(Long id) throws NotFoundException;
}