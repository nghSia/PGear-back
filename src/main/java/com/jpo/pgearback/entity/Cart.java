package com.jpo.pgearback.entity;

import com.jpo.pgearback.enums.CartStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private long id;

    @Column(name="cart_description")
    private String cartDescription;

    @Column(name="cart_date_cart")
    private Date dateCart;

    @Column(name="cart_total_price")
    private Double totalPrice;

    @Column(name="cart_payment_method")
    private String paymentMethod;

    @Column(name="cart_price_after_reduction")
    private Double priceAfterReduction;

    @Enumerated(EnumType.STRING)
    @Column(name="cart_status")
    private CartStatus cartStatus;

    @Column(name="cart_discount_amount")
    private Double discountAmount;

    @Column(name="cart_tracking_num")
    private UUID trackingNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    private List<CartProducts> cartProducts;
}
