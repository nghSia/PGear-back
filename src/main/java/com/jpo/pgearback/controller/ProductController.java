package com.jpo.pgearback.controller;

import com.jpo.pgearback.dto.ProductDTO;
import com.jpo.pgearback.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDTO p_product) throws IOException {
        if(productService.hasProductWithNomProduit(p_product.getNomProduit())){
            return new ResponseEntity<>("Produit already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        ProductDTO newProductDTO = productService.addProduct(p_product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOS = productService.getAllProduct();
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/find/{p_name}")
    public ResponseEntity<List<ProductDTO>> getAllProductByProductName(@PathVariable String p_name) {
        if(p_name == null){
            List<ProductDTO> productDTOS = productService.getAllProduct();
            return ResponseEntity.ok(productDTOS);
        } else {
            List<ProductDTO> productDTOS = productService.getAllProductByProductName(p_name);
            return ResponseEntity.ok(productDTOS);
        }
    }

    @GetMapping("/find/category/{p_nomCategory}")
    public ResponseEntity<List<ProductDTO>> getAllProductByCategory(@PathVariable String p_nomCategory) {
        if(p_nomCategory == null){
            List<ProductDTO> productDTOS = productService.getAllProduct();
            return ResponseEntity.ok(productDTOS);
        } else {
            List<ProductDTO> productDTOS = productService.getAllProductByCategory(p_nomCategory);
            return ResponseEntity.ok(productDTOS);
        }
    }


    @DeleteMapping("/delete/{p_uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long p_uuid) {
        boolean isDeleted = productService.deleteProduct(p_uuid);
        if(isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
