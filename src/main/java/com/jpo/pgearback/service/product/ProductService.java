package com.jpo.pgearback.service.product;

import com.jpo.pgearback.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO addProduct(ProductDTO p_product) throws IOException;
    List<ProductDTO> getAllProduct();
    List<ProductDTO> getAllProductByProductName(String p_name);
    boolean deleteProduct(Long p_uuid);
    Boolean hasProductWithNomProduit(String p_nomProduit);
}
