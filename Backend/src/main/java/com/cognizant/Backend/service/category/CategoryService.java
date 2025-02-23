package com.cognizant.Backend.service.category;

import com.cognizant.Backend.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAllCategories();
}
