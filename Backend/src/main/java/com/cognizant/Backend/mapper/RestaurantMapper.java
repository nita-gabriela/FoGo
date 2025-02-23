package com.cognizant.Backend.mapper;

import com.cognizant.Backend.dto.RestaurantDTO;
import com.cognizant.Backend.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {
    public RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .description(restaurant.getDescription())
                .startHour(restaurant.getStartHour())
                .endHour(restaurant.getEndHour())
                .phoneNumber(restaurant.getPhoneNumber())
                .rating(restaurant.getRating())
                .build();
    }

    public Restaurant toRestaurant(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .name(restaurantDTO.getName())
                .latitude(restaurantDTO.getLatitude())
                .longitude(restaurantDTO.getLongitude())
                .description(restaurantDTO.getDescription())
                .startHour(restaurantDTO.getStartHour())
                .endHour(restaurantDTO.getEndHour())
                .phoneNumber(restaurantDTO.getPhoneNumber())
                .rating(restaurantDTO.getRating())
                .build();
    }

    public List<RestaurantDTO> toRestaurantDTOList(List<Restaurant> restaurantList) {
        return restaurantList.stream()
                .map(this::toRestaurantDTO)
                .toList();
    }
}
