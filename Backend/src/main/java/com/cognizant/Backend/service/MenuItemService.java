package com.cognizant.Backend.service;

import com.cognizant.Backend.dto.MenuItemDTO;
import com.cognizant.Backend.entity.Restaurant;
import com.cognizant.Backend.mapper.MenuItemMapper;
import com.cognizant.Backend.repository.MenuItemRepository;
import com.cognizant.Backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public List<MenuItemDTO> getMenuItems(String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByName(restaurantName);
        return MenuItemMapper.toMenuItemList(menuItemRepository.findByRestaurant(restaurant));
    }
}
