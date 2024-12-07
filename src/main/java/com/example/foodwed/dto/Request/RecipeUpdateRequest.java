package com.example.foodwed.dto.Request;

import com.example.foodwed.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeUpdateRequest {
    private Recipe recipe;
    private List<String> categoryids;
}
