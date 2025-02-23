package com.cognizant.Backend.mapper;

import com.cognizant.Backend.dto.MenuItemDTO;
import com.cognizant.Backend.entity.MenuItem;

import java.util.List;

public class MenuItemMapper {
    public static MenuItemDTO toMenuItemDTO(MenuItem menuItem) {
        return MenuItemDTO.builder()
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .imagePath(menuItem.getImagePath())
                .build();
    }

    public static List<MenuItemDTO> toMenuItemList(List<MenuItem> menuItemList) {
        return menuItemList.stream()
                .map(MenuItemMapper::toMenuItemDTO)
                .toList();
    }
}
