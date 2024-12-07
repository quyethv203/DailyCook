package com.example.foodwed.repository;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.entity.Recipe_Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeReponsitory extends JpaRepository<Recipe, String> {
    @Query("SELECT r FROM Recipe r WHERE r.id <> :id ORDER BY function('RAND')")
    List<Recipe> findRandomByIdNotAndCategoryId(@Param("id") String id);

    @Query("SELECT rc FROM Recipe_Category rc WHERE "
            + "LOWER(rc.recipe.name) LIKE LOWER(CONCAT('%', :name, '%')) "
            + "AND (rc.category.id = :categoryId OR :categoryId IS NULL)")
    List<Recipe_Category> searchRecipes(
            @Param("name") String name,
            @Param("categoryId") String categoryId);


}
