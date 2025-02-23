package com.cognizant.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "user_details")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user_details", nullable = false)
    private UUID idUserDetails;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
    @ToString.Exclude
    private User user;
}