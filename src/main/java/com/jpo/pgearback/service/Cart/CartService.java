package com.jpo.pgearback.service.Cart;

import com.jpo.pgearback.dto.AddToCartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface CartService {
    ResponseEntity<?> addProductsToCart(AddToCartDTO p_addToCartDTO);
}
