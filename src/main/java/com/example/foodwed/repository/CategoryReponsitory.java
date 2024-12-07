package com.example.foodwed.repository;

import com.example.foodwed.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category,String> {

    Boolean findByName(String name);
    boolean existsByName(String name);
    Optional<Category> findById(String name);





}
