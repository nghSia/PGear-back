package com.jpo.pgearback.service.category;


import com.jpo.pgearback.dto.CategoryDTO;
import com.jpo.pgearback.entity.Category;
import com.jpo.pgearback.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository v_categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository v_categoryRepository) {
        this.v_categoryRepository = v_categoryRepository;
    }

    @Override
    public Category createCategory(CategoryDTO p_category) {
        Category v_newCategory = new Category();
        v_newCategory.setNomCategory(p_category.getNomCategory());
        v_newCategory.setDescriptionCategory(p_category.getDescriptionCategory());

        return v_categoryRepository.save(v_newCategory);
    }

    @Override
    public Boolean hasCategoryWithNomCategory(String p_nomCategory) {
        return v_categoryRepository.findFirstByNomCategory(p_nomCategory).isPresent();
    }

    @Override
    public List<Category> getAllCategories() {
        return v_categoryRepository.findAll();
    }

}
