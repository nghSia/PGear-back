package com.jpo.pgearback.repository;

import com.jpo.pgearback.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findFirstByNomCategory(String p_nomCategory);
}
