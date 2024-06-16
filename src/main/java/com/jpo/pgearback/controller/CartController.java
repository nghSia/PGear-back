package com.jpo.pgearback.controller;

import com.jpo.pgearback.dto.AddToCartDTO;
import com.jpo.pgearback.service.Cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService s_cartSerice;

    @Autowired
    public CartController (CartService p_cartService){
        this.s_cartSerice = p_cartService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> addProductToCart(@RequestBody AddToCartDTO p_addToCart){
        return s_cartSerice.addProductsToCart(p_addToCart);
    }
}
