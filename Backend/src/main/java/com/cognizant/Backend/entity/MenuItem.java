package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "menu_item")
@Entity
@Data
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_menu_item", nullable = false)
    private UUID idMenuItem;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "imagePath")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "id_restaurant", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL)
    @ToString.Exclude
    private CartItem cartItem;
}