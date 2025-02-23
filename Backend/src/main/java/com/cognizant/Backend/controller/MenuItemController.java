package com.cognizant.Backend.controller;

import com.cognizant.Backend.dto.MenuItemDTO;
import com.cognizant.Backend.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {
    private final MenuItemService menuItemService;

    @GetMapping("/{restaurant}")
    public ResponseEntity<List<MenuItemDTO>> getMenuItems(@PathVariable String restaurant) {
        return new ResponseEntity<> (
                menuItemService.getMenuItems(restaurant),
                HttpStatus.OK
        );
    }
}
