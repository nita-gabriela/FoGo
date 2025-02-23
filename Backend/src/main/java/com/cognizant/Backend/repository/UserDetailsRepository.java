package com.cognizant.Backend.repository;

import com.cognizant.Backend.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
}
