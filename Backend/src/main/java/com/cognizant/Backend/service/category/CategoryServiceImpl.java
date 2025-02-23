package com.cognizant.Backend.service.category;

import com.cognizant.Backend.dto.CategoryDTO;
import com.cognizant.Backend.entity.Category;
import com.cognizant.Backend.mapper.CategoryMapper;
import com.cognizant.Backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDTOList(categories);
    }
}
