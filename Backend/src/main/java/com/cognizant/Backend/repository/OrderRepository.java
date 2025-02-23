package com.cognizant.Backend.repository;

import com.cognizant.Backend.entity.Order;
import com.cognizant.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

        Optional<Order> findByUser(User user);

        Optional<Order> findByUserAndIsConfirmed(User user, boolean isConfirmed);
}
