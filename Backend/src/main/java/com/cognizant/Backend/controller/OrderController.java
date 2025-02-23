package com.cognizant.Backend.controller;

import com.cognizant.Backend.dto.OrderDTO;
import com.cognizant.Backend.entity.Order;
import com.cognizant.Backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@CookieValue("sessionID") String cookieId, @RequestBody OrderDTO orderDTO) {
        try{

            UUID sessionId = UUID.fromString(cookieId);
            //DTO cu parametrii functiei
            Order updatedOrder = orderService.addToCart(sessionId, orderDTO.getMenuItemId(), orderDTO.getQuantity());
                return ResponseEntity.ok(updatedOrder.getIdOrder()); //trimit UUID orderID din DTO
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.valueOf(401)).body(e.getMessage());
        }
    }
    @PostMapping("/cart/set-quantity")
    public ResponseEntity<?> setQuantity(@CookieValue("sessionID") String cookieId, @RequestBody OrderDTO orderDTO) {
        try{

            UUID sessionId = UUID.fromString(cookieId);
            //DTO cu parametrii functiei
            Order updatedOrder = orderService.setItemQuantity(sessionId, orderDTO.getMenuItemId(), orderDTO.getQuantity());
            return ResponseEntity.ok(updatedOrder.getIdOrder()); //trimit UUID orderID din DTO
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.valueOf(401)).body(e.getMessage());
        }
    }
    @PostMapping("/cart/decrease")
    public ResponseEntity<?> decreaseQuantity(@CookieValue("sessionID") String cookieId, @RequestBody OrderDTO orderDTO) {
        try {
            UUID sessionId = UUID.fromString(cookieId);
            Order updatedOrder = orderService.decreaseQuantity(sessionId, orderDTO.getMenuItemId());
            return ResponseEntity.ok(updatedOrder.getIdOrder());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.valueOf(401)).body(e.getMessage());
        }
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<?> deleteFromCart(@CookieValue("sessionID") String cookieId, @RequestBody OrderDTO orderDTO) {
        try {
            UUID sessionId = UUID.fromString(cookieId);
            Order updatedOrder = orderService.deleteItemFromCart(sessionId, orderDTO.getMenuItemId());
            return ResponseEntity.ok(updatedOrder.getIdOrder());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.valueOf(401)).body(e.getMessage());
        }
    }
}
