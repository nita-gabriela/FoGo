package com.cognizant.Backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItemDTO {
    private String name;
    private double price;
    private String description;
    private String imagePath;
}
