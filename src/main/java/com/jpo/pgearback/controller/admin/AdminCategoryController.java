package com.jpo.pgearback.controller.admin;

import com.jpo.pgearback.dto.CategoryDTO;
import com.jpo.pgearback.entity.Category;
import com.jpo.pgearback.service.admin.category.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryServiceImpl v_categoryService;

    @PostMapping("category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO p_category) {
        if(v_categoryService.hasCategoryWithNomCategory(p_category.getNomCategory())){
            return new ResponseEntity<>("category already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        Category v_newCategory = v_categoryService.createCategory(p_category);
        return new ResponseEntity<>(v_categoryService, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(v_categoryService.getAllCategories(), HttpStatus.OK);
    }
}
