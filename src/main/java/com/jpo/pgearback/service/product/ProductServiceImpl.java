package com.jpo.pgearback.service.product;

import com.jpo.pgearback.dto.ProductDTO;
import com.jpo.pgearback.entity.Category;
import com.jpo.pgearback.entity.Product;
import com.jpo.pgearback.repository.CategoryRepository;
import com.jpo.pgearback.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO addProduct(ProductDTO p_product) throws IOException {
        Product productACreer = new Product();
        productACreer.setNomProduit(p_product.getNomProduit());
        productACreer.setPrix(p_product.getPrix());
        productACreer.setDescriptionProduit(p_product.getDescriptionProduit());
        productACreer.setImgProduit(p_product.getImgM().getBytes());

        Category categoryProduit = categoryRepository.findById(p_product.getCategoryId()).orElseThrow();

        productACreer.setCategory(categoryProduit);
        return productRepository.save(productACreer).getDTO();
    }

    public List<ProductDTO> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }
}
