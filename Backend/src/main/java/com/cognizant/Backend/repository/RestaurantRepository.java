package com.cognizant.Backend.repository;

import com.cognizant.Backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByRatingGreaterThanOrderByRatingDesc(double rating);
    Restaurant findByName(String name);
    List<Restaurant> findAllByOrderByRatingDesc();
    List<Restaurant> findByCategoriesNameInOrderByRatingDesc(List<String> categories);
}
