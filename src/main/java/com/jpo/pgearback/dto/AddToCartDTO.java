package com.jpo.pgearback.dto;

import lombok.Data;

@Data
public class AddToCartDTO {
    private Long userId;
    private Long productId;
}
