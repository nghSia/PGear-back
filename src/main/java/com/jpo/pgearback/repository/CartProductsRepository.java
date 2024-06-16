package com.jpo.pgearback.repository;

import com.jpo.pgearback.entity.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {
    Optional<CartProducts> findByProductIdAndCartIdAndUserId(Long p_productId, Long p_cartId, Long userId);
}
