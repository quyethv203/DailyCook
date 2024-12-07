package com.example.foodwed.dto.Request;

import lombok.Data;

@Data
public class CommentRequest {
    private String userid;      // ID của người dùng thêm comment
    private String recipeId;    // ID của recipe
    private String content;     // Nội dung của comment
    private String parentCmtId; // Nếu là reply, đây là ID của comment gốc
}