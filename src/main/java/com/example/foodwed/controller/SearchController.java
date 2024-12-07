package com.example.foodwed.controller;

import com.example.foodwed.dto.response.RecipeResponse;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<?> searchRecipe(
            @RequestParam String name,
            @RequestParam(required = false) String categoryId) {
        try {
            // Gọi service để tìm kiếm
            List<RecipeResponse> recipeSearch = searchService.searchRecipes(name, categoryId);
            return ResponseEntity.ok(recipeSearch);
        } catch (IllegalArgumentException ex) {
            // Lỗi: Trả về 400 với thông báo lỗi
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
