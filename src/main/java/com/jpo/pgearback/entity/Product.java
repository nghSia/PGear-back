package com.jpo.pgearback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    @Column(name="product_name")
    private String nomProduit;

    @Column(name="product_price")
    private Double prix;

    @Lob
    @Column(name= "product_description")
    private String descriptionProduit;

    @Lob
    @Column(columnDefinition = "longblob", name="product_image")
    private byte[] imgProduit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;
}
