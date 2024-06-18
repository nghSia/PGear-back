package com.jpo.pgearback.repository;

import com.jpo.pgearback.entity.Cart;
import com.jpo.pgearback.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserIdAndCartStatus(Long userId, CartStatus cartStatus);
}
