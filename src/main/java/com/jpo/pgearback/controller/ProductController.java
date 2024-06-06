package com.jpo.pgearback.controller;

import com.jpo.pgearback.dto.ProductDTO;
import com.jpo.pgearback.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")

public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> addProduct(@ModelAttribute ProductDTO p_product) throws IOException {
        ProductDTO newProductDTO = productService.addProduct(p_product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOS = productService.getAllProduct();
        return ResponseEntity.ok(productDTOS);
    }
}
