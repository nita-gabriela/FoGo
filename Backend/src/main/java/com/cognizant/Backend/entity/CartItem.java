package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "cart_item")
@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cart_item", nullable = false)
    private UUID idCartItem;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    @ToString.Exclude
    private Order order;

    @OneToOne
    @JoinColumn(name = "id_menu_item")
    private MenuItem menuItem;
}