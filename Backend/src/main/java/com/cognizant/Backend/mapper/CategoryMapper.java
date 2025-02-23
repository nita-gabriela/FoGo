package com.cognizant.Backend.mapper;

import com.cognizant.Backend.dto.CategoryDTO;
import com.cognizant.Backend.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    public CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .build();
    }

    public List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryDTO)
                .toList();
    }
}
