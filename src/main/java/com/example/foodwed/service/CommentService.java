package com.example.foodwed.service;

import com.example.foodwed.dto.Request.CommentRequest;
import com.example.foodwed.dto.response.CommentResponse;
import com.example.foodwed.entity.Comment;
import com.example.foodwed.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    // Thêm comment hoặc reply
    public CommentResponse addComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setUserid(request.getUserid());
        comment.setRecipeid(request.getRecipeId());
        comment.setContent(request.getContent());
        comment.setParentCmtid(request.getParentCmtId()); // nếu là reply, cần parent comment ID

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponse(savedComment, Collections.emptyList());
    }

    // Lấy danh sách comment và reply theo recipe ID
    public List<CommentResponse> getCommentsByRecipe(String recipeId) {
        List<Comment> comments = commentRepository.findByRecipeidAndParentCmtidIsNull(recipeId);
        return comments.stream()
                .map(comment -> new CommentResponse(comment, getReplies(comment.getCmtid())))
                .collect(Collectors.toList());
    }

    // Lấy danh sách replies của một comment
    private List<CommentResponse> getReplies(String parentCmtId) {
        List<Comment> replies = commentRepository.findByParentCmtid(parentCmtId);
        return replies.stream()
                .map(reply -> new CommentResponse(reply, Collections.emptyList()))
                .collect(Collectors.toList());
    }
}
