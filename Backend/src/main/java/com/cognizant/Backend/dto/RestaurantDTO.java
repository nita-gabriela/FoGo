package com.cognizant.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private String name;
    private double latitude;
    private double longitude;
    private String description;
    private LocalTime startHour;
    private LocalTime endHour;
    private String phoneNumber;
    private double rating;
}
