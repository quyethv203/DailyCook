package com.example.foodwed.service;

import com.example.foodwed.dto.response.RecipeResponse;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.entity.Recipe_Category;
import com.example.foodwed.entity.Recipe_Category;
import com.example.foodwed.repository.RecipeCategoryRepository;
import com.example.foodwed.repository.RecipeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private RecipeReponsitory recipeReponsitory;

    // Phương thức tìm kiếm theo tên và categoryId
    public List<RecipeResponse> searchRecipes(String name, String categoryId) {
        // Thực hiện tìm kiếm với các tham số truyền vào
        List<Recipe_Category> recipeCategories = recipeReponsitory.searchRecipes(name, categoryId);

        Collections.shuffle(recipeCategories);
        List<Recipe_Category> randomRecipeSearch = recipeCategories.stream().limit(16).collect(Collectors.toList());

        // Chuyển đổi kết quả từ RecipeCategory thành RecipeResponse
        return randomRecipeSearch.stream()
                .map(rc -> new RecipeResponse(rc.getRecipe().getId(), rc.getRecipe().getName(), rc.getRecipe().getImage()))
                .collect(Collectors.toList());
    }
}
