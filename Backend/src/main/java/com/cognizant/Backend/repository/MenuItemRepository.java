package com.cognizant.Backend.repository;

import com.cognizant.Backend.entity.MenuItem;
import com.cognizant.Backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    List<MenuItem> findByRestaurant(Restaurant restaurant);
}
