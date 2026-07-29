package com.example.posts.controller;

import com.example.posts.dto.CreatePostRequest;
import com.example.posts.dto.PostResponse;
import com.example.posts.dto.UpdatePostRequest;
import com.example.posts.service.PostService;
import java.util.List;

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
@RequestMapping("/posts/{postId}")
public class PostController {

    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @GetMapping
    public PostResponse getPostById(@PathVariable Long postId)
    {
        return postService.getPostById(postId);
    }

    @DeleteMapping
    public void deletePost(@PathVariable Long postId)
    {
        postService.deletePost(postId);
    }

    @PutMapping
    public PostResponse updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        return postService.updatePost(postId, request);
    }
}
