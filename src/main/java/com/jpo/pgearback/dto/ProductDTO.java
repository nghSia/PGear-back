package com.jpo.pgearback.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {
    private Long id;
    private String nomProduit;
    private Double prix;
    private String descriptionProduit;
    private byte[] imgProduit;
    private Long categoryId;
    private String nomCategory;
    private MultipartFile imgM;
}
