package com.example.foodwed.dto.response;

import com.example.foodwed.entity.Comment;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommentResponse {
    private String cmtid;
    private String userid;
    private String content;
    private LocalDate date;
    private List<CommentResponse> replies;

    public CommentResponse(Comment comment, List<CommentResponse> replies) {
        this.cmtid = comment.getCmtid();
        this.userid = comment.getUserid();
        this.content = comment.getContent();
        this.date = comment.getDate();
        this.replies = replies;
    }
}
