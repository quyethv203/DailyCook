package com.example.foodwed.repository;

import com.example.foodwed.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,String> {
    Page<Orders> findByIsactive(boolean isactive, Pageable pageable);
    Page<Orders> findByUid(String uid, Pageable pageable);
}
