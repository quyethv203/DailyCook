package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.CommentRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.CommentResponse;
import com.example.foodwed.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // API để thêm comment hoặc reply
    @PostMapping
    public ResponseEntity<ApiRespone<CommentResponse>> addComment(@RequestBody CommentRequest request) {
        CommentResponse comment = commentService.addComment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiRespone<>("success", "201", "Comment or reply added successfully", comment));
    }

    // API để lấy tất cả comments và replies của một công thức
    @GetMapping("/{recipeId}")
    public ResponseEntity<ApiRespone<List<CommentResponse>>> getCommentsByRecipe(@PathVariable String recipeId) {
        List<CommentResponse> comments = commentService.getCommentsByRecipe(recipeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<>("success", "200", "Comments retrieved successfully", comments));
    }
}
