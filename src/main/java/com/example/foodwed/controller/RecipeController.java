package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.RecipeCreateRequest;
import com.example.foodwed.dto.Request.RecipeUpdateRequest;
import com.example.foodwed.dto.response.*;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipeAll")
    public ResponseEntity<?> getAllRecipe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        PaginatedResponse<RecipeResponse> response = recipeService.getAllRecipe(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<>(
                        "success",
                        "200",
                        "Recipes retrieved successfully",
                        response
                ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("ingredien") String ingredien,
                                          @RequestParam("step") String step,
                                          @RequestParam("time") int time,
                                          @RequestParam("serves") int serves,
                                          @RequestParam("price") int price,
                                          @RequestParam("categoryids") List<String> categoryids,
                                          @RequestParam("image") MultipartFile image) throws IOException {

        // 1. Lưu file ảnh vào thư mục trên server
        String uploadDir = "E:/BTLSpringboot/my-app/src/assests/images"; // Đường dẫn lưu ảnh
        String imageName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename(); // Tạo tên file duy nhất
        Path imagePath = Paths.get(uploadDir, imageName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        // 2. Lưu tên file ảnh vào cơ sở dữ liệu
        RecipeCreateRequest request = RecipeCreateRequest.builder()
                .name(name)
                .description(description)
                .ingredien(ingredien)
                .step(step)
                .time(time)
                .serves(serves)
                .price(price)
                .categoryids(categoryids)
                .image(imageName) // Chỉ lưu tên file
                .build();

        // 3. Gọi service để lưu thông tin recipe
        RecipeEditlResponse recipe =recipeService.create(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiRespone<RecipeEditlResponse>("success","200","Recipe create successfully",recipe)
        );
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateRecipe(@RequestParam("id") String id,
                                          @RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("ingredien") String ingredien,
                                          @RequestParam("step") String step,
                                          @RequestParam("time") int time,
                                          @RequestParam("serves") int serves,
                                          @RequestParam("price") int price,
                                          @RequestParam("categoryids") List<String> categoryids,
                                          @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        // Lưu ảnh mới nếu có
        String imageName = null;
        if (image != null && !image.isEmpty()) {
            String uploadDir = "E:/BTLSpringboot/my-app/src/assests/images"; // Đường dẫn lưu ảnh
            imageName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename(); // Tạo tên file duy nhất
            Path imagePath = Paths.get(uploadDir, imageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        }

        Recipe recipe = new Recipe(id,ingredien,description,name,step,imageName,time,serves,price);
        System.out.print(recipe);
        // Tạo đối tượng RecipeUpdateRequest từ các tham số nhận được
        RecipeUpdateRequest request = RecipeUpdateRequest.builder()
                .recipe(recipe)
                .categoryids(categoryids) // Các categoryIds
                .build();

        // Gọi service để cập nhật thông tin recipe
        RecipeEditlResponse recipeUpdate =recipeService.update(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiRespone<RecipeEditlResponse>("success","200","Recipe create successfully",recipeUpdate)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String id){
        if (id == null || id.trim().isEmpty()) {
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        recipeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<String>("success", "200", "Recipe deleted successfully", "delete success"));
    }
    @GetMapping()
    public ResponseEntity<ApiRespone<PaginatedResponse<RecipeDetailResponse>>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginatedResponse<RecipeDetailResponse> paginatedResponse = recipeService.getAllPaginated(page, size);
        ApiRespone<PaginatedResponse<RecipeDetailResponse>> response = new ApiRespone<>(
                "success", "200", "Recipes retrieved successfully", paginatedResponse
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}