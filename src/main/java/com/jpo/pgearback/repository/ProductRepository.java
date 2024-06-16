package com.jpo.pgearback.repository;

import com.jpo.pgearback.entity.Category;
import com.jpo.pgearback.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNomProduitContaining(String p_nomProduit);
    List<Product> findByCategoryNomCategory(String p_nomCategory);
    Optional<Product> findFirstByNomProduit(String p_nomProduit);

}
