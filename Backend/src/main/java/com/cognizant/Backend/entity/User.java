package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "user")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders;

    @OneToOne
    @JoinColumn(name = "id_user_details")
    @ToString.Exclude
    private UserDetails userDetails;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Restaurant restaurant;
}