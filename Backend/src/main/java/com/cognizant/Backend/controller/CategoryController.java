package com.cognizant.Backend.controller;

import com.cognizant.Backend.dto.CategoryDTO;
import com.cognizant.Backend.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        return new ResponseEntity<>(
                categoryService.findAllCategories(),
                HttpStatus.OK
        );
    }
}
