package com.example.foodwed.repository;

import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Recipe_Category;
import com.example.foodwed.entity.RecipeCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeCategoryRepository extends JpaRepository<Recipe_Category, RecipeCategoryId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Recipe_Category rc WHERE rc.recipe.id = :recipeId")
    void deleteByRecipeId(String recipeId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Recipe_Category rc WHERE rc.category.id = :categoryId")
    void deleteByCategoryId(String categoryId);
    @Query("SELECT rc.category FROM Recipe_Category rc WHERE rc.recipe.id = :recipeId")
    List<Category> findCategoriesByRecipeId(String recipeId);
}
