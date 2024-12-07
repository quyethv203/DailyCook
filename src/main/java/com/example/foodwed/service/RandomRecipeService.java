package com.example.foodwed.service;

import com.example.foodwed.dto.response.RecipeResponse;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.RecipeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RandomRecipeService {
    @Autowired
    private RecipeReponsitory recipeReponsitory;

    public List<RecipeResponse> getSuggestion(String id) {
        List<Recipe> recipes = recipeReponsitory.findRandomByIdNotAndCategoryId(id);
        if (recipes.isEmpty()){
            throw new Appexception(ErrorCode.SUGGESTION_ERROR);
        }

        // Lấy ngẫu nhiên 8 bản ghi
        Collections.shuffle(recipes);
        List<Recipe> randomRecipes = recipes.stream().limit(8).collect(Collectors.toList());

        return randomRecipes.stream()
                .map(recipe -> new RecipeResponse(recipe.getId(), recipe.getName(), recipe.getImage()))
                .collect(Collectors.toList());
    }

}
