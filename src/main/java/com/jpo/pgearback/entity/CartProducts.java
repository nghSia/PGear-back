package com.jpo.pgearback.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name="cart_products")
public class CartProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_product_id")
    private Long id;

    @Column(name="cart_product_price")
    private Double priceProduct;

    @Column(name="cart_product_quantity")
    private Long quantityProduct;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @PreRemove
    public void preRemove() {
        if (cart != null) {
            cart.setTotalPrice(cart.getTotalPrice() - priceProduct);
            cart.setPriceAfterReduction(cart.getPriceAfterReduction() - priceProduct);
            cart.getCartProducts().remove(this);
        }
    }
}
