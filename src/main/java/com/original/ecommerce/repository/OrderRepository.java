package com.original.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.original.ecommerce.entity.Orders;
import com.original.ecommerce.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_Id(Long userId);
   

    // 2. Get all cart items for a user
    List<Orders> findByUser(User user);
    Optional<Orders> findByIdAndUser(long id,User user);
    List<Orders> findByUserOrderByOrderDateDesc(User user);
}

