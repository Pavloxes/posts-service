package com.example.posts.controller;

import com.example.posts.dto.*;
import com.example.posts.service.PostService;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/users/{userId}/posts")
public class UserPostController {

    private final PostService postService;

    public UserPostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @PostMapping
    public PostResponse createPost(
            @PathVariable Long userId,
            @Valid @RequestBody CreatePostRequest request
    ) {
        return postService.createPost(userId, request);
    }

    @GetMapping
    public List<PostResponse> getPosts(@PathVariable Long userId) {
        return postService.getPostsByUserId(userId);
    }
}
