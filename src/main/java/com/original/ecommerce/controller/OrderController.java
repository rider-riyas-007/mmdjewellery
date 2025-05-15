package com.original.ecommerce.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.original.ecommerce.entity.CartItem;
import com.original.ecommerce.entity.OrderItem;
import com.original.ecommerce.entity.Orders;
import com.original.ecommerce.entity.User;
import com.original.ecommerce.repository.CartItemRepository;
import com.original.ecommerce.repository.OrderItemRepository;
import com.original.ecommerce.repository.OrderRepository;
import com.original.ecommerce.repository.UserRepository;

@Controller
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private NotificationController notificationController;

    @PostMapping("/order/place")
    public String placeOrder(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) return "redirect:/login";

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) return "redirect:/cart";

        // Create a new order
        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        

        orderRepository.save(order);
        notificationController.sendOrderNotification("Order placed by user: " + user.getName());

        // Create order items
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "redirect:/orders"; // Show order confirmation/history
    }
    @GetMapping("/orders")
    public String viewOrders(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "redirect:/login";

        List<Orders> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        model.addAttribute("orders", orders);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        model.addAttribute("formatter", formatter);

        return "orders";
    }


}
