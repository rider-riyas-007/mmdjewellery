package com.original.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.original.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.original.ecommerce.entity.CartItem;
import com.original.ecommerce.entity.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser_Id(Long userId);

    Optional<CartItem> findByUser_IdAndProduct_Id(Long userId, Long productId);
    List<CartItem> findByUserAndProduct(User user, Product product);


    // 2. Get all cart items for a user
    List<CartItem> findByUser(User user);
    
}

