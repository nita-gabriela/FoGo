package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.util.UUID;

@Table(name = "category")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_category", nullable = false)
    private UUID idCategory;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    private List<Restaurant> restaurants;
}