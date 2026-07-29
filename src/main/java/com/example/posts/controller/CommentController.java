package com.example.posts.controller;

import com.example.posts.dto.*;
import com.example.posts.service.CommentService;
import com.example.posts.service.PostService;
import java.util.List;

import com.example.posts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponse createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        return commentService.createComment(postId, request);
    }

    @GetMapping
    public List<CommentResponse> getCommentsByPostId(
            @PathVariable Long postId
    ) {
        return commentService.getCommentsByPostId(postId);
    }

}
