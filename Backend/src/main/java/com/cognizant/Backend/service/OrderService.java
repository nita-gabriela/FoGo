package com.cognizant.Backend.service;

import com.cognizant.Backend.entity.*;
import com.cognizant.Backend.repository.CartItemRepository;
import com.cognizant.Backend.repository.MenuItemRepository;
import com.cognizant.Backend.repository.OrderRepository;
import com.cognizant.Backend.repository.SessionIdRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    SessionIdRepository sessionIdRepository;
    @Autowired
    EntityManager entityManager;

    public Order addToCart(UUID sessionId, UUID menuItemId, int quantity) throws Exception {
        User user = getCurrentUser(sessionId);

        Optional<Order> optionalOrder = orderRepository.findByUserAndIsConfirmed(user, false);

        Order order = optionalOrder.orElseGet(() -> createNewOrder(user));

        MenuItem menuItem = findMenuItemById(menuItemId);

        Optional<CartItem> existingCartItem = findExistingCartItem(order, menuItemId);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newCartItem = createNewItem(order, menuItem, quantity);
            order.getCartItems().add(newCartItem);
        }
        updateOrderTotal(order);
        return orderRepository.save(order);
    }

    public Order decreaseQuantity(UUID sessionId, UUID menuItemId) throws Exception {

        Order order = getUserUnconfirmedOrder(sessionId);

        MenuItem menuItem = findMenuItemById(menuItemId);

        Optional<CartItem> existingCartItem = findExistingCartItem(order, menuItemId);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() - 1;
            if (newQuantity > 0) {
                cartItem.setQuantity(newQuantity);
            } else {
                order.getCartItems().remove(cartItem);
                cartItemRepository.deleteByIdCartItem(cartItem.getIdCartItem());
            }
        } else {
            throw new Exception("Cart Item not found");
        }

        updateOrderTotal(order);
        return orderRepository.save(order);
    }

    public Order setItemQuantity(UUID sessionId, UUID menuItemId, Integer newQuantity) throws Exception {
        Order order = getOrderForCurrentUser(sessionId).get();
        MenuItem menuItem = findMenuItemById(menuItemId);

        Optional<CartItem> existingCartItem = findExistingCartItem(order, menuItemId);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            if (newQuantity > 0) {
                cartItem.setQuantity(newQuantity);
            }
        } else {
            throw new Exception("Cart Item not found");
        }

        updateOrderTotal(order);
        return orderRepository.save(order);
    }

    public Order deleteItemFromCart(UUID sessionId, UUID menuItemId) throws Exception {
        Order order = getUserUnconfirmedOrder(sessionId);
        MenuItem menuItem = findMenuItemById(menuItemId);

        Optional<CartItem> existingCartItem = findExistingCartItem(order, menuItemId);

        if (order.getCartItems().isEmpty()) {
            orderRepository.delete(order);
            return null;
        }

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            order.getCartItems().remove(cartItem);
            cartItemRepository.deleteByIdCartItem(cartItem.getIdCartItem());
        } else {
            throw new Exception("Cart Item not found");
        }

        updateOrderTotal(order);
        return orderRepository.save(order);
    }

    private final User getCurrentUser(UUID sessionId) throws Exception {
        Session session = sessionIdRepository.findById(sessionId)
                .orElseThrow(() -> new Exception("Invalid Session ID!"));
        return session.getUser();
    }

    private final Optional<Order> getOrderForCurrentUser(UUID sessionId) throws Exception {
        User user = getCurrentUser(sessionId);
        return orderRepository.findByUser(user);
    }

    private final Order getUserUnconfirmedOrder(UUID sessionId) throws Exception {
        User user = getCurrentUser(sessionId);
        return orderRepository.findByUserAndIsConfirmed(user, false)
                .orElseThrow(() -> new Exception("Invalid Order!"));
    }

    private final Order createNewOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setIsConfirmed(false);
        order.setCartItems(new ArrayList<>());
        order.setAddress("DUM");
        return orderRepository.save(order);
    }

    private final CartItem createNewItem(Order order, MenuItem menuItem, int quantity) {
        CartItem newCartItem = new CartItem();
        newCartItem.setOrder(order);
        newCartItem.setMenuItem(menuItem);
        newCartItem.setQuantity(quantity);
        return cartItemRepository.save(newCartItem);
    }

    private final MenuItem findMenuItemById(UUID menuItemId) throws Exception {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new Exception("Menu Item not found"));
    }

    private final Optional<CartItem> findExistingCartItem(Order order, UUID menuItemId) {
        return order.getCartItems().stream()
                .filter(item -> item.getMenuItem().getIdMenuItem().equals(menuItemId)).findFirst();
    }

    private final void updateOrderTotal(Order order) {
        double newTotal = order.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getMenuItem().getPrice())
                .sum();
        order.setTotal(newTotal);
    }
}

