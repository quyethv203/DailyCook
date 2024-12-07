package com.example.foodwed.repository;

import com.example.foodwed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    // Tìm comment gốc của một recipe
    List<Comment> findByRecipeidAndParentCmtidIsNull(String recipeId);

    // Tìm các reply của một comment
    List<Comment> findByParentCmtid(String parentCmtId);
}
