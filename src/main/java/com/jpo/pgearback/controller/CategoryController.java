package com.jpo.pgearback.controller;

import com.jpo.pgearback.dto.CategoryDTO;
import com.jpo.pgearback.entity.Category;
import com.jpo.pgearback.service.category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")

public class CategoryController {

    private final CategoryServiceImpl v_categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl v_categoryService) {
        this.v_categoryService = v_categoryService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO p_category) {
        if(v_categoryService.hasCategoryWithNomCategory(p_category.getNomCategory())){
            return new ResponseEntity<>("category already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        Category v_newCategory = v_categoryService.createCategory(p_category);
        return ResponseEntity.status(HttpStatus.CREATED).body(v_newCategory);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(v_categoryService.getAllCategories(), HttpStatus.OK);
    }
}
