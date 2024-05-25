package com.jpo.pgearback.service.admin.category;

import com.jpo.pgearback.dto.CategoryDTO;
import com.jpo.pgearback.entity.Category;

import java.util.List;

public interface CategoryService {
    Boolean hasCategoryWithNomCategory(String nomCategory);
    Category createCategory(CategoryDTO p_category);
    List<Category> getAllCategories();
}
