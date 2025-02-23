package com.cognizant.Backend.service.restaurant;

import com.cognizant.Backend.dto.RestaurantDTO;
import com.cognizant.Backend.entity.Restaurant;
import com.cognizant.Backend.mapper.RestaurantMapper;
import com.cognizant.Backend.repository.RestaurantRepository;
import com.cognizant.Backend.util.HaversineDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    public static final double DISTANCE = 5;

    @Override
    public List<RestaurantDTO> findNearbyRestaurantsByCategory(List<String> categories,
                                                               double latitude, double longitude) {
        List<Restaurant> restaurants;
        if (categories == null || categories.isEmpty()) {
            restaurants = restaurantRepository.findAllByOrderByRatingDesc();
        } else {
            restaurants = restaurantRepository.findByCategoriesNameInOrderByRatingDesc(categories);
        }
        List<Restaurant> nearbyRestaurants = restaurants.stream()
                .filter(restaurant -> isRestaurantNearby(restaurant, latitude, longitude))
                .toList();
        return restaurantMapper.toRestaurantDTOList(nearbyRestaurants);
    }

    private boolean isRestaurantNearby(Restaurant restaurant, double latitude, double longitude) {
        double distance = HaversineDistance.calculateDistance(restaurant.getLatitude(),
                restaurant.getLongitude(), latitude, longitude);
        return distance <= DISTANCE;
    }
}
