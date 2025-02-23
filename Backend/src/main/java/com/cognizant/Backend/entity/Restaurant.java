package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Table(name = "restaurant")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_restaurant", nullable = false)
    private UUID idRestaurant;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_hour", nullable = false)
    private LocalTime startHour;

    @Column(name = "end_hour", nullable = false)
    private LocalTime endHour;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "rating", nullable = false)
    private double rating;

    @OneToOne
    @JoinColumn(name = "id_user")
    @ToString.Exclude
    private User employee;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MenuItem> menuItems;

    @ManyToMany
    @JoinTable(
            name = "restaurant_category",
            joinColumns = @JoinColumn(name = "id_restaurant"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    @ToString.Exclude
    private List<Category> categories;
}