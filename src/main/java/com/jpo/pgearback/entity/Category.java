package com.jpo.pgearback.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String nomCategory;

    @Lob
    @Column(name = "category_description")
    private String descriptionCategory;
}
