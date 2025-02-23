package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "\"order\"")
@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_order", nullable = false)
    private UUID idOrder;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CartItem> cartItems;

}