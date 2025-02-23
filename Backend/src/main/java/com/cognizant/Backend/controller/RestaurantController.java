package com.cognizant.Backend.controller;

import com.cognizant.Backend.dto.CoordinatesDTO;
import com.cognizant.Backend.dto.RestaurantDTO;
import com.cognizant.Backend.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/nearby")
    public ResponseEntity<List<RestaurantDTO>> findRestaurantsByCategory(
            @RequestParam(required = false) List<String> categories,
            @RequestBody CoordinatesDTO coordinatesDTO) {
        return new ResponseEntity<>(
                restaurantService.findNearbyRestaurantsByCategory(categories,
                        coordinatesDTO.getLatitude(), coordinatesDTO.getLongitude()),
                HttpStatus.OK
        );
    }
}
