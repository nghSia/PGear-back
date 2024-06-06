package com.jpo.pgearback.service.product;

import com.jpo.pgearback.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO addProduct(ProductDTO p_product) throws IOException;
    List<ProductDTO> getAllProduct();
}
