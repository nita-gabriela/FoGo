package com.cognizant.Backend.service.restaurant;

import com.cognizant.Backend.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDTO> findNearbyRestaurantsByCategory(
            List<String> categories, double latitude, double longitude);
}
