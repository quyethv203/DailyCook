package com.example.foodwed.service;

import com.example.foodwed.dto.Request.RecipeCreateRequest;
import com.example.foodwed.dto.Request.RecipeUpdateRequest;
import com.example.foodwed.dto.response.PaginatedResponse;
import com.example.foodwed.dto.response.RecipeDetailResponse;
import com.example.foodwed.dto.response.RecipeEditlResponse;
import com.example.foodwed.dto.response.RecipeResponse;
import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.entity.RecipeCategoryId;
import com.example.foodwed.entity.Recipe_Category;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.CategoryReponsitory;
import com.example.foodwed.repository.RecipeCategoryRepository;
import com.example.foodwed.repository.RecipeReponsitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeReponsitory recipeReponsitory;
    @Autowired
    private CategoryReponsitory categoryReponsitory;
    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    public PaginatedResponse<RecipeResponse> getAllRecipe(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeReponsitory.findAll(pageable);

        // Map Recipe to RecipeResponse
        List<RecipeResponse> recipeResponses = recipePage.getContent().stream()
                .map(recipe -> new RecipeResponse(
                        recipe.getId(),
                        recipe.getName(),
                        recipe.getImage()
                ))
                .toList();

        return new PaginatedResponse<>(
                recipeResponses, // Use the mapped list
                recipePage.getNumber(),
                recipePage.getSize(),
                recipePage.getTotalElements(),
                recipePage.getTotalPages(),
                recipePage.isLast()
        );
    }


    public RecipeEditlResponse create(RecipeCreateRequest recipeRequest) {
        // 1. Kiểm tra tất cả categoryId có tồn tại trong database không
        for (String categoryId : recipeRequest.getCategoryids()) {
            if (!categoryReponsitory.existsById(categoryId)) {
                throw new Appexception(ErrorCode.CATEGORY_NOT_EXITED);
            }
        }

        // 2. Nếu tất cả categoryId đều hợp lệ, tiến hành tạo và lưu Recipe
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredien(recipeRequest.getIngredien());
        recipe.setImage(recipeRequest.getImage());
        recipe.setTime(recipeRequest.getTime());
        recipe.setServes(recipeRequest.getServes());
        recipe.setStep(recipeRequest.getStep());
        recipe.setPrice(recipeRequest.getPrice());
        Recipe savedRecipe = recipeReponsitory.save(recipe);

        // 3. Tạo các bản ghi Recipe_Category
        for (String categoryId : recipeRequest.getCategoryids()) {
            Category category = categoryReponsitory.findById(categoryId).get(); // Vì đã check tồn tại ở trên
            Recipe_Category recipeCategory = new Recipe_Category();
            recipeCategory.setId(new RecipeCategoryId(savedRecipe.getId(), categoryId));
            recipeCategory.setRecipe(savedRecipe);
            recipeCategory.setCategory(category);
            recipeCategoryRepository.save(recipeCategory);
        }

        // 4. Trả về phản hồi
        return new RecipeEditlResponse(savedRecipe, recipeRequest.getCategoryids());
    }


    @Transactional
    public void delete(String recipeId) {
        // Kiểm tra xem Recipe có tồn tại không
        if (!recipeReponsitory.existsById(recipeId)) {
            throw new Appexception(ErrorCode.RECIPE_NOT_FOUND);
        }

        // Xóa tất cả các bản ghi trong bảng recipe_category có liên quan đến recipeId
        recipeCategoryRepository.deleteByRecipeId(recipeId);

        // Xóa bản ghi trong bảng recipe
        recipeReponsitory.deleteById(recipeId);
    }


    public RecipeEditlResponse update(RecipeUpdateRequest recipeRequest) {
        // 1. Kiểm tra xem Recipe có tồn tại không
        Recipe recipe = recipeReponsitory.findById(recipeRequest.getRecipe().getId())
                .orElseThrow(() -> new Appexception(ErrorCode.RECIPE_NOT_FOUND));

        // 2. Kiểm tra tất cả categoryId có tồn tại trong database không
        for (String categoryId : recipeRequest.getCategoryids()) {
            if (!categoryReponsitory.existsById(categoryId)) {
                throw new Appexception(ErrorCode.CATEGORY_NOT_EXITED);
            }
        }

        Optional<Recipe> recipeOld = recipeReponsitory.findById(recipe.getId());

        // 3. Cập nhật các thuộc tính của Recipe
        recipe.setName(recipeRequest.getRecipe().getName());
        recipe.setDescription(recipeRequest.getRecipe().getDescription());
        recipe.setIngredien(recipeRequest.getRecipe().getIngredien());

        recipe.setTime(recipeRequest.getRecipe().getTime());
        recipe.setServes(recipeRequest.getRecipe().getServes());
        recipe.setStep(recipeRequest.getRecipe().getStep());
        // kiểm tra có ảnh mới hay không
        if (recipeRequest.getRecipe().getImage() == null) {
            recipe.setImage(recipeOld.get().getImage());
        } else {
            recipe.setImage(recipeRequest.getRecipe().getImage());
        }
        recipe.setPrice(recipeRequest.getRecipe().getPrice());
        Recipe updatedRecipe = recipeReponsitory.save(recipe);

        // 4. Xóa các bản ghi Recipe_Category cũ liên quan đến Recipe này
        recipeCategoryRepository.deleteByRecipeId(recipeRequest.getRecipe().getId());

        // 5. Thêm các bản ghi Recipe_Category mới
        for (String categoryId : recipeRequest.getCategoryids()) {
            Category category = categoryReponsitory.findById(categoryId).get(); // Chắc chắn tồn tại do đã kiểm tra ở trên
            Recipe_Category recipeCategory = new Recipe_Category();
            recipeCategory.setId(new RecipeCategoryId(updatedRecipe.getId(), categoryId));
            recipeCategory.setRecipe(updatedRecipe);
            recipeCategory.setCategory(category);
            recipeCategoryRepository.save(recipeCategory);
        }

        // 6. Trả về phản hồi
        return new RecipeEditlResponse(updatedRecipe, recipeRequest.getCategoryids());
    }


    public PaginatedResponse<RecipeDetailResponse> getAllPaginated(int page, int size) {
        // Lấy danh sách recipe theo phân trang
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeReponsitory.findAll(pageRequest);


        // Map các Recipe sang RecipeDetailResponse
        List<RecipeDetailResponse> recipeDetails = recipePage.getContent().stream().map(recipe -> {
            List<Category> categories = recipeCategoryRepository.findCategoriesByRecipeId(recipe.getId());
            return new RecipeDetailResponse(recipe, categories);
        }).collect(Collectors.toList());

        // Trả về đối tượng PaginatedResponse
        return new PaginatedResponse<>(
                recipeDetails,
                recipePage.getNumber(),
                recipePage.getSize(),
                recipePage.getTotalElements(),
                recipePage.getTotalPages(),
                recipePage.isLast()
        );
    }

}