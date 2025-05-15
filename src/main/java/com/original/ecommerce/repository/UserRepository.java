package com.original.ecommerce.repository;

import java.util.Optional;

import com.original.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
    
    Optional<User> findByName(String name);
    boolean existsByEmail(String email);
    

    

}


