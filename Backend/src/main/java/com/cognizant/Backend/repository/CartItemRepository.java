package com.cognizant.Backend.repository;

import com.cognizant.Backend.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.idCartItem = :id")
    void deleteByIdCartItem(@Param("id") UUID id);
}
